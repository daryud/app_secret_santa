package com.darin.amigooculto.ui.components.dialogs.participantoptions.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darin.amigooculto.databinding.FriendsRowAdapterBinding
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel

class FriendsAdapter : RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder>() {

    class FriendsViewHolder(private val binding: FriendsRowAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(friend: ParticipantModel) {
            binding.txtParticipantName.text = friend.name
        }
    }

    private lateinit var friendsList: List<ParticipantModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val binding =
            FriendsRowAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        holder.bind(friendsList[position])
    }

    override fun getItemCount(): Int {
        return friendsList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<ParticipantModel>) {
        friendsList = list
        notifyDataSetChanged()
    }

}