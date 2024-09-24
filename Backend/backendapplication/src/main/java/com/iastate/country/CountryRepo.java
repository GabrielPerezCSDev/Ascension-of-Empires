package com.iastate.country;

import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author Gabriel Perez
 *
 */
public interface CountryRepo extends JpaRepository<Country, Long> {
}
