package com.xpand.movie.movie.filter;

import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

public class MovieFilter {

    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
