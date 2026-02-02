package io.github.AnirudhT28.spotify_graph_service.repository;

import io.github.AnirudhT28.spotify_graph_service.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, String> {

    // Global Hidden Gems
    @Query(value = "SELECT * FROM artists ORDER BY score / (1.0 + listeners) DESC LIMIT 10", nativeQuery = true)
    List<Artist> findHiddenGems();

    // Get all names for the search dropdown
    @Query(value = "SELECT name FROM artists ORDER BY name ASC", nativeQuery = true)
    List<String> findAllArtistNames();

    

     //TWO-STEP DISCOVERY: Finds artists influenced by the same people 
     
@Query(value = "SELECT DISTINCT a.* FROM artists a " +
"JOIN influences i1 ON a.name = i1.influenced_name " +
"JOIN influences i2 ON i1.artist_name = i2.influenced_name " +
"WHERE i2.artist_name = :seedName " +
"AND a.name != :seedName " + // Don't recommend the seed itself
"ORDER BY a.score / (1.0 + a.listeners) DESC LIMIT 10", nativeQuery = true)
List<Artist> findGemsBySeed(@Param("seedName") String seedName);
}