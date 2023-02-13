package com.xpand.movie;

import com.xpand.movie.actor.Actor;
import com.xpand.movie.actor.ActorRepository;
import com.xpand.movie.actor.ActorServiceImpl;
import com.xpand.movie.actor.filter.ActorFilter;
import com.xpand.movie.actor.filter.ActorSpecification;
import com.xpand.movie.exception.BadRequestException;
import com.xpand.movie.exception.ResourceNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ActorServiceTest {
    @Mock
    private ActorRepository actorRepository;

    @InjectMocks
    private ActorServiceImpl actorService;

    @Test
    public void getActors_withoutFilters_returnActorList() {
        ActorFilter actorFilter = new ActorFilter();
        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor(1, "Alba Batista", LocalDate.of(1997,7,10), "Female"));
        actors.add(new Actor(2, "Diogo Morgado", LocalDate.of(1981,1,17), "Male"));

        given(actorRepository.findAll(ActorSpecification.filter(actorFilter))).willReturn(actors);

        actorService.getActors(actorFilter);
        assertThat(actors.size()).isEqualTo(2);
    }

    @Test
    public void getActorById_whenExistingId_returnActor() {
        Actor actor = new Actor(1, "Alba Batista", LocalDate.of(1997,7,10), "Female");

        when(actorRepository.findById(1)).thenReturn(Optional.of(actor));
        Actor actorDb = actorService.getActorById(1);

        assertThat(actorDb.getName()).isEqualTo(actor.getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getActorById_whenNonExistingId_throwsNotFoundException() {
        when(actorRepository.findById(1)).thenReturn(Optional.empty());
        actorService.getActorById(1);
    }

    @Test
    public void createActor_whenValidActor_returnActorCreated() {
        Actor actor = new Actor(1, "Alba Batista", LocalDate.of(1997,7,10), "Female");

        when(actorRepository.save(ArgumentMatchers.any(Actor.class))).thenReturn(actor);
        Actor actorCreated = actorService.createActor(actor);
        assertThat(actorCreated.getName()).isSameAs(actor.getName());
    }

    @Test(expected = BadRequestException.class)
    public void createActor_whenInvalidName_throwsBadRequestException() {
        Actor actor = new Actor(1, null, LocalDate.of(1997,7,10), "Female");
        actorService.createActor(actor);
    }

    @Test(expected = BadRequestException.class)
    public void createActor_whenInvalidGender_throwsBadRequestException() {
        Actor actor = new Actor(1, "Alba Batista", LocalDate.of(1997,7,10), "Other");
        actorService.createActor(actor);
    }

    @Test
    public void updateActor_whenValidActor_returnActorUpdated() {
        Actor actor = new Actor(1, "Alba Batista", LocalDate.of(1997,7,10), "Female");
        Actor actorToUpdate = new Actor(1, "Alba Batista Updated", LocalDate.of(1997,7,10), "Female");

        given(actorRepository.findById(1)).willReturn(Optional.of(actor));
        when(actorRepository.save(ArgumentMatchers.any(Actor.class))).thenReturn(actorToUpdate);
        Actor actorUpdated = actorService.updateActor(actor);

        assertThat(actorUpdated.getName()).isEqualTo(actorToUpdate.getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateActor_whenNonExistingId_throwsNotFoundException() {
        Actor actorToUpdate = new Actor(1, "Alba Batista", LocalDate.of(1997,7,10), "Female");
        given(actorRepository.findById(1)).willReturn(Optional.empty());
        actorService.updateActor(actorToUpdate);
    }

    @Test(expected = BadRequestException.class)
    public void updateActor_whenInvalidName_throwsBadRequestException() {
        Actor actor = new Actor(1, "Alba Batista", LocalDate.of(1997,7,10), "Female");
        Actor actorToUpdate = new Actor(1, null, LocalDate.of(1997,7,10), "Female");
        given(actorRepository.findById(1)).willReturn(Optional.of(actor));
        actorService.updateActor(actorToUpdate);
    }

    @Test(expected = BadRequestException.class)
    public void updateActor_whenInvalidGender_throwsBadRequestException() {
        Actor actor = new Actor(1, "Alba Batista", LocalDate.of(1997,7,10), "Female");
        Actor actorToUpdate = new Actor(1, "Alba Batista Update", LocalDate.of(1997,7,10), "Other");
        given(actorRepository.findById(1)).willReturn(Optional.of(actor));
        actorService.updateActor(actorToUpdate);
    }

    @Test
    public void deleteActor_whenExistingId_returnSuccessMessage() {
        Actor actor = new Actor(1, "Alba Batista", LocalDate.of(1997,7,10), "Female");
        when(actorRepository.findById(1)).thenReturn(Optional.of(actor));
        actorService.deleteActorById(1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteActorById_whenNonExistingId_throwsNotFoundException() {
        when(actorRepository.findById(1)).thenReturn(Optional.empty());
        actorService.deleteActorById(1);
    }

}

