package br.com.alura.screensound.repository;

import br.com.alura.screensound.model.Artist;
import br.com.alura.screensound.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {
    @Query("SELECT s FROM Song s JOIN s.artist a WHERE a = :artist ORDER BY s.dateReleased")
    List<Song> findByArtist(Artist artist);
}
