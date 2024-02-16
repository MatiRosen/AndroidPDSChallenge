package io.github.matirosen.pdschallenge.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.matirosen.pdschallenge.BuildConfig
import io.github.matirosen.pdschallenge.api.WorldClockApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitForWorldClockApi(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideWorldClockApiInterface(retrofit: Retrofit): WorldClockApiInterface {
        return retrofit.create(WorldClockApiInterface::class.java)
    }
}