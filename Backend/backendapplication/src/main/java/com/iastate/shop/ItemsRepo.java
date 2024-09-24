package com.iastate.shop;

import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author Gabriel Perez
 *
 */
public interface ItemsRepo  extends JpaRepository<Items, Long> {
}

