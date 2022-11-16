package com.ls.iusta.remote.mappers

interface EntityMapper<M, E> {

    fun mapFromModel(model: M): E
}
