package com.mehmetpetek.satellitedemo.app.di

import android.content.Context
import androidx.room.Room
import com.mehmetpetek.satellitedemo.app.BuildConfig
import com.mehmetpetek.satellitedemo.data.local.db.SatelliteDetailDatabase
import com.mehmetpetek.satellitedemo.data.local.model.SatelliteDetail
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provide(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, SatelliteDetailDatabase::class.java, BuildConfig.APPLICATION_ID + ".db"
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(db: SatelliteDetailDatabase) = db.satelliteDetailDao()

    @Provides
    fun provideEntity() = SatelliteDetail(id = 0)


}