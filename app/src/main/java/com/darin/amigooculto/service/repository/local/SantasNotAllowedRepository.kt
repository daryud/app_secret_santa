package com.darin.amigooculto.service.repository.local

import android.content.Context
import com.darin.amigooculto.service.repository.local.database.SecretSantaDatabase
import com.darin.amigooculto.service.repository.local.databasemodels.SantaNotAllowedModel

class SantasNotAllowedRepository(context: Context) {

    private val database = SecretSantaDatabase.getDatabase(context).santaNotAllowedDAO()

    fun insert(santaNotAllowedModel: SantaNotAllowedModel): Boolean {
        return database.insert(santaNotAllowedModel) > 0
    }

    fun update(santaNotAllowedModel: SantaNotAllowedModel): Boolean {
        return database.update(santaNotAllowedModel) > 0
    }

    fun delete(id: Int): Boolean {
        val santaNotAllowed = SantaNotAllowedModel()
        santaNotAllowed.id = id
        return database.delete(santaNotAllowed) > 0
    }

    fun deleteWhere(participantId: Int, santaNotAllowedId: Int): Boolean {
        return database.deleteWhere(participantId, santaNotAllowedId) > 0
    }

    fun getAllNotAllowed(participantId: Int): List<Int> {
        return database.getAllNotAllowed(participantId)
    }

}