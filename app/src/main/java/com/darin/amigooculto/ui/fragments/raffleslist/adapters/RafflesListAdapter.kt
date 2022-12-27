package com.darin.amigooculto.ui.fragments.raffleslist.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darin.amigooculto.databinding.RafflesRowAdapterBinding
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel
import kotlin.concurrent.thread

class RafflesListAdapter: RecyclerView.Adapter<RafflesListAdapter.RafflesListViewHolder>() {

    class RafflesListViewHolder(private val binding: RafflesRowAdapterBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(participant: ParticipantModel) {
            binding.txtParticipantName.text = participant.name
            binding.txtFriendName.text = participant.name
        }
    }

    private class Santa(val participant: String, val santa: String)

    private var participantList: List<ParticipantModel> = listOf()
    private var santaList: List<Santa> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RafflesListViewHolder {
        val binding = RafflesRowAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RafflesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RafflesListViewHolder, position: Int) {
        holder.bind(participantList[position])
    }

    override fun getItemCount(): Int {
        return participantList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<ParticipantModel>) {
        participantList = list
        santaList = raffle(participantList)
        notifyDataSetChanged()
    }

    private fun raffle(participantList: List<ParticipantModel>): List<Santa> {
        val santas = mutableListOf<Santa>()

        for (i in participantList.indices) {
            do {
                var done = false
                val value = participantList.random()
                if(value.id != participantList[i].id && !santas.any { j -> j.santa == value.name || santas.any { it.participant == j.santa  && it.santa == value.name } }) {
                    santas.add(Santa(participantList[i].name, value.name))
                    done = true
                }
            } while (!done)
        }


        return santas
    }

}