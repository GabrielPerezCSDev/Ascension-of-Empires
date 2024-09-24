package com.iastate.server;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 *
 * @author Gabriel Perez
 *
 */
@Repository
public interface ServerRepo extends JpaRepository<Server, Long> {

    Server findByPlayerId(Long id);
    @Query("SELECT s.serverSettings.id FROM Server s WHERE s.id = :serverId")
    Long findServerSettingsIdByServerId(@Param("serverId") Long serverId);

}

