package com.dallman.lookingtoplay.Repository;

import com.dallman.lookingtoplay.Game.Game;
import com.dallman.lookingtoplay.Game.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlatformRepository extends JpaRepository<Platform, Integer> {

    // IgdbId is our identifier to check if we have already added this game previously, and it exists in our db
    Optional<Platform> findByIgdbPlatformId(Integer id);

    Optional<List<Platform>> findByGamesId(Integer id);

    Optional<Platform> findByNameIgnoreCase(String name);

    // We return a simple true or false to determine if the game already exists on the platform or not
    // From this response, we can then decide what we want to do.
    @Query("""
        select case when count(p) > 0 then true else false end
        from Platform p
        join p.games g
        where p.id = :platformId
          and g.id = :gameId
    """)
    boolean existsGameOnPlatform(
            @Param("platformId") Integer platformId,
            @Param("gameId") Integer gameId
    );

    // Return the platforms a game is, on if they exist
    @Query("""
        select distinct p
        from Platform p
        left join fetch p.games
        where p.id = :platformId
    """)
    Optional<Platform> findByIdWithGames(@Param("platformId") Integer platformId);

    void deleteById(Integer id);
}
