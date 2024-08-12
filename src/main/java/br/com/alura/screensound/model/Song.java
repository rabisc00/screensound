package br.com.alura.screensound.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Artist artist;

    private String title;
    private long durationInSeconds;
    private LocalDate dateReleased;

    public Song() {}

    public Song(String title, Artist artist, long durationInSeconds, String dateReleased) {
        this.title = title;
        this.artist = artist;
        this.durationInSeconds = durationInSeconds;

        try {
            this.dateReleased = LocalDate.parse(dateReleased);
        } catch (DateTimeParseException e) {
            this.dateReleased = null;
        }
    }

    public String getTitle() {
        return title;
    }
}
