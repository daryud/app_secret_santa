package com.darin.amigooculto.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.darin.amigooculto.databinding.ActivityMainBinding
import com.darin.amigooculto.ui.components.dialogs.NewParticipantDialog

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.includeCustomAppbar.toolbarMain)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.flactbtnAddParticipant.setOnClickListener {
            onClickFlactbtnNewParticipant()
        }
    }

    private fun onClickFlactbtnNewParticipant() {
        NewParticipantDialog {
            Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show()
        }.show(supportFragmentManager, "dialog")
    }

}