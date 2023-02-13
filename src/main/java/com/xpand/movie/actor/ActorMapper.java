package com.xpand.movie.actor;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActorMapper {

    Actor actorDtoToActor(ActorDto actorDto);

    ActorDto actorToActorDto(Actor actor);

}