package com.xpand.movie.actor;

import com.xpand.movie.actor.filter.ActorFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ActorService {

    /**
     * Gets a filtered actor list
     * @param actorFilter Actor Filter
     * @return Actor List
     */
    List<Actor> getActors(ActorFilter actorFilter);

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
