package com.darin.amigooculto.ui.components.dialogs.revealsanta

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.darin.amigooculto.databinding.DialogRevealSantaBinding
import com.darin.amigooculto.service.utils.AESEncryption

class RevealSantaDialog: DialogFragment() {

    private lateinit var binding: DialogRevealSantaBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = DialogRevealSantaBinding.inflate(LayoutInflater.from(context))

        binding.btnCancellReveal.setOnClickListener {
            dismiss()
        }

        binding.btnRevealSanta.setOnClickListener {
            val code = binding.edtSantasCode.text.toString().trim()

            if(code != "") {
                val santaName = AESEncryption.decrypt(code)
                if(santaName != null) {
                    binding.txtSantaName.text = santaName
                    binding.edtSantasCode.visibility = View.GONE
                    binding.btnCancellReveal.visibility = View.GONE
                    binding.btnRevealSanta.visibility = View.GONE
                    binding.llcRevealedSanta.visibility = View.VISIBLE

                    val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.edtSantasCode.windowToken, 0)
                } else {
                    Toast.makeText(requireActivity(), "Este código não é válido!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireActivity(), "Insira um código de amigo!", Toast.LENGTH_SHORT).show()
            }
        }

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }

    companion object {

        fun newInstance(): RevealSantaDialog {
            return RevealSantaDialog()
        }

    }

}