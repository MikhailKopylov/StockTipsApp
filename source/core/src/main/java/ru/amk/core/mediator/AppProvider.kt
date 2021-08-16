package ru.amk.core.mediator

import android.content.Context

interface AppProvider {

    fun provideContext(): Context
}