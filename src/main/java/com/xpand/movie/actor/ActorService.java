package com.xpand.movie.actor;

import com.xpand.movie.actor.filter.ActorFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActorService {

    /**
     * Gets a paginated and filtered actor list
     * @param actorFilter Actor Filter
     * @param pageable Page and Size
     * @return Actor List
     */
    Page<Actor> getActors(ActorFilter actorFilter, Pageable pageable);

    /**
     * Get actor with id actorId
     * @param actorId Actor Id
     * @return Actor with actorId
     */
    Actor getActorById(int actorId);

    /**
     * Creates a actor with the data passed in the actor object
     * @param actor Actor to create
     * @return Actor created
     */
    Actor createActor(Actor actor);

    /**
     * Updates the actor with the data passed in the actor object
     * @param actor Actor to update
     * @return Actor updated
     */
    Actor updateActor(Actor actor);

    /**
     * Delete actor with id actorId
     * @param actorId Actor Id
     */
    void deleteActorById(int actorId);
}
