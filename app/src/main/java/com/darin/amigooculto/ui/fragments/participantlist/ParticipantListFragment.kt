package com.darin.amigooculto.ui.fragments.participantlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.darin.amigooculto.R
import com.darin.amigooculto.databinding.FragmentParticipantListBinding
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel
import com.darin.amigooculto.ui.fragments.participantlist.adapters.ParticipantListAdapter

class ParticipantListFragment : Fragment() {

    private var _binding: FragmentParticipantListBinding? = null
    private val binding get() = _binding!!

    private val adapter = ParticipantListAdapter()

    private var participantList: List<ParticipantModel> = listOf<ParticipantModel>(
        ParticipantModel().apply {
            this.id = 1
            this.name = "João"
        },
        ParticipantModel().apply {
            this.id = 2
            this.name = "Carlos"
        },
        ParticipantModel().apply {
            this.id = 3
            this.name = "Joaquin"
        },
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentParticipantListBinding.inflate(inflater, container, false)

        binding.recviewParticipants.layoutManager = LinearLayoutManager(context)
        binding.recviewParticipants.adapter = adapter

//      Apenas para fins de teste
        adapter.updateList(participantList)

        if(participantList.isNotEmpty()) {
            binding.txtAddParticipants.visibility = View.GONE
            binding.recviewParticipants.visibility = View.VISIBLE
        } else {
            binding.txtAddParticipants.visibility = View.VISIBLE
            binding.recviewParticipants.visibility = View.GONE
        }

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }
}