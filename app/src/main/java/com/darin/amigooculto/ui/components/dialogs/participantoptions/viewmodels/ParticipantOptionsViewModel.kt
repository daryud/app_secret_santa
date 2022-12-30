package com.darin.amigooculto.ui.components.dialogs.participantoptions.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.darin.amigooculto.service.repository.local.ParticipantsRepository
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel

class ParticipantOptionsViewModel(application: Application): AndroidViewModel(application) {

    val repository = ParticipantsRepository(application)

    fun deleteParticipant(participant: ParticipantModel): Boolean {
        return repository.delete(participant.id)
    }

}