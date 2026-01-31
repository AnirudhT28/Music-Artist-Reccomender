package io.github.AnirudhT28.spotify_graph_service.repository; 

import io.github.AnirudhT28.spotify_graph_service.model.Artist; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, String> {
    
    /**
     * The "Hidden Gem" Algorithm: Influence / (Listeners + 1)
     * nativeQuery = true tells Spring to use standard SQL for SQLite.
     */
    @Query(value = "SELECT * FROM artists ORDER BY score / (1.0 + listeners) DESC LIMIT 10", nativeQuery = true)
    List<Artist> findHiddenGems();
}