package com.xpand.movie.actor.filter;

import com.xpand.movie.actor.Actor;
import com.xpand.movie.movie.Movie;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class ActorSpecification {
    public static Specification<Actor> filter(ActorFilter actorFilter) {
        return new Specification<Actor>() {

            @Override
            public Predicate toPredicate(Root<Actor> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();

                if (actorFilter.getMovieId() != null) {
                    Join<Actor, Movie> actorMovieJoin = root.join("movies", JoinType.LEFT);
                    predicateList.add(actorMovieJoin.isNotNull());
                    predicateList.add(cb.equal(actorMovieJoin.get("id"), actorFilter.getMovieId()));
                }

                return cb.and(predicateList.toArray(new Predicate[0]));
            }
        };
    }
}
