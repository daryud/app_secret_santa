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
import com.darin.amigooculto.databinding.ActivityMainBinding
import com.darin.amigooculto.databinding.FragmentParticipantListBinding
import com.darin.amigooculto.service.models.objects.RaffledObject
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel
import com.darin.amigooculto.service.repository.local.databasemodels.SantaModel
import com.darin.amigooculto.ui.fragments.participantlist.adapters.ParticipantListAdapter
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
            carryOutDraw(participantList)

            val fragment = RafflesListFragment.newInstance()
            setFragmentToParentActivity(fragment)
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

    private fun carryOutDraw(participantList: List<ParticipantModel>) {

        val shuffledList: MutableList<ParticipantModel> = participantList.shuffled() as MutableList<ParticipantModel>

        fun reorderShuffledList(indexOfNext: Int) {
            val lastShuffled = shuffledList.removeLast()
            shuffledList.add(indexOfNext, lastShuffled)
        }

        val raffled = mutableListOf<RaffledObject>()

        for (i in shuffledList.indices) {
            val listOfNotAllowed = viewModel.getListOfNotAllowed(shuffledList[i].id)

            var proceed = false

            while (!proceed) {
                val lastShuffledNotAlloweds = viewModel.getListOfNotAllowed(shuffledList.last().id)
                if(lastShuffledNotAlloweds.contains(shuffledList.first().id)) {
                    reorderShuffledList(i + 1)
                    continue
                }
                if (i != shuffledList.size - 1) {
                    if(!listOfNotAllowed.contains(shuffledList[i + 1].id)) {
                        raffled.add(
                            RaffledObject(
                                shuffledList[i],
                                shuffledList[i + 1]
                            )
                        )
                        proceed = true
                    } else {
                        reorderShuffledList(i + 1)
                    }
                } else {
                    raffled.add(RaffledObject(shuffledList[i], shuffledList[0]))
                    proceed = true
                }
            }
        }

        val santaList = raffled.sortedBy { it.participant.id }.map {
            SantaModel().apply {
                this.participantId = it.participant.id
                this.santaId = it.santa.id
            }
        }

        try {
            viewModel.insertAllSantas(santaList)
        } catch (error: Exception) {
            Toast.makeText(requireActivity(), error.toString(), Toast.LENGTH_SHORT).show()
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