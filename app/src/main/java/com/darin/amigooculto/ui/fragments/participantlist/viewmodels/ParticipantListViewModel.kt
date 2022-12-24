package com.darin.amigooculto.ui.fragments.participantlist.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.darin.amigooculto.service.repository.local.ParticipantsRepository
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel

class ParticipantListViewModel(application: Application): AndroidViewModel(application) {

    private val repository = ParticipantsRepository(application.applicationContext)

    fun getParticipants(): List<ParticipantModel> {
        return repository.getAll()
    }

}