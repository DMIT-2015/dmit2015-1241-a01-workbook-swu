package dmit2015.faces;

import dmit2015.entity.Movie;
import dmit2015.service.MovieService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;

@Named("currentMovieDeleteView")
@ViewScoped
public class MovieDeleteView implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private MovieService _movieService;

    @Inject
    @ManagedProperty("#{param.editId}")
    @Getter
    @Setter
    private Long editId;

    @Getter
    private Movie existingMovie;

    @PostConstruct
    public void init() {
        Optional<Movie> optionalMovie = _movieService.findMovieById(editId);
        if (optionalMovie.isPresent()) {
            existingMovie = optionalMovie.orElseThrow();
        } else {
            Faces.redirect(Faces.getRequestURI().substring(0, Faces.getRequestURI().lastIndexOf("/")) + "/index.xhtml");
        }
    }

    public String onDelete() {
        String nextPage = "";
        try {
            _movieService.deleteMovie(existingMovie);
            Messages.addFlashGlobalInfo("Delete was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (RuntimeException ex) {
            Messages.addGlobalWarn(ex.getMessage());
        } catch (Exception e) {
            Messages.addGlobalError("Delete not successful.");
        }
        return nextPage;
    }
}