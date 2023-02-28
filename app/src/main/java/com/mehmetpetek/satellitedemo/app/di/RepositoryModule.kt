package com.mehmetpetek.satellitedemo.app.di

import android.content.Context
import com.mehmetpetek.satellitedemo.data.repository.SatelliteRepositoryImp
import com.mehmetpetek.satellitedemo.domain.repository.SatelliteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSatelliteRepository(
        @ApplicationContext appContext: Context
    ): SatelliteRepository = SatelliteRepositoryImp(appContext)
}