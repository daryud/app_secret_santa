package com.darin.amigooculto.ui.fragments.participantlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.darin.amigooculto.databinding.ActivityMainBinding
import com.darin.amigooculto.databinding.FragmentParticipantListBinding
import com.darin.amigooculto.service.models.objects.RaffledObject
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel
import com.darin.amigooculto.service.repository.local.databasemodels.SantaModel
import com.darin.amigooculto.ui.fragments.participantlist.adapters.ParticipantListAdapter
import com.darin.amigooculto.ui.fragments.participantlist.objects.ParticipantModelWithConstraints
import com.darin.amigooculto.ui.fragments.participantlist.viewmodels.ParticipantListViewModel
import com.darin.amigooculto.ui.fragments.raffleslist.RafflesListFragment

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

    private fun generateValidPermutation(participantListWithConstraints: MutableList<ParticipantModelWithConstraints>, participantListWithConstraintsSize: Int): List<ParticipantModel>? {

        if(participantListWithConstraintsSize == 1) {

            var proceed = true

            for (index in participantListWithConstraints.indices) {
                if(index < participantListWithConstraints.size - 1) {
                    if(participantListWithConstraints[index].constraints.contains(participantListWithConstraints[index + 1].id)) {
                        proceed = false
                        break
                    }
                } else {
                    if(participantListWithConstraints[index].constraints.contains(participantListWithConstraints[0].id)) {
                        proceed = false
                        break
                    }
                }
            }

            return if(proceed) {
                participantListWithConstraints.map {
                    ParticipantModel().apply {
                        this.id = it.id
                        this.name = it.name
                    }
                }
            } else {
                null
            }

        }

        for (index in 0..participantListWithConstraintsSize) {
            val finalValue = generateValidPermutation(participantListWithConstraints, participantListWithConstraintsSize - 1)

            if(finalValue != null) {
                return  finalValue
            }

            if (participantListWithConstraintsSize % 2 == 1) {
                val aux = participantListWithConstraints[0]
                participantListWithConstraints[0] = participantListWithConstraints[participantListWithConstraintsSize - 1]
                participantListWithConstraints[participantListWithConstraintsSize - 1] = aux
            } else {
                val aux = participantListWithConstraints[index]
                participantListWithConstraints[index] = participantListWithConstraints[participantListWithConstraintsSize - 1]
                participantListWithConstraints[participantListWithConstraintsSize - 1] = aux
            }

        }

        return null

    }

    private fun carryOutDraw(participantList: List<ParticipantModel>): Boolean {

        val shuffledList: MutableList<ParticipantModel> = participantList.shuffled() as MutableList<ParticipantModel>

        val validPermutation = generateValidPermutation(shuffledList.map {
            ParticipantModelWithConstraints().apply {
                this.id = it.id
                this.name = it.name
                this.constraints = viewModel.getListOfNotAllowed(it.id)
            }
        } as MutableList<ParticipantModelWithConstraints>, shuffledList.size)

        val raffled = mutableListOf<RaffledObject>()

        if(validPermutation != null) {

            for (index in validPermutation.indices) {

                if(index < validPermutation.size - 1) {
                    raffled.add(RaffledObject(validPermutation[index], validPermutation[index + 1]))
                } else {
                    raffled.add(RaffledObject(validPermutation[index], validPermutation[0]))
                }

            }

            val santaList = raffled.sortedBy { it.participant.id }.map {
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