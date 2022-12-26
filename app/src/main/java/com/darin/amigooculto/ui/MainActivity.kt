package com.darin.amigooculto.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.darin.amigooculto.databinding.ActivityMainBinding
import com.darin.amigooculto.service.models.listeners.IConcludeOrCancelListener
import com.darin.amigooculto.ui.components.dialogs.newparticipant.NewParticipantDialog
import com.darin.amigooculto.ui.fragments.participantlist.ParticipantListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var participantListFragment: ParticipantListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.includeCustomAppbar.toolbarMain)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.flactbtnAddParticipant.setOnClickListener {
            onClickFlactbtnNewParticipant()
        }

        participantListFragment = ParticipantListFragment()

        setFragment(participantListFragment)
    }

    private fun onClickFlactbtnNewParticipant() {
        NewParticipantDialog(object : IConcludeOrCancelListener {
            override fun onConclude() {
                Toast.makeText(applicationContext, "Adicionado com sucesso!", Toast.LENGTH_SHORT).show()
                participantListFragment.updateList()
            }

            override fun onCancel() {}

        }).show(supportFragmentManager, "dialog")
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.flMain.id, fragment)
        fragmentTransaction.commit()
    }

}