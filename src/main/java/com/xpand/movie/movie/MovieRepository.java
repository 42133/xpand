package com.xpand.movie.movie;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MovieRepository extends PagingAndSortingRepository<Movie, Integer>, JpaSpecificationExecutor<Movie> {

}


