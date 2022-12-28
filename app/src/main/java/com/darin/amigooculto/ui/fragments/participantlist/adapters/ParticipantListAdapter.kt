package com.darin.amigooculto.ui.fragments.participantlist.adapters

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darin.amigooculto.R
import com.darin.amigooculto.databinding.ParticipantRowAdapterBinding
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel

class ParticipantListAdapter :
    RecyclerView.Adapter<ParticipantListAdapter.ParticipantListViewHolder>() {

    class ParticipantListViewHolder(private val binding: ParticipantRowAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(participant: ParticipantModel) {
            binding.txtParticipantName.text = participant.name
        }
    }

    private var participantList: List<ParticipantModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantListViewHolder {
        val binding =
            ParticipantRowAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParticipantListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParticipantListViewHolder, position: Int) {
        holder.bind(participantList[position])
    }

    override fun getItemCount(): Int {
        return participantList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<ParticipantModel>) {
        participantList = list
        notifyDataSetChanged()
    }
}