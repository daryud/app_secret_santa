package com.darin.amigooculto.service.repository.local

import android.content.Context
import androidx.room.Transaction
import com.darin.amigooculto.service.repository.local.database.SecretSantaDatabase
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel
import com.darin.amigooculto.service.repository.local.databasemodels.SantaNotAllowedModel

class ParticipantsRepository(context: Context) {

    private val database = SecretSantaDatabase.getDatabase(context).participantDAO()
    private val databaseSantaNotAllowed = SecretSantaDatabase.getDatabase(context).santaNotAllowedDAO()

    @Transaction
    fun insert(participant: ParticipantModel): Boolean {
        val participantId = database.insert(participant).toInt()
        if (participantId > 0) {
            val santaNotAllowed = SantaNotAllowedModel().apply {
                this.participantId = participantId
                this.notAllowedId = participantId
            }
            return databaseSantaNotAllowed.insert(santaNotAllowed) > 0
        }
        return false
    }

    fun update(participant: ParticipantModel): Boolean {
        return database.update(participant) > 0
    }

    fun delete(id: Int): Boolean {
        val participant = ParticipantModel()
        participant.id = id
        return database.delete(participant) > 0
    }

    fun getParticipant(id: Int): ParticipantModel {
        return database.getParticipant(id)
    }

    fun getAll(): List<ParticipantModel> {
        return database.getAll()
    }

    fun getAllWhereIdIsDifferent(id: Int): List<ParticipantModel> {
        return database.getAllWhereIdIsDifferent(id)
    }

    fun resetAllParticipantSantaStatus(): Boolean {
        return database.resetAllParticipantSantaStatus() > 0
    }

}