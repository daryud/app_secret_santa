package com.darin.amigooculto.ui.fragments.participantlist

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
import com.darin.amigooculto.databinding.FragmentParticipantListBinding
import com.darin.amigooculto.service.models.objects.RaffledObject
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel
import com.darin.amigooculto.service.repository.local.databasemodels.SantaModel
import com.darin.amigooculto.ui.fragments.participantlist.adapters.ParticipantListAdapter
import com.darin.amigooculto.ui.fragments.participantlist.objects.ParticipantModelWithConstraints
import com.darin.amigooculto.ui.fragments.participantlist.viewmodels.ParticipantListViewModel
import com.darin.amigooculto.ui.fragments.raffleslist.RafflesListFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ParticipantListFragment : Fragment() {

    private var _binding: FragmentParticipantListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ParticipantListViewModel

    private lateinit var adapter: ParticipantListAdapter

    private var participantList: List<ParticipantModel> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentParticipantListBinding.inflate(inflater, container, false)

        val myActivityFloatingButton = requireActivity().findViewById<FloatingActionButton>(R.id.flactbtn_add_participant)
        myActivityFloatingButton.visibility = View.VISIBLE

        viewModel = ViewModelProvider(requireActivity())[ParticipantListViewModel::class.java]

        adapter =
            ParticipantListAdapter(requireActivity() as AppCompatActivity, object : () -> Unit {
                override fun invoke() {
                    updateList()
                }
            })

        binding.recviewParticipants.layoutManager = LinearLayoutManager(requireActivity())
        binding.recviewParticipants.adapter = adapter

        binding.btnRaffle.setOnClickListener {
            val proceed = carryOutDraw(participantList)

            if(proceed) {
                val fragment = RafflesListFragment.newInstance()
                setFragmentToParentActivity(fragment)
            }

        }

        updateList()

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }

    fun updateList() {
        participantList = viewModel.getAllParticipants()

        adapter.updateList(participantList)

        if (participantList.isNotEmpty()) {
            binding.txtAddParticipants.visibility = View.GONE
            binding.recviewParticipants.visibility = View.VISIBLE
            if (participantList.count() >= 3) {
                binding.btnRaffle.visibility = View.VISIBLE
            } else {
                binding.btnRaffle.visibility = View.GONE
            }
        } else {
            binding.txtAddParticipants.visibility = View.VISIBLE
            binding.recviewParticipants.visibility = View.GONE
            binding.btnRaffle.visibility = View.GONE
        }
    }

    private fun setFragmentToParentActivity(fragment: Fragment) {
        val binding = ActivityMainBinding.inflate(LayoutInflater.from(requireActivity()))
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.flMain.id, fragment)
        fragmentTransaction.commit()
    }

    private fun getDrawedSantas(participantList: List<ParticipantModel>): List<RaffledObject>? {

        var participantListWithConstraints =  participantList.map {
            ParticipantModelWithConstraints().apply {
                this.id = it.id
                this.name = it.name
                this.constraints = viewModel.getListOfAllowed(it.id)
            }
        }.shuffled().sortedBy { participant -> participant.constraints.size }.toMutableList()

        val sortedIds = mutableListOf<Int>()

        fun reorganizeList() {
            for (participant in participantListWithConstraints) {
                participant.constraints = participant.constraints.filter { constraint -> !sortedIds.contains(constraint) }
            }
            participantListWithConstraints = participantListWithConstraints.sortedBy { participant -> participant.constraints.size }
                .toMutableList()
        }

        val raffled = mutableListOf<RaffledObject>()

        while (participantListWithConstraints.isNotEmpty()) {
            reorganizeList()

            val participant = participantListWithConstraints.removeFirst()

            if (participant.constraints.isNotEmpty()) {
                val sortedId = participant.constraints.random()

                sortedIds.add(sortedId)

                val sant = participantListWithConstraints.find { item -> item.id == sortedId }
                if (sant != null) {
                    sant.constraints = sant.constraints.filter { it != participant.id }
                    raffled.add(RaffledObject(ParticipantModel().apply {
                        this.id = participant.id
                        this.name = participant.name
                    }, ParticipantModel().apply {
                        this.id = sant.id
                        this.name = sant.name
                    }))
                } else {
                    val santForAnotherList = participantList.find { item -> item.id == sortedId }!!
                    raffled.add(RaffledObject(ParticipantModel().apply {
                        this.id = participant.id
                        this.name = participant.name
                    }, ParticipantModel().apply {
                        this.id = santForAnotherList.id
                        this.name = santForAnotherList.name
                    }))
                }

            }
            else return null
        }

        return raffled.sortedBy { it.participant.id }
    }

    private fun carryOutDraw(participantList: List<ParticipantModel>): Boolean {

        val drawedSantas = getDrawedSantas(participantList)

        if (drawedSantas != null) {
            val santaList = drawedSantas.map {
                SantaModel().apply {
                    this.participantId = it.participant.id
                    this.santaId = it.santa.id
                }
            }
            return try {
                viewModel.insertAllSantas(santaList)
                true
            } catch (error: Exception) {
                Toast.makeText(requireActivity(), error.toString(), Toast.LENGTH_SHORT).show()
                false
            }
        } else {
            Toast.makeText(requireActivity(), "Impossível sortear com as atuais restrições de amigos!", Toast.LENGTH_SHORT).show()
            return false
        }
    }

    companion object {

        private lateinit var instance: ParticipantListFragment

        fun newInstance(): ParticipantListFragment {
            instance = ParticipantListFragment()
            return instance
        }

        fun getInstance(): ParticipantListFragment {
            return instance
        }
    }
}