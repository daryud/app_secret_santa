package com.darin.amigooculto.ui.fragments.raffleslist.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darin.amigooculto.databinding.RafflesRowAdapterBinding
import com.darin.amigooculto.service.models.objects.RaffledObject
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel

class RafflesListAdapter : RecyclerView.Adapter<RafflesListAdapter.RafflesListViewHolder>() {

    class RafflesListViewHolder(private val binding: RafflesRowAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(raffled: RaffledObject) {
            binding.txtParticipantName.text = raffled.participant.name
            binding.txtFriendName.text = raffled.santa.name
        }
    }

    private var raffledList: List<RaffledObject> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RafflesListViewHolder {
        val binding =
            RafflesRowAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RafflesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RafflesListViewHolder, position: Int) {
        holder.bind(raffledList[position])
    }

    override fun getItemCount(): Int {
        return raffledList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<RaffledObject>) {
        raffledList = list
        notifyDataSetChanged()
    }

}