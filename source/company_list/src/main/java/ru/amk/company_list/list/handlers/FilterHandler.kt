package ru.amk.company_list.list.handlers

import io.reactivex.subjects.BehaviorSubject

const val DEFAULT_FILTER_QUERY = ""

object FilterHandler  {

    val filterObserve = BehaviorSubject.createDefault(DEFAULT_FILTER_QUERY)

    fun filterCompany(query:String){
        filterObserve.onNext(query)
    }
}