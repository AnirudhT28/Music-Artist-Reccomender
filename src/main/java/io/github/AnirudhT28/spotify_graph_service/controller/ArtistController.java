package io.github.AnirudhT28.spotify_graph_service.controller;

import io.github.AnirudhT28.spotify_graph_service.model.Artist;
import io.github.AnirudhT28.spotify_graph_service.repository.ArtistRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistRepository repository;

    public ArtistController(ArtistRepository repository) {
        this.repository = repository;
    }

    // This is our sanity check URL
    @GetMapping("/test")
    public String test() {
        return "The API is alive and reachable!";
    }

    @GetMapping("/gems")
    public List<Artist> getHiddenGems() {
        return repository.findHiddenGems();
    }
}