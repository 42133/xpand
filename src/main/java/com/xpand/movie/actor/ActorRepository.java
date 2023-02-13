package com.xpand.movie.actor;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ActorRepository extends PagingAndSortingRepository<Actor, Integer>, JpaSpecificationExecutor<Actor> {
}
