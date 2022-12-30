package com.darin.amigooculto.service.repository.local

import android.content.Context
import com.darin.amigooculto.service.repository.local.database.SecretSantaDatabase
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel

class ParticipantsRepository(context: Context) {

    private val database = SecretSantaDatabase.getDatabase(context).participantDAO()

    fun insert(participant: ParticipantModel): Boolean {
        return database.insert(participant) > 0
    }

    fun update(participant: ParticipantModel): Boolean {
        return database.update(participant) > 0
    }

    fun delete(id: Int): Boolean {
        val participant = ParticipantModel()
        participant.id = id
        return database.delete(participant) > 0
    }

    fun getAll(): List<ParticipantModel> {
        return database.getAll()
    }

}