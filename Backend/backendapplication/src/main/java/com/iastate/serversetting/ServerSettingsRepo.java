package com.iastate.serversetting;

import com.iastate.server.Server;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author Gabriel Perez
 *
 */
public interface ServerSettingsRepo extends JpaRepository<ServerSettings, Long> {

       // ServerSettings findByServer(Server server);
}
