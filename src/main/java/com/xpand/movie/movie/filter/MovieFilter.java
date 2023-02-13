package com.xpand.movie.movie.filter;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class MovieFilter {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
