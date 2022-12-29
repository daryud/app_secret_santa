package com.darin.amigooculto.ui.fragments.raffleslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.darin.amigooculto.R
import com.darin.amigooculto.databinding.ActivityMainBinding
import com.darin.amigooculto.databinding.FragmentRafflesListBinding
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel
import com.darin.amigooculto.ui.fragments.participantlist.ParticipantListFragment
import com.darin.amigooculto.ui.fragments.participantlist.viewmodels.ParticipantListViewModel
import com.darin.amigooculto.ui.fragments.raffleslist.adapters.RafflesListAdapter

class RafflesListFragment : Fragment() {

    private var _binding: FragmentRafflesListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ParticipantListViewModel

    private var participantList: List<ParticipantModel> = listOf()

    private val adapter = RafflesListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ParticipantListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRafflesListBinding.inflate(inflater, container, false)

        binding.recviewRaffles.layoutManager = LinearLayoutManager(requireActivity())
        binding.recviewRaffles.adapter = adapter

        binding.btnRevertRaffle.setOnClickListener {
            val fragment = ParticipantListFragment.newInstance()
            setFragmentToParentActivity(fragment)
        }

        updateList()

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }

    private fun updateList() {
        participantList = viewModel.getParticipants()

        adapter.updateList(participantList)

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