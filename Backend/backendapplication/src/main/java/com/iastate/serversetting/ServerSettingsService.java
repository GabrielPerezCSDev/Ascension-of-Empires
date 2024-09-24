package com.iastate.serversetting;

import com.iastate.country.Country;
import com.iastate.country.CountryRepo;
import com.iastate.server.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 *
 * @author Gabriel Perez
 *
 */
@Service
public class ServerSettingsService {

    @Autowired
    ServerSettingsRepo ssr;
    @Autowired
    CountryRepo cr;
    @Autowired
    ServerService sser;
    //CREATE
    public void createServerSettings(ServerSettings ss){
        //for now will have all countries be in the server settings (this will change later) check if null
        if(ss.getCountryList() == null || ss.getCountryList().isEmpty()){
            List<Country> countryList = cr.findAll();
            ss.setCountryList(countryList);
            System.out.println(ss.getCountryList().toString());
        }

        ssr.save(ss);

        //We also will create a new Server on this call as well
        sser.createServer(ss);
    }

    //READ
    public List<ServerSettings> getAllServerSettings(){
        return ssr.findAll();
    }

    public ServerSettings getServerSettingsById(Long id){
        return ssr.findById(id).orElseThrow(() -> new RuntimeException("ServerSettings not found"));
    }

    //UPDATE
    public void updateServerSettings(ServerSettings ss){
        ssr.save(ss);
    }

    //DELETE
    public void deleteServerSettings(ServerSettings ss){
        ssr.delete(ss);
    }

}
