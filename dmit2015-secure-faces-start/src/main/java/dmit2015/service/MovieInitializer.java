package dmit2015.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;

import java.util.logging.Logger;

@ApplicationScoped
public class MovieInitializer {
    private final Logger _logger = Logger.getLogger(MovieInitializer.class.getName());

//    @Inject
//    private MovieService _movieService;

    /**
     * Using the combination of `@Observes` and `@Initialized` annotations, you can
     * intercept and perform additional processing during the phase of beans or events
     * in a CDI container.
     *
     * The @Observers is used to specify this method is in observer for an event
     * The @Initialized is used to specify the method should be invoked when a bean type of `ApplicationScoped` is being
     * initialized
     *
     * Execute code to create the test data for the Movie entity.
     * This is an alternative to using a @WebListener that implements a ServletContext listener.
     *
]    * @param event
     */
    public void initialize(@Observes @Initialized(ApplicationScoped.class) Object event) {
        _logger.info("Initializing movies");

//        _movieService.deleteAllMovies();

//        try {
//            Movie movie1 = new Movie();
////            movie1.setUsername("DAUSTIN");
//            movie1.setTitle("When Harry Met Sally");
//            movie1.setReleaseDate(LocalDate.parse("1989-02-12"));
//            movie1.setGenre("Romantic Comedy");
//            movie1.setPrice(BigDecimal.valueOf(7.99));
//            movie1.setRating("G");
//            _movieRepository.add(movie1);
//
//            Movie movie2 = new Movie();
////            movie2.setUsername("DAUSTIN");
//            movie2.setTitle("Ghostbusters");
//            movie2.setReleaseDate(LocalDate.parse("1984-03-13"));
//            movie2.setGenre("Comedy");
//            movie2.setPrice(BigDecimal.valueOf(8.99));
//            movie2.setRating("PG");
//            _movieRepository.add(movie2);
//
//            Movie movie3 = new Movie();
////            movie3.setUsername("DAUSTIN");
//            movie3.setTitle("Ghostbusters 2");
//            movie3.setReleaseDate(LocalDate.parse("1986-02-23"));
//            movie3.setGenre("Comedy");
//            movie3.setPrice(BigDecimal.valueOf(9.99));
//            movie3.setRating("PG");
//            _movieRepository.add(movie3);
//
//            Movie movie4 = new Movie();
////            movie4.setUsername("DAUSTIN");
//            movie4.setTitle("Rio Bravo");
//            movie4.setReleaseDate(LocalDate.parse("1959-04-15"));
//            movie4.setGenre("Western");
//            movie4.setPrice(BigDecimal.valueOf(7.99));
//            movie4.setRating("PG-13");
//            _movieRepository.add(movie4);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }

//        if (_movieService.countMovies() == 0) {
//            try {
//                try (var reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/data/csv/movies.csv")))) ) {
//                    String line;
//                    final var delimiter = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
//                    // Skip the first line as it is containing column headings
//                    reader.readLine();
//                    while ((line = reader.readLine()) != null) {
//                        Optional<Movie> optionalMovie = Movie.parseCsv(line);
//                        if (optionalMovie.isPresent()) {
//                            Movie csvMovie = optionalMovie.orElseThrow();
//                            _movieService.addMovie(csvMovie);
//                        }
//                    }
//                }
//
//            } catch (Exception ex) {
//                _logger.log(Level.WARNING,"Exception occurred while processing csv file ",ex);
//            }
//        }
    }
}
