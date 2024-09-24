package com.iastate.websockets;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

@ServerEndpoint("/chat/{serverId}/{username}")
@Component
public class ServerChat {


    // Store all socket session and their corresponding username and serverId
    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<Session, String> sessionServerIdMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    // server side logger
    private final Logger logger = LoggerFactory.getLogger(ServerChat.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("serverId") String serverId, @PathParam("username") String username) throws IOException {
        // Handle the case of a duplicate username in the same server
        if (isUsernameInServer(username, serverId)) {
            session.getBasicRemote().sendText("Username already exists in this server");
            session.close();
        } else {
            // map current session with username and serverId
            sessionUsernameMap.put(session, username);
            sessionServerIdMap.put(session, serverId);
            usernameSessionMap.put(username, session);

            // send to the user joining in
            sendMessageToParticularUser(username, "Welcome to the chat server, " + username);

            // send to everyone in the chat
            broadcast(serverId, "User: " + username + " has Joined the Chat");
        }
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        String username = sessionUsernameMap.get(session);
        String serverId = sessionServerIdMap.get(session);

        // Direct message to a user using the format "@username <message>"
        if (message.startsWith("@")) {
            String[] split_msg = message.split("\\s+");
            StringBuilder actualMessageBuilder = new StringBuilder();
            for (int i = 1; i < split_msg.length; i++) {
                actualMessageBuilder.append(split_msg[i]).append(" ");
            }
            String destUserName = split_msg[0].substring(1);
            String actualMessage = actualMessageBuilder.toString();
            sendMessageToParticularUser(destUserName, "[DM from " + username + "]: " + actualMessage);
            sendMessageToParticularUser(username, "[DM from " + username + "]: " + actualMessage);
        } else {
            // Message to whole chat
            broadcast(serverId, username + ": " + message);
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        String username = sessionUsernameMap.get(session);
        String serverId = sessionServerIdMap.get(session);

        sessionUsernameMap.remove(session);
        sessionServerIdMap.remove(session);
        usernameSessionMap.remove(username);

        broadcast(serverId, username + " disconnected");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        String username = sessionUsernameMap.get(session);
        System.err.println("[onError] " + username + ": " + throwable.getMessage());
    }

    private void sendMessageToParticularUser(String username, String message) {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
            System.err.println("[DM Exception] " + e.getMessage());
        }
    }

    private void broadcast(String serverId, String message) {
        sessionServerIdMap.forEach((session, currentServerId) -> {
            if (serverId.equals(currentServerId)) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    System.err.println("[Broadcast Exception] " + e.getMessage());
                }
            }
        });
    }

    private boolean isUsernameInServer(String username, String serverId) {
        for (Map.Entry<Session, String> entry : sessionServerIdMap.entrySet()) {
            if (entry.getValue().equals(serverId) && sessionUsernameMap.get(entry.getKey()).equals(username)) {
                return true;
            }
        }
        return false;
    }


}
