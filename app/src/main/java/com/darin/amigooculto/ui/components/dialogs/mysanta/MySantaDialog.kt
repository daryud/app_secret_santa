package com.darin.amigooculto.ui.components.dialogs.mysanta

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.DialogFragment
import com.darin.amigooculto.databinding.DialogMySantaBinding
import com.darin.amigooculto.ui.components.dialogs.mysanta.listeners.IOnRevealSanta

class MySantaDialog: DialogFragment() {

    private lateinit var binding: DialogMySantaBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = DialogMySantaBinding.inflate(LayoutInflater.from(context))

        binding.txtTitle.text = participant
        binding.txtSantaName.text = santa
        binding.txtSantaNameMask.setOnClickListener {
            it.visibility = View.GONE
            listeners.onReveal()
        }

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.TOP)
        dialog.window!!.attributes.y = 185

        return dialog
    }

    companion object {

        private var participant = ""
        private var santa = ""
        private lateinit var listeners: IOnRevealSanta

        fun newInstance(participant: String, santa: String, listeners: IOnRevealSanta): MySantaDialog {
            this.participant = participant
            this.santa = santa
            this.listeners = listeners
            return MySantaDialog()
        }

    }

}