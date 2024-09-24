package com.iastate.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.iastate.player.Player;
import com.iastate.player.PlayerSerializer;
import com.iastate.tile.Tile;
import com.iastate.tile.TileSerializer;
import org.springframework.context.annotation.Bean;

public class JacksonConfiguration {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        //module.addSerializer(Tile.class, new TileSerializer());
       // module.addSerializer(Player.class, new PlayerSerializer());
        mapper.registerModule(module);
        return mapper;
    }
}
