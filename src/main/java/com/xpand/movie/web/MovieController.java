package com.xpand.movie.web;

import com.xpand.movie.movie.MovieDto;
import com.xpand.movie.movie.MovieMapper;
import com.xpand.movie.movie.MovieService;
import com.xpand.movie.movie.filter.MovieFilter;
import com.xpand.movie.utils.message.Message;
import com.xpand.movie.utils.message.MessageBuilder;
import com.xpand.movie.utils.pagination.PageWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.xpand.movie.utils.message.Internationalization.*;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private MessageBuilder<MovieDto> messageBuilder;

    @GetMapping
    PageWrapper<MovieDto> getMoviesPaginated(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          MovieFilter movieFilter){
        logger.info("Get a paginated list of movies");

        return new PageWrapper<>(
                movieService.getMovies(movieFilter, PageRequest.of(page, size))
                        .map(movieMapper::movieToMovieDto)
        );
    }

    @GetMapping("/{id}")
    MovieDto getMovieById(@PathVariable int id){
        logger.info("Get movie with id {}", id);
        return movieMapper.movieToMovieDto(movieService.getMovieById(id));
    }

    @PostMapping
    Message<MovieDto> createMovie(@RequestBody MovieDto movie){
        logger.info("Create movie");

        MovieDto movieCreated = movieMapper.movieToMovieDto(
                movieService.createMovie(movieMapper.movieDtoToMovie(movie))
        );

        logger.info("The movie was successfully created with id {}", movieCreated.getId());
        return messageBuilder.success(HttpStatus.CREATED, movieCreated, CREATE_SUCCESSFUL.getLabel(), MOVIE.getLabel());
    }

    @PutMapping("/{id}")
    Message<MovieDto> updateMovie(@PathVariable int id, @RequestBody MovieDto movie){
        logger.info("Update movie with id {}", id);

        movie.setId(id);
        MovieDto movieUpdated = movieMapper.movieToMovieDto(
                movieService.updateMovie(movieMapper.movieDtoToMovie(movie))
        );

        logger.info("The movie with id {} was successfully updated", id);
        return messageBuilder.success(HttpStatus.OK, movieUpdated, UPDATE_SUCCESSFUL.getLabel(), MOVIE.getLabel());
    }

    @DeleteMapping("/{id}")
    Message<MovieDto> deleteMovieById(@PathVariable int id){
        logger.info("Delete movie with id {}", id);

        movieService.deleteMovieById(id);

        logger.info("The movie with id {} was successfully deleted", id);
        return messageBuilder.success(HttpStatus.OK, null, DELETE_SUCCESSFUL.getLabel(), MOVIE.getLabel());
    }

}
