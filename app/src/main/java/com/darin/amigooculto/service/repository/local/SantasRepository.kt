package com.darin.amigooculto.service.repository.local

import android.content.Context
import androidx.sqlite.db.SupportSQLiteProgram
import androidx.sqlite.db.SupportSQLiteQuery
import com.darin.amigooculto.service.models.objects.RaffledObject
import com.darin.amigooculto.service.repository.local.database.SecretSantaDatabase
import com.darin.amigooculto.service.repository.local.databasemodels.SantaModel

class SantasRepository(context: Context) {

    private val database = SecretSantaDatabase.getDatabase(context).santaDAO()
    private val participantsDatabase = SecretSantaDatabase.getDatabase(context).participantDAO()

    fun insert(santa: SantaModel): Boolean {
        return database.insert(santa) > 0
    }

    fun insert(santaList: List<SantaModel>): Boolean {
        return database.insert(santaList).isNotEmpty()
    }

    fun clearTable(): Boolean {
        return database.clearTable() > 0
    }

    fun getRaffledList(): List<RaffledObject> {
        val santasList = mutableListOf<RaffledObject>()

        val tableSantas = database.getAllSantas()

        tableSantas.forEach {
            val participant = participantsDatabase.getParticipant(it.participantId)
            val santa = participantsDatabase.getParticipant(it.santaId)

            santasList.add(RaffledObject(participant, santa))
        }

        return santasList
    }

}