package com.iastate.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 *
 * @author Gabriel Perez
 *
 */
public interface UserRepo extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByUsername(String username);

    @Query("SELECT id FROM User WHERE username = :username")
    Long findUserIdByUsername(@Param("username") String username);

    @Override
    @Query("SELECT u FROM User u")
    List<User> findAll();
}

