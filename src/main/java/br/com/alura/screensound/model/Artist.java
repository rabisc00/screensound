package br.com.alura.screensound.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "artists")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "artistType")
    private ArtistType type;

    private String name;

    public Artist() {}

    public Artist(String name, String type) {
        this.name = name;
        this.type = ArtistType.fromString(type);
    }

    public String getName() {
        return name;
    }
}
