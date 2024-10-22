package dmit2015.service;

import dmit2015.entity.Movie;
import dmit2015.repository.MovieRepository;
import jakarta.data.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MovieService implements Serializable {

    @Inject
    private MovieRepository _movieRepository;

    public void addMovie(Movie newMovie) {
        newMovie.setCreateTime(LocalDateTime.now());
        _movieRepository.add(newMovie);
    }

    public void updateMovie(Movie updatedMovie) {
        updatedMovie.setUpdateTime(LocalDateTime.now());
        _movieRepository.update(updatedMovie);
    }

    public void deleteMovie(Movie existingMovie) {
        _movieRepository.delete(existingMovie);
    }

//    public void deleteMovieById(Long id) {
//        Optional<Movie> optionalMovie = _movieRepository.find(id);
//        if (optionalMovie.isPresent()) {
//            Movie existingMovie = optionalMovie.orElseThrow();
//            deleteMovie(existingMovie);
//        }
//    }

    public Optional<Movie> findMovieById(Long id) {
        return _movieRepository.find(id);
    }

    public List<Movie> findAllMovies(Sort<Movie> sort) {
        return _movieRepository.findAll(sort);

    }

    public long countMovies() {
        return _movieRepository.count();
    }

    public void deleteAllMovies() {
        _movieRepository.deleteAll();
    }

}
