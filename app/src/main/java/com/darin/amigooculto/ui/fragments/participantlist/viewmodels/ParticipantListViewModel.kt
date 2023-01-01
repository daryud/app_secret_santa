package com.darin.amigooculto.ui.fragments.participantlist.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.darin.amigooculto.service.models.objects.RaffledObject
import com.darin.amigooculto.service.repository.local.ParticipantsRepository
import com.darin.amigooculto.service.repository.local.SantasRepository
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel
import com.darin.amigooculto.service.repository.local.databasemodels.SantaModel

class ParticipantListViewModel(application: Application): AndroidViewModel(application) {

    private val participantsRepository = ParticipantsRepository(application.applicationContext)
    private val santasRepository = SantasRepository(application.applicationContext)

    fun getAllParticipants(): List<ParticipantModel> {
        return participantsRepository.getAll()
    }

    fun insertAllSantas(santaList: List<SantaModel>): Boolean {
        return santasRepository.insert(santaList)
    }

    fun clearSantas(): Boolean {
        return santasRepository.clearTable()
    }

    fun getRaffledList(): List<RaffledObject> {
        return santasRepository.getRaffledList()
    }
}