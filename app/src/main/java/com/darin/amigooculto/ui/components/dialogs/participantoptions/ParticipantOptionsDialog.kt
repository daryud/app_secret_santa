package com.darin.amigooculto.ui.components.dialogs.participantoptions

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.darin.amigooculto.databinding.DialogParticipantOptionsBinding
import com.darin.amigooculto.service.models.listeners.IExcludeOrEditListener
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel

class ParticipantOptionsDialog : DialogFragment() {

    private lateinit var binding: DialogParticipantOptionsBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = DialogParticipantOptionsBinding.inflate(LayoutInflater.from(context))

        binding.txtParticipantName.text = participant?.name

        setListeners()

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }

    private fun setListeners() {
        binding.btnExcludeParticipant.setOnClickListener {
            onClickListeners.onExclude()
            dismiss()
        }

        binding.btnEditParticipant.setOnClickListener {
            onClickListeners.onEdit()
            dismiss()
        }
    }

    companion object {
        private var participant: ParticipantModel? = null
        private lateinit var onClickListeners: IExcludeOrEditListener

        fun newInstance(participant: ParticipantModel, onClickListeners: IExcludeOrEditListener): ParticipantOptionsDialog {
            this.participant = participant
            this.onClickListeners = onClickListeners
            return ParticipantOptionsDialog()
        }
    }

}