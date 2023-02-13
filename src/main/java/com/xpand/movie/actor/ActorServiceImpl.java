package com.xpand.movie.actor;

import com.xpand.movie.actor.filter.ActorFilter;
import com.xpand.movie.actor.filter.ActorSpecification;
import com.xpand.movie.exception.BadRequestException;
import com.xpand.movie.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.xpand.movie.utils.message.Internationalization.*;

@Service
public class ActorServiceImpl implements ActorService{

    private final Logger logger = LoggerFactory.getLogger(ActorService.class);

    @Autowired
    private ActorRepository actorRepository;

    @Override
    public Page<Actor> getActors(ActorFilter actorFilter, Pageable pageable) {
        return actorRepository.findAll(
                ActorSpecification.filter(actorFilter),
                pageable
        );
    }

    @Override
    public Actor getActorById(int actorId) {
        return actorRepository.findById(actorId)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND.getLabel(), ACTOR.getLabel()));
    }

    @Override
    public Actor createActor(Actor actor) {
        validateActorObject(actor);

        try{
            return actorRepository.save(actor);
        } catch(Exception ex){
            logger.error("Error creating actor - Exception {}", ex.getMessage());
            throw new BadRequestException(OPERATION_FAILED.getLabel());
        }
    }

    @Override
    public Actor updateActor(Actor actor) {
        //Validate if exists actor with that id
        this.getActorById(actor.getId());

        validateActorObject(actor);

        try{
            return actorRepository.save(actor);
        } catch(Exception ex){
            logger.error("Error updating actor - Exception {}", ex.getMessage());
            throw new BadRequestException(OPERATION_FAILED.getLabel());
        }
    }

    @Override
    public void deleteActorById(int actorId) {
        //Validate if exists actor with that id
        this.getActorById(actorId);

        try {
            actorRepository.deleteById(actorId);
        } catch(Exception ex){
            logger.error("Error deleting actor with id {} - Exception: {}", actorId, ex.getMessage());
            throw new BadRequestException(OPERATION_FAILED.getLabel());
        }
    }

    private void validateActorObject(Actor actor) {
        if(actor.getName() == null || actor.getName().isBlank()){
            logger.error("Name {} is invalid", actor.getName());
            throw new BadRequestException(NOT_EMPTY_NAME.getLabel());
        }

        boolean isInvalidGender = ActorGender.fromType(actor.getGender()).isEmpty();
        if(isInvalidGender){
            logger.error("Gender {} is invalid", actor.getGender());
            throw new BadRequestException(INVALID_GENDER.getLabel());
        }

    }

}
