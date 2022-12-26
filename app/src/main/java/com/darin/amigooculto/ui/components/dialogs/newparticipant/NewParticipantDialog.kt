package com.darin.amigooculto.ui.components.dialogs.newparticipant

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.darin.amigooculto.databinding.DialogNewParticipantBinding
import com.darin.amigooculto.service.models.listeners.IConcludeOrCancelListener
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel
import com.darin.amigooculto.ui.components.dialogs.newparticipant.viewmodels.NewParticipantViewModel

class NewParticipantDialog(private val onClickListeners: IConcludeOrCancelListener): DialogFragment() {

    private lateinit var binding: DialogNewParticipantBinding

    private lateinit var viewModel: NewParticipantViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewParticipantBinding.inflate(LayoutInflater.from(context))
        viewModel = ViewModelProvider(requireActivity())[NewParticipantViewModel::class.java]

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

            if(participantName.isNotEmpty()) {
                val success = viewModel.setParticipant(ParticipantModel().apply {
                    this.name = participantName
                })
                if(success) {
                    onClickListeners.onConclude()
                    dismiss()
                }
            } else {
                Toast.makeText(context, "Informe um nome!", Toast.LENGTH_SHORT).show()
            }
        }

        return dialog
    }

}