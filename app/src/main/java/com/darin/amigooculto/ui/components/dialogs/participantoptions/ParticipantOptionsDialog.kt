package com.darin.amigooculto.ui.components.dialogs.participantoptions

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.darin.amigooculto.databinding.DialogParticipantOptionsBinding
import com.darin.amigooculto.service.models.listeners.IExcludeOrEditListener
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel
import com.darin.amigooculto.ui.components.dialogs.participantoptions.adapters.FriendsAdapter
import com.darin.amigooculto.ui.components.dialogs.participantoptions.viewmodels.ParticipantOptionsViewModel

class ParticipantOptionsDialog : DialogFragment() {

    private lateinit var binding: DialogParticipantOptionsBinding
    private lateinit var viewModel: ParticipantOptionsViewModel

    private val adapter = FriendsAdapter()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = DialogParticipantOptionsBinding.inflate(LayoutInflater.from(context))
        viewModel = ViewModelProvider(requireActivity())[ParticipantOptionsViewModel::class.java]

        binding.txtParticipantName.text = participant.name

        setListeners()
        fillFriends()

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }

    private fun setListeners() {
        binding.btnExcludeParticipant.setOnClickListener {
            val success = viewModel.deleteParticipant(participant)
            if(success) {
                onClickListeners.onExclude()
                dismiss()
            } else {
                Toast.makeText(requireActivity(), "Erro ao excluir participante!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnEditParticipant.setOnClickListener {
            onClickListeners.onEdit()
            dismiss()
        }
    }

    private fun fillFriends() {
        binding.recviewFriends.layoutManager = LinearLayoutManager(context)
        binding.recviewFriends.adapter = adapter

        val friends = viewModel.getAllWhereIdIsDifferent(participant.id)

        adapter.updateList(friends)
    }

    companion object {
        private lateinit var participant: ParticipantModel
        private lateinit var onClickListeners: IExcludeOrEditListener

        fun newInstance(participant: ParticipantModel, onClickListeners: IExcludeOrEditListener): ParticipantOptionsDialog {
            this.participant = participant
            this.onClickListeners = onClickListeners
            return ParticipantOptionsDialog()
        }
    }

}