package com.iastate.country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 *
 * @author Gabriel Perez
 *
 */
@Service
public class CountryService {
    @Autowired
    CountryRepo cr;

    //READ
    public Country getCountryById(Long id) {
        return cr.findById(id).orElseThrow(() -> new RuntimeException("Country not found"));
    }


    public List<Country> getAllCountries() {
        return cr.findAll();
    }
}
