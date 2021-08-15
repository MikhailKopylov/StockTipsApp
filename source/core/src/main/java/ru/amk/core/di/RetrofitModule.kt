package ru.amk.core.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.amk.core.moex_model.company.MoexCandleService

@Module
class RetrofitModule {

    @Provides
    fun provideLogger(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun provideClient(logger: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(logger).build()

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://iss.moex.com/iss/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
//            .addConverterFactory(SimpleXmlConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    @Provides
    fun provideMoexCandleService(retrofit: Retrofit):MoexCandleService = retrofit.create(MoexCandleService::class.java)


}