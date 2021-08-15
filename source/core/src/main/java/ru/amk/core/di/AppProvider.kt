package ru.amk.core.di

import android.content.Context

interface AppProvider {

    fun provideContext():Context
}