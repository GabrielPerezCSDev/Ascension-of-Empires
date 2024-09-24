package com.iastate.player;
import com.iastate.country.Country;
import com.iastate.server.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
/**
 *
 * @author Gabriel Perez
 *
 */
public interface PlayerRepo extends JpaRepository<Player, Long> {
    Long findIdByUsername(String username);
    @Query("SELECT p FROM Player p WHERE p.server.id = :serverId")
    List<Player> findByServerId(@Param("serverId") Long serverId);

    Player findByUserId(Long userId);

    List<Player> findAllByServerId(Long serverId);

    Player findByUsername(String username);


}
