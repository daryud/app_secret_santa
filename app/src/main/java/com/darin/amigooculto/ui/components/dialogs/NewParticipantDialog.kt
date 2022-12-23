package com.darin.amigooculto.ui.components.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.darin.amigooculto.databinding.DialogNewParticipantBinding

class NewParticipantDialog(private val onSubmitClickListener: () -> Unit): DialogFragment() {

    private lateinit var binding: DialogNewParticipantBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewParticipantBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnCancellNewParticipant.setOnClickListener {
            onSubmitClickListener.invoke()
            dialog.hide()
        }

        return dialog
    }

}