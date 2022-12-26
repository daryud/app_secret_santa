package com.darin.amigooculto.service.repository.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.darin.amigooculto.service.repository.local.daos.ParticipantDAO
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel

@Database(entities = [ParticipantModel::class], version = 1)
abstract class SecretSantaDatabase : RoomDatabase() {

    abstract fun participantDAO(): ParticipantDAO

    companion object {
        private lateinit var instance: SecretSantaDatabase

        fun getDatabase(context: Context): SecretSantaDatabase {
            if (!this::instance.isInitialized) {
                synchronized(SecretSantaDatabase::class) {
                    instance = Room.databaseBuilder(
                        context,
                        SecretSantaDatabase::class.java,
                        "secretsantadb"
                    ).allowMainThreadQueries().build()
                }
            }
            return instance
        }
    }

}