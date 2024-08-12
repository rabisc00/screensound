package br.com.alura.screensound.principal;

import br.com.alura.screensound.model.Artist;
import br.com.alura.screensound.model.Song;
import br.com.alura.screensound.model.WrapperLastFM;
import br.com.alura.screensound.repository.ArtistRepository;
import br.com.alura.screensound.repository.SongRepository;
import br.com.alura.screensound.service.ApiCaller;
import br.com.alura.screensound.service.DataConverter;

import java.util.*;

public class Principal {
    private ArtistRepository artistRepository;
    private SongRepository songRepository;

    private Scanner scanner = new Scanner(System.in);
    private ApiCaller apiCaller = new ApiCaller();
    private DataConverter dataConverter = new DataConverter();

    private final List<String> validTypes = Arrays.asList("solo", "duo", "band");

    private final String ENDPOINT = "http://ws.audioscrobbler.com/2.0/?format=json";
    private final String API_KEY = "&api_key=" + System.getenv("LAST_FM_SCREENSOUND_KEY");

    public Principal(ArtistRepository artistRepository, SongRepository songRepository) {
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
    }

    public void displayMenu() {
        System.out.println("* * * * * ScreenSound * * * * *");

        var menu = """
                \n1 - Get artist bio
                2 - Register artist
                3 - Register song
                4 - List songs of an artist
                
                0 - Exit""";

        var option = -1;

        while (option != 0) {
            System.out.println(menu);

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    searchArtistBio();
                    break;
                case 2:
                    registerArtist(null);
                    break;
                case 3:
                    registerSong();
                    break;
                case 4:
                    listSongsByArtist();
                    break;
                case 0:
                    System.out.println("\nExiting...");
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private void searchArtistBio() {
        System.out.print("\nWhat artist do you want to search for?\n- ");
        var inputArtist = scanner.nextLine();

        String uri = ENDPOINT + API_KEY + "&method=artist.getInfo&artist=" + inputArtist.replace(" ", "+");
        var json = apiCaller.requestData(uri);

        var wrapperLastFM = dataConverter.convertTo(json, WrapperLastFM.class);
        System.out.println("\n" + wrapperLastFM.artist().bio().content().replace("\n", "") + "\n");
    }

    private void registerArtist(String artistName) {
        var inputName = artistName;
        if (inputName == null) {
            System.out.print("\nWhat is the name of the artist?\n- ");
            inputName = scanner.nextLine();
        }

        System.out.print("\nWhat is the type of the artist? solo, duo, or band?\n- ");
        var inputType = scanner.nextLine();

        while (!validTypes.contains(inputType)) {
            System.out.print("\nWhat is the type of the artist? solo, duo, or band?\n- ");
            inputType = scanner.nextLine();
        }

        var newArtist = new Artist(inputName, inputType);
        artistRepository.save(newArtist);
    }

    private void registerSong() {
        System.out.print("\nWhat's the name of the song?\n- ");
        var inputSong = scanner.nextLine();

        System.out.print("\nWhat artist is the song attached to?\n- ");
        var inputArtist = scanner.nextLine();

        Optional<Artist> artistFound = artistRepository.findByNameIgnoreCase(inputArtist);

        if (artistFound.isEmpty()) {
            System.out.print("\nThere's no artist called " + inputArtist +
                    " in the database. Do you want to register them? Y/N\n- ");

            var inputRegisterArtist = scanner.nextLine();

            if (inputRegisterArtist.equalsIgnoreCase("Y")) {
                registerArtist(inputArtist);
                artistFound = artistRepository.findByNameIgnoreCase(inputArtist);
            } else {
                return;
            }
        }

        System.out.print("\nHow many seconds does the song have?\n- ");

        var inputLength = scanner.nextInt();
        scanner.nextLine();

        System.out.print("\nWhen was the song released? (format yyyy-MM-dd)\n- ");
        var inputDate = scanner.nextLine();

        Song newSong = new Song(inputSong, artistFound.get(), inputLength, inputDate);
        songRepository.save(newSong);
    }

    private void listSongsByArtist() {
        System.out.print("\nWhat's the name of the artist?\n- ");
        var inputArtist = scanner.nextLine();

        Optional<Artist> artistFound = artistRepository.findByNameIgnoreCase(inputArtist);

        while (artistFound.isEmpty()) {
            System.out.print("\nThis artist isn't in the database!\n" +
                    "What's the name of the artist?\n- ");
            inputArtist = scanner.nextLine();

            artistFound = artistRepository.findByNameIgnoreCase(inputArtist);
        }

        System.out.println("\nSongs by " + inputArtist + ":");

        List<Song> songsFound = songRepository.findByArtist(artistFound.get());
        songsFound.forEach(s -> System.out.println(s.getTitle()));
    }
}
