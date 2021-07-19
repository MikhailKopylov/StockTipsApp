package ru.amk.core.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.amk.core.MoexCandleService
import ru.amk.core.MoexCandleServiceByPage

class DiContainerCore {

    private val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder().addInterceptor(logger).build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://iss.moex.com/iss/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
//            .addConverterFactory(SimpleXmlConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    val moexCandleServiceByPage: MoexCandleServiceByPage by lazy { retrofit.create(MoexCandleServiceByPage::class.java) }

    val moexCandleService:MoexCandleService = MoexCandleService(moexCandleServiceByPage)
}