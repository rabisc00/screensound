package br.com.alura.screensound;

import br.com.alura.screensound.principal.Principal;
import br.com.alura.screensound.repository.ArtistRepository;
import br.com.alura.screensound.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class ScreensoundApplication implements CommandLineRunner {
	@Autowired
	private ArtistRepository artistRepository;

	@Autowired
	private SongRepository songRepository;

	public static void main(String[] args) {
		SpringApplication.run(ScreensoundApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(artistRepository, songRepository);
		principal.displayMenu();
	}
}
