package com.xpand.movie.movie;

import com.xpand.movie.exception.BadRequestException;
import com.xpand.movie.exception.ResourceNotFoundException;
import com.xpand.movie.movie.filter.MovieFilter;
import com.xpand.movie.movie.filter.MovieSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.xpand.movie.utils.message.Internationalization.*;

@Service
public class MovieServiceImpl implements MovieService {

    private final Logger logger = LoggerFactory.getLogger(MovieService.class);

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Page<Movie> getMovies(MovieFilter movieFilter, Pageable pageable) {
        return movieRepository.findAll(
                MovieSpecification.filter(movieFilter),
                pageable
        );
    }

    @Override
    public Movie getMovieById(int movieId){
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND.getLabel(), MOVIE.getLabel()));
    }

    @Override
    public Movie createMovie(Movie movie) {
        validateMovieObject(movie);

        try{
            return movieRepository.save(movie);
        } catch(Exception ex){
            logger.error("Error creating movie - Exception {}", ex.getMessage());
            throw new BadRequestException(OPERATION_FAILED.getLabel());
        }
    }

    @Override
    public Movie updateMovie(Movie movie) {
        //Validate if exists a movie with that id
        this.getMovieById(movie.getId());

        validateMovieObject(movie);

        try{
            return movieRepository.save(movie);
        } catch(Exception ex){
            logger.error("Error updating movie - Exception {}", ex.getMessage());
            throw new BadRequestException(OPERATION_FAILED.getLabel());
        }
    }

    @Override
    public void deleteMovieById(int movieId) {
        //Validate if exists a movie with that id
        this.getMovieById(movieId);

        try {
            movieRepository.deleteById(movieId);
        } catch(Exception ex){
            logger.error("Error deleting movie with id {} - Exception: {}", movieId, ex.getMessage());
            throw new BadRequestException(OPERATION_FAILED.getLabel());
        }
    }

    private void validateMovieObject(Movie movie) {
        if(movie.getTitle() == null || movie.getTitle().isBlank()){
            logger.error("Title {} is invalid", movie.getTitle());
            throw new BadRequestException(NOT_EMPTY_TITLE.getLabel());
        }

        if(movie.getRank() < 0 || movie.getRank() > 10){
            logger.error("Rank {} is invalid", movie.getRank());
            throw new BadRequestException(INVALID_RANK.getLabel());
        }
    }

}
