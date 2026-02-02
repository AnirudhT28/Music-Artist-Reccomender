package io.github.AnirudhT28.spotify_graph_service.controller;

import io.github.AnirudhT28.spotify_graph_service.model.Artist;
import io.github.AnirudhT28.spotify_graph_service.repository.ArtistRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistRepository repository;

    public ArtistController(ArtistRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/names")
    public List<String> getNames() {
        return repository.findAllArtistNames();
    }

    @GetMapping("/gems")
    public List<Artist> getGems(@RequestParam(required = false) String seed) {
        if (seed != null && !seed.isEmpty()) {
            return repository.findGemsBySeed(seed);
        }
        return repository.findHiddenGems();
    }
}