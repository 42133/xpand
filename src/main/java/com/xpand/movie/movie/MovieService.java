package com.xpand.movie.movie;

import com.xpand.movie.movie.filter.MovieFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {
    /**
     * Gets a paginated and filtered movie list
     * @param movieFilter Movie Filters
     * @param pageable Page and Size
     * @return Movie List
     */
    Page<Movie> getMovies(MovieFilter movieFilter, Pageable pageable);

    /**
     * Get movie with id movieId
     * @param movieId Movie Id
     * @return Movie with movieId
     */
    Movie getMovieById(int movieId);

    /**
     * Creates a movie with the data passed in the movie object
     * @param movie Movie to create
     * @return Movie created
     */
    Movie createMovie(Movie movie);

    /**
     * Updates the movie with the data passed in the movie object
     * @param movie Movie to update
     * @return Movie updated
     */
    Movie updateMovie(Movie movie);

    /**
     * Delete movie with id movieId
     * @param movieId Movie Id
     */
    void deleteMovieById(int movieId);
}
