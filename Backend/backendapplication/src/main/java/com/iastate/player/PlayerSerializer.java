package com.iastate.player;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.iastate.tile.Tile;

import java.io.IOException;

public class PlayerSerializer extends StdSerializer<Player> {
    public PlayerSerializer() {
        this(null);
    }

    public PlayerSerializer(Class<Player> t) {
        super(t);
    }

    @Override
    public void serialize(Player player, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", player.getId());
        jsonGenerator.writeStringField("username", player.getUsername());
        jsonGenerator.writeNumberField("gold", player.getGold());
        jsonGenerator.writeNumberField("gpt", player.getGpt());
        jsonGenerator.writeNumberField("grwpt", player.getGrwpt());
        jsonGenerator.writeObjectField("country", player.getCountry());
        jsonGenerator.writeObjectField("items", player.getItems());
        jsonGenerator.writeNumberField("totalTilesOwned", player.getTotalTilesOwned());
        jsonGenerator.writeNumberField("totalItemsOwned", player.getTotalItemsOwned());
        jsonGenerator.writeEndObject();
    }
}
