package dmit2015.faces;

import dmit2015.entity.Movie;
import dmit2015.repository.MovieRepository;

import dmit2015.service.MovieService;
import lombok.Getter;
import org.omnifaces.util.Messages;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("currentMovieCreateView")
@RequestScoped
public class MovieCreateView {

    @Inject
    private MovieService _movieService;

    @Getter
    private Movie newMovie = new Movie();

    public String onCreateNew() {
        String nextPage = "";
        try {
            _movieService.addMovie(newMovie);
            Messages.addFlashGlobalInfo("Create was successful. {0}", newMovie.getId());
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            Messages.addGlobalError("Create was not successful. {0}", e.getMessage());
        }
        return nextPage;
    }

}