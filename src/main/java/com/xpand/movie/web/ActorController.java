package com.xpand.movie.web;

import com.xpand.movie.actor.ActorDto;
import com.xpand.movie.actor.ActorMapper;
import com.xpand.movie.actor.ActorService;
import com.xpand.movie.actor.filter.ActorFilter;
import com.xpand.movie.utils.message.Message;
import com.xpand.movie.utils.message.MessageBuilder;
import com.xpand.movie.utils.pagination.PageWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.xpand.movie.utils.message.Internationalization.*;
import static com.xpand.movie.utils.message.Internationalization.UPDATE_SUCCESSFUL;

@RestController
@RequestMapping("/api/actors")
public class ActorController {

    private final Logger logger = LoggerFactory.getLogger(ActorController.class);

    @Autowired
    private ActorService actorService;

    @Autowired
    private ActorMapper actorMapper;

    @Autowired
    private MessageBuilder<ActorDto> messageBuilder;

    @GetMapping
    PageWrapper<ActorDto> getActorsPaginated(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size,
                                             ActorFilter actorFilter){
        logger.info("Get a paginated list of actors");

        return new PageWrapper<>(
                actorService.getActors(actorFilter, PageRequest.of(page, size))
                        .map(actorMapper::actorToActorDto)
        );
    }

    @GetMapping("/{id}")
    ActorDto getActorById(@PathVariable int id){
        logger.info("Get actor with id {}", id);
        return actorMapper.actorToActorDto(actorService.getActorById(id));
    }

    @PostMapping
    Message<ActorDto> createActor(@RequestBody ActorDto actor){
        logger.info("Create actor");

        ActorDto actorCreated = actorMapper.actorToActorDto(
                actorService.createActor(actorMapper.actorDtoToActor(actor))
        );

        logger.info("The actor was successfully created with id {}", actorCreated.getId());
        return messageBuilder.success(HttpStatus.CREATED, actorCreated, CREATE_SUCCESSFUL.getLabel(), ACTOR.getLabel());
    }

    @PutMapping("/{id}")
    Message<ActorDto> updateActor(@PathVariable int id, @RequestBody ActorDto actor){
        logger.info("Update actor with id {}", id);

        actor.setId(id);
        ActorDto actorUpdated = actorMapper.actorToActorDto(
                actorService.updateActor(actorMapper.actorDtoToActor(actor))
        );

        logger.info("The actor with id {} was successfully updated", id);
        return messageBuilder.success(HttpStatus.OK, actorUpdated, UPDATE_SUCCESSFUL.getLabel(), ACTOR.getLabel());
    }

    @DeleteMapping("/{id}")
    Message<ActorDto> deleteActorById(@PathVariable int id){
        logger.info("Delete actor with id {}", id);

        actorService.deleteActorById(id);

        logger.info("The actor with id {} was successfully deleted", id);
        return messageBuilder.success(HttpStatus.OK, null, DELETE_SUCCESSFUL.getLabel(), ACTOR.getLabel());
    }

}
