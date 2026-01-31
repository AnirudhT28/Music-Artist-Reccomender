package io.github.AnirudhT28.spotify_graph_service.model; 

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "artists")
public class Artist {
    @Id
    private String name;
    private Double score;
    private Integer listeners;

    // Getters
    public String getName() { return name; }
    public Double getScore() { return score; }
    public Integer getListeners() { return listeners; }
}