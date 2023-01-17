package com.darin.amigooculto.ui.components.dialogs.participantoptions.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.darin.amigooculto.service.repository.local.ParticipantsRepository
import com.darin.amigooculto.service.repository.local.SantasNotAllowedRepository
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel
import com.darin.amigooculto.service.repository.local.databasemodels.SantaNotAllowedModel

class ParticipantOptionsViewModel(application: Application): AndroidViewModel(application) {

    private val participantRepository = ParticipantsRepository(application)
    private val santaNotAllowedRepository = SantasNotAllowedRepository(application)

    fun deleteParticipant(participant: ParticipantModel): Boolean {
        return participantRepository.delete(participant.id)
    }

    fun getAllWhereIdIsDifferent(id: Int): List<ParticipantModel> {
        return participantRepository.getAllWhereIdIsDifferent(id)
    }

    fun deleteSantaNotAllowed(participantId: Int, santaNotAllowedId: Int): Boolean {
        return santaNotAllowedRepository.deleteWhere(participantId, santaNotAllowedId)
    }

    fun insertSantaNotAllowed(santaNotAllowed: SantaNotAllowedModel): Boolean {
        return santaNotAllowedRepository.insert(santaNotAllowed)
    }

    fun getListOfNotAllowed(id: Int): List<Int> {
        return santaNotAllowedRepository.getAllNotAllowed(id)
    }

}