package com.darin.amigooculto.ui.components.dialogs.newparticipant.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.darin.amigooculto.service.repository.local.ParticipantsRepository
import com.darin.amigooculto.service.repository.local.SantasNotAllowedRepository
import com.darin.amigooculto.service.repository.local.database.SecretSantaDatabase
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel
import com.darin.amigooculto.service.repository.local.databasemodels.SantaNotAllowedModel

class NewParticipantViewModel(application: Application): AndroidViewModel(application) {

    private val repository = ParticipantsRepository(application.applicationContext)

    fun setParticipant(participant: ParticipantModel): Boolean {
        return repository.insert(participant)
    }

    fun updateParticipant(participant: ParticipantModel): Boolean {
        return repository.update(participant)
    }

}