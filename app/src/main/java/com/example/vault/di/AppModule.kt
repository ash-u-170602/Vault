package com.example.vault.di

import androidx.room.Room
import com.example.vault.VaultApplication
import com.example.vault.database.room.RoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesRoomDB(): RoomDB {
        return Room.databaseBuilder(
            VaultApplication.instance?.baseContext!!,
            RoomDB::class.java,
            "room_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}