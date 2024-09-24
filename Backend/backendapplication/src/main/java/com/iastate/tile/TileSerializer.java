package com.iastate.tile;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.iastate.player.Player;

import java.io.IOException;

public class TileSerializer extends StdSerializer<Tile> {
    public TileSerializer() {
        this(null);
    }

    public TileSerializer(Class<Tile> t) {
        super(t);
    }

    @Override
    public void serialize(Tile tile, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("position", tile.getPosition());
        JsonSerializer<Object> playerSerializer = serializerProvider.findValueSerializer(Player.class);
        jsonGenerator.writeFieldName("owner");
        playerSerializer.serialize(tile.getOwner(), jsonGenerator, serializerProvider);
        jsonGenerator.writeObjectField("resource", tile.getResource());
        //jsonGenerator.writeStringField("type", tile.getType().toString());
        jsonGenerator.writeEndObject();
    }


}
