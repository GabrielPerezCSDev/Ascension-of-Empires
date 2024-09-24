package com.iastate.country;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 *
 * @author Gabriel Perez
 *
 */
@Api(value = "CountryController", description = "REST APIs related to Country Entity!!!!")
@RestController
@EnableAutoConfiguration
public class CountryController {
    @Autowired
    CountryService cs;
    @ApiOperation(value = "Get list of Countries in the System ", response = Iterable.class, tags = "getCountries")
    @ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "Success|OK"),
            @io.swagger.annotations.ApiResponse(code = 401, message = "not authorized!"),
            @io.swagger.annotations.ApiResponse(code = 403, message = "forbidden!!!"),
            @io.swagger.annotations.ApiResponse(code = 404, message = "not found!!!") })
    @GetMapping("/countries")
    public List<Country> getCountries(){
       return cs.getAllCountries();
    }
    @ApiOperation(value = "Get specific Country in the System ", response = Iterable.class, tags = "getCountryById")
    @PutMapping ("/countries/{id}")
    public Country getCountryById(@PathVariable Long id){
        return cs.getCountryById(id);
    }

}
