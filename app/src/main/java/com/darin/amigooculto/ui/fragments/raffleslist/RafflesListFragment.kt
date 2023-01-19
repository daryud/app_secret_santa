package com.darin.amigooculto.ui.fragments.raffleslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.darin.amigooculto.R
import com.darin.amigooculto.databinding.ActivityMainBinding
import com.darin.amigooculto.databinding.FragmentRafflesListBinding
import com.darin.amigooculto.service.constants.ParticipantModelConstants
import com.darin.amigooculto.service.models.objects.RaffledObject
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel
import com.darin.amigooculto.ui.components.dialogs.information.InformationDialog
import com.darin.amigooculto.ui.fragments.participantlist.ParticipantListFragment
import com.darin.amigooculto.ui.fragments.participantlist.viewmodels.ParticipantListViewModel
import com.darin.amigooculto.ui.fragments.raffleslist.adapters.RafflesListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RafflesListFragment : Fragment() {

    private var _binding: FragmentRafflesListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ParticipantListViewModel

    private var raffledList: List<RaffledObject> = listOf()

    private lateinit var adapter: RafflesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ParticipantListViewModel::class.java]

        adapter = RafflesListAdapter(
            requireActivity() as AppCompatActivity,
            object : (ParticipantModel) -> Unit {

                override fun invoke(participant: ParticipantModel) {
                    viewModel.updateParticipantSantaStatus(participant.apply {
                        this.santaStatus = ParticipantModelConstants.SantaStatus.VISUALIZED
                    })
                    raffledList = viewModel.getRaffledList()
                    updateList()
                }

            })

        raffledList = viewModel.getRaffledList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRafflesListBinding.inflate(inflater, container, false)

        val myActivityFloatingButton =
            requireActivity().findViewById<FloatingActionButton>(R.id.flactbtn_add_participant)
        myActivityFloatingButton.visibility = View.GONE

        binding.recviewRaffles.layoutManager = LinearLayoutManager(requireActivity())
        binding.recviewRaffles.adapter = adapter

        binding.btnRevertRaffle.setOnClickListener {
            val success = viewModel.clearSantas()
            if (success) {
                viewModel.resetAllParticipantSantaStatus()
                val fragment = ParticipantListFragment.getInstance()
                setFragmentToParentActivity(fragment)
            } else {
                Toast.makeText(requireActivity(), "Erro ao reverter sorteio!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        updateList()

        InformationDialog.newInstance("Toque e segure em seu nome para revelar seu amigo oculto!\nOu toque no ícone de compartilhamento para enviar o código de revelação!")
            .show(requireActivity().supportFragmentManager, "informative_dialog")

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }

    private fun updateList() {
        adapter.updateList(raffledList)
    }

    private fun setFragmentToParentActivity(fragment: Fragment) {
        val binding = ActivityMainBinding.inflate(LayoutInflater.from(requireActivity()))
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.flMain.id, fragment)
        fragmentTransaction.commit()
    }

    companion object {
        fun newInstance(): RafflesListFragment {
            return RafflesListFragment()
        }
    }

}