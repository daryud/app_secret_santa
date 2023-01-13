package com.darin.amigooculto.service.repository.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabase.Callback
import androidx.sqlite.db.SupportSQLiteDatabase
import com.darin.amigooculto.service.repository.local.daos.ParticipantDAO
import com.darin.amigooculto.service.repository.local.daos.SantaDAO
import com.darin.amigooculto.service.repository.local.daos.SantaNotAllowedDAO
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel
import com.darin.amigooculto.service.repository.local.databasemodels.SantaModel
import com.darin.amigooculto.service.repository.local.databasemodels.SantaNotAllowedModel

@Database(entities = [ParticipantModel::class, SantaModel::class, SantaNotAllowedModel::class], exportSchema = false, version = 1)
abstract class SecretSantaDatabase : RoomDatabase() {

    abstract fun participantDAO(): ParticipantDAO
    abstract fun santaDAO(): SantaDAO
    abstract fun santaNotAllowedDAO(): SantaNotAllowedDAO

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