package com.xpand.movie.actor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xpand.movie.movie.Movie;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="actors")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;

    @Column(name="birth_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Column
    private String gender;

    @ManyToMany
    @JoinTable(
            name = "movies_actors",
            joinColumns = @JoinColumn(name = "actor_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private List<Movie> movies;

    public Actor() {
    }

    public Actor(int id, String name, LocalDate birthDate, String gender) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
