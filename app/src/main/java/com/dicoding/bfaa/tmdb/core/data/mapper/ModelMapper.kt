package com.dicoding.bfaa.tmdb.core.data.mapper

interface ModelMapper<Domain, Entity, Response> {
    fun mapFromResponse(response: Response) : Domain

    fun mapFromResponses(responses: List<Response>): List<Domain>

    fun mapFromEntity(entity: Entity) : Domain

    fun mapFromEntities(entities: List<Entity>): List<Domain>

    fun mapToEntity(model: Domain) : Entity
}