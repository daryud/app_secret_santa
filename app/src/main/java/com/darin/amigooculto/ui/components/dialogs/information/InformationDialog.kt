package com.darin.amigooculto.ui.components.dialogs.information

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.darin.amigooculto.databinding.DialogInformationBinding

class InformationDialog: DialogFragment() {

    private lateinit var binding: DialogInformationBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogInformationBinding.inflate(LayoutInflater.from(context))

        binding.txtInformation.text = message

        binding.btnOk.setOnClickListener {
            dismiss()
        }

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }

    companion object {

        private var message: String = ""

        fun newInstance(message: String): InformationDialog {
            this.message = message
            return InformationDialog()
        }

    }

}