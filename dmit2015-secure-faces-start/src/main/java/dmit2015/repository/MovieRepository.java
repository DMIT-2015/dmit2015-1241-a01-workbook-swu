package dmit2015.repository;

import dmit2015.entity.Movie;
import jakarta.data.Sort;
import jakarta.data.repository.*;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository {

    @Insert
    void add(Movie newMovie);

    @Update
    void update(Movie updatedMovie);

    @Delete
    void delete(Movie existingMovie);

    @Find
    Optional<Movie> find(Long id);

    // An instance of Sort represents a single criterion for sorting query results
    // An instance of Order packages multiple Sorts together.
    @Find
    List<Movie> findAll(Sort<Movie> sort);

    @Query("select count(m) from Movie m")
    long count();

    @Delete
    void deleteAll();

}