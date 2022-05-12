package ru.amk.core.utils

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun io(): Scheduler
    fun computation():Scheduler
    fun result():Scheduler
}