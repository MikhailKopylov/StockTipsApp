package ru.amk.core.di

import ru.amk.core.mediator.ProvidesFacade

interface AppWithFacade {

    fun getFacade(): ProvidesFacade
}