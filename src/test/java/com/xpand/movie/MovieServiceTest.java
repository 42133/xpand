package com.xpand.movie;

import com.xpand.movie.exception.BadRequestException;
import com.xpand.movie.exception.ResourceNotFoundException;
import com.xpand.movie.movie.Movie;
import com.xpand.movie.movie.MovieRepository;
import com.xpand.movie.movie.MovieServiceImpl;
import com.xpand.movie.movie.filter.MovieFilter;
import com.xpand.movie.movie.filter.MovieSpecification;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class MovieServiceTest {
    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    @Test
    public void getMovies_withoutFilters_returnMovieList() {
        MovieFilter movieFilter = new MovieFilter();
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie(1, "Test Movie", LocalDate.of(2022,10,20),8, new BigDecimal(20000)));
        movies.add(new Movie(2, "Test Movie 22", LocalDate.of(2022,12,8),10, new BigDecimal(1200)));

        Page<Movie> moviesPage = new PageImpl<>(movies);
        given(movieRepository.findAll(
                MovieSpecification.filter(movieFilter),
                PageRequest.of(0, 10))
        ).willReturn(moviesPage);

        movieService.getMovies(movieFilter, PageRequest.of(0, 10));
        assertThat(moviesPage.getSize()).isEqualTo(2);
    }

    @Test
    public void getMovies_withFilters_returnMovieListFiltered() {
        MovieFilter movieFilter = new MovieFilter();
        movieFilter.setDate(LocalDate.of(2022,10,20));
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie(1, "Test Movie", LocalDate.of(2022,10,20),8, new BigDecimal(20000)));

        Page<Movie> moviesPage = new PageImpl<>(movies);
        given(movieRepository.findAll(
                MovieSpecification.filter(movieFilter),
                PageRequest.of(0, 10))
        ).willReturn(moviesPage);

        movieService.getMovies(movieFilter, PageRequest.of(0, 10));
        assertThat(moviesPage.getSize()).isEqualTo(1);
    }

    @Test
    public void getMovieById_whenExistingId_returnMovie() {
        Movie movie = new Movie(1, "Test Movie", LocalDate.of(2022,10,15),8, new BigDecimal(20000));

        when(movieRepository.findById(1)).thenReturn(Optional.of(movie));
        Movie movieDb = movieService.getMovieById(1);

        assertThat(movieDb.getTitle()).isEqualTo(movie.getTitle());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getMovieById_whenNonExistingId_throwsNotFoundException() {
        when(movieRepository.findById(1)).thenReturn(Optional.empty());
        movieService.getMovieById(1);
    }

    @Test
    public void createMovie_whenValidMovie_returnMovieCreated() {
        Movie movie = new Movie(1, "Test Movie", LocalDate.of(2022,10,15),8, new BigDecimal(20000));

        when(movieRepository.save(ArgumentMatchers.any(Movie.class))).thenReturn(movie);
        Movie movieCreated = movieService.createMovie(movie);
        assertThat(movieCreated.getTitle()).isSameAs(movie.getTitle());
    }

    @Test(expected = BadRequestException.class)
    public void createMovie_whenInvalidTitle_throwsBadRequestException() {
        Movie movie = new Movie(1, null, LocalDate.of(2022,10,15),8, new BigDecimal(20000));
        movieService.createMovie(movie);
    }

    @Test(expected = BadRequestException.class)
    public void createMovie_whenInvalidRange_throwsBadRequestException() {
        Movie movie = new Movie(1, "Test Movie", LocalDate.of(2022,10,15),30, new BigDecimal(20000));
        movieService.createMovie(movie);
    }

    @Test
    public void updateMovie_whenValidMovie_returnMovieUpdated() {
        Movie movie = new Movie(1, "Test Movie", LocalDate.of(2022,10,15),2, new BigDecimal(20000));
        Movie movieToUpdate = new Movie(1, "Test Movie Updated", LocalDate.of(2022,10,15),2, new BigDecimal(20000));

        given(movieRepository.findById(1)).willReturn(Optional.of(movie));
        when(movieRepository.save(ArgumentMatchers.any(Movie.class))).thenReturn(movieToUpdate);
        Movie movieUpdated = movieService.updateMovie(movie);

        assertThat(movieUpdated.getTitle()).isEqualTo(movieToUpdate.getTitle());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateMovie_whenNonExistingId_throwsNotFoundException() {
        Movie movieToUpdate = new Movie(1, "Test Movie", LocalDate.of(2022,10,15),2, new BigDecimal(20000));
        given(movieRepository.findById(1)).willReturn(Optional.empty());
        movieService.updateMovie(movieToUpdate);
    }

    @Test(expected = BadRequestException.class)
    public void updateMovie_whenInvalidTitle_throwsBadRequestException() {
        Movie movie = new Movie(1, "Test Movie", LocalDate.of(2022,10,15),2, new BigDecimal(20000));
        Movie movieToUpdate = new Movie(1, null, LocalDate.of(2022,10,15),2, new BigDecimal(20000));

        given(movieRepository.findById(1)).willReturn(Optional.of(movie));
        movieService.updateMovie(movieToUpdate);
    }

    @Test(expected = BadRequestException.class)
    public void updateMovie_whenInvalidRange_throwsBadRequestException() {
        Movie movie = new Movie(1, "Test Movie", LocalDate.of(2022,10,15),2, new BigDecimal(20000));
        Movie movieToUpdate = new Movie(1, "Test Movie Updated", LocalDate.of(2022,10,15),30, new BigDecimal(20000));

        given(movieRepository.findById(1)).willReturn(Optional.of(movie));
        movieService.updateMovie(movieToUpdate);
    }

    @Test
    public void deleteMovie_whenExistingId_returnSuccessMessage() {
        int movieId = 1;
        Movie movie = new Movie(movieId, "Test Movie", LocalDate.of(2022,10,15),8, new BigDecimal(20000));
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        movieService.deleteMovieById(movieId);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteMovieById_whenNonExistingId_throwsNotFoundException() {
        when(movieRepository.findById(1)).thenReturn(Optional.empty());
        movieService.deleteMovieById(1);
    }

}
