package com.xpand.movie.movie.filter;

import com.xpand.movie.movie.Movie;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class MovieSpecification {
    public static Specification<Movie> filter(MovieFilter movieFilter) {
        return new Specification<Movie>() {

            @Override
            public Predicate toPredicate(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();

                if (movieFilter.getDate() != null) {
                    predicateList.add(cb.equal(root.get("date"), movieFilter.getDate()));
                }

                return cb.and(predicateList.toArray(new Predicate[0]));
            }
        };
    }
}
