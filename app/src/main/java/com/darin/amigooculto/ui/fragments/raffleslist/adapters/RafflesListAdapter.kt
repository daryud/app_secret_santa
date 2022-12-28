package com.darin.amigooculto.ui.fragments.raffleslist.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darin.amigooculto.databinding.RafflesRowAdapterBinding
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel

class RafflesListAdapter : RecyclerView.Adapter<RafflesListAdapter.RafflesListViewHolder>() {

    class RafflesListViewHolder(private val binding: RafflesRowAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(santa: Santa) {
            binding.txtParticipantName.text = santa.participant
            binding.txtFriendName.text = santa.santa
        }
    }

    class Santa(val participant: String, val santa: String)

    private var participantList: List<ParticipantModel> = listOf()
    private var santaList: List<Santa> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RafflesListViewHolder {
        val binding =
            RafflesRowAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RafflesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RafflesListViewHolder, position: Int) {
        holder.bind(santaList[position])
    }

    override fun getItemCount(): Int {
        return santaList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<ParticipantModel>) {
        participantList = list
        santaList = raffle(participantList.shuffled())
        notifyDataSetChanged()
    }

    private fun raffle(participantList: List<ParticipantModel>): List<Santa> {
        val santas = mutableListOf<Santa>()

        for (i in participantList.indices) {
            if(i != participantList.size - 1) {
                santas.add(Santa(participantList[i].name, participantList[i + 1].name))
            } else {
                santas.add(Santa(participantList[i].name, participantList[0].name))
            }
        }

        return santas
    }

}