package dmit2015.faces;

import dmit2015.entity.Movie;
import dmit2015.service.MovieService;
import jakarta.data.Sort;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import org.omnifaces.util.Messages;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Named("currentMovieListView")
@ViewScoped
public class MovieListView implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private MovieService _movieService;

    @Getter
    private List<Movie> movieList;

    @Getter
    private String subject;

//    @PostConstruct  // After @Inject is complete
    public void init() {
        try {
            movieList = _movieService.findAllMovies(Sort.asc("title"));
        } catch (RuntimeException ex) {
            Messages.addGlobalWarn(ex.getMessage());
        } catch (Exception ex) {
            Messages.addGlobalError(ex.getMessage());
        }
    }
}