package com.iastate.serversetting;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 *
 * @author Gabriel Perez
 *
 */
@Api(value = "ServerSettingsController", description = "REST APIs related to ServerSettings Entity!!!!")
@RestController
@EnableAutoConfiguration
public class ServerSettingsController {

    @Autowired
    private ServerSettingsService sss;

    //CREATE
    @ApiOperation(value = "Create a new ServerSettings in the System by players current Session", response = Iterable.class, tags = "createServerSettings")
    @PostMapping("/ServerSettings/create")
    public void createServerSettings(@RequestBody ServerSettings ss){
        sss.createServerSettings(ss);
    }

    //READ
    @ApiOperation(value = "Get all ServerSettings in the system ", response = Iterable.class, tags = "getAllServerSettings")
    @GetMapping("/ServerSettings")
    public List<ServerSettings> getAllServerSettings(){
        return sss.getAllServerSettings();
    }

    @ApiOperation(value = "Get specific ServerSettings in the System by ID ", response = Iterable.class, tags = "getServerSettingsById")
    @PutMapping("/ServerSettings/{id}")
    public ServerSettings getServerSettingsById(@PathVariable Long id){
        return sss.getServerSettingsById(id);
    }

    //UPDATE
    @ApiOperation(value = "Update a ServerSettings in the System by players current Session", response = Iterable.class, tags = "updateServerSettings")
    @PostMapping("/ServerSettings/update")
    public void updateServerSettings(@RequestBody ServerSettings ss){
        sss.updateServerSettings(ss);
    }

    //DELETE
    @ApiOperation(value = "Delete a ServerSettings by ServerSettings (mainly for debugging as they are deleted when the server is attached to is deleted anyways", response = Iterable.class, tags = "deleteServerSetting")
    @DeleteMapping("/ServerSettings/delete")
    public void deleteServerSettings(@RequestBody ServerSettings ss){
        sss.deleteServerSettings(ss);
    }
}
