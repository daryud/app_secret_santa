package com.darin.amigooculto.ui.components.dialogs.newparticipant

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.darin.amigooculto.databinding.DialogNewParticipantBinding
import com.darin.amigooculto.service.constants.ParticipantModelConstants
import com.darin.amigooculto.service.models.listeners.IConcludeOrCancelListener
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel
import com.darin.amigooculto.ui.components.dialogs.newparticipant.viewmodels.NewParticipantViewModel

class NewParticipantDialog : DialogFragment() {

    private lateinit var binding: DialogNewParticipantBinding

    private lateinit var viewModel: NewParticipantViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewParticipantBinding.inflate(LayoutInflater.from(context))
        viewModel = ViewModelProvider(requireActivity())[NewParticipantViewModel::class.java]

        if(currentParticipant != null) {
            binding.edtParticipantName.setText(currentParticipant!!.name)
        }

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnCancellNewParticipant.setOnClickListener {
            onClickListeners.onCancel()
            dismiss()
        }

        binding.btnConcludeNewParticipant.setOnClickListener {
            val participantName = binding.edtParticipantName.text.toString()

            if (participantName.isNotEmpty()) {
                var success = false
                if (currentParticipant == null) {
                    success = viewModel.setParticipant(ParticipantModel().apply {
                        this.name = participantName
                        this.santaStatus = ParticipantModelConstants.SantaStatus.NOT_VISUALIZED
                    })
                } else {
                    success = viewModel.updateParticipant(currentParticipant!!.apply {
                        this.name = participantName
                    })
                }

                if (success) {
                    onClickListeners.onConclude()
                    dismiss()
                }
            } else {
                Toast.makeText(context, "Informe um nome!", Toast.LENGTH_SHORT).show()
            }
        }

        return dialog
    }

    companion object {
        private lateinit var onClickListeners: IConcludeOrCancelListener
        private var currentParticipant: ParticipantModel? = null

        fun newInstance(onClickListeners: IConcludeOrCancelListener): NewParticipantDialog {
            this.onClickListeners = onClickListeners
            this.currentParticipant = null
            return NewParticipantDialog()
        }

        fun newInstance(
            onClickListeners: IConcludeOrCancelListener,
            participant: ParticipantModel
        ): NewParticipantDialog {
            this.onClickListeners = onClickListeners
            this.currentParticipant = participant
            return NewParticipantDialog()
        }
    }

}