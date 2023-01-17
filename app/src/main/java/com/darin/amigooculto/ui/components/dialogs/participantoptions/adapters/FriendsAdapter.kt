package com.darin.amigooculto.ui.components.dialogs.participantoptions.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darin.amigooculto.databinding.FriendsRowAdapterBinding
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel
import com.darin.amigooculto.ui.components.dialogs.participantoptions.listeners.IOnClickCheck

class FriendsAdapter : RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder>() {

    class FriendsViewHolder(private val binding: FriendsRowAdapterBinding, private val listeners: IOnClickCheck, private val listOfNotAllowed: List<Int>) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(friend: ParticipantModel) {
            binding.txtParticipantName.text = friend.name

            binding.checkboxAllowed.setOnClickListener {
                if(binding.checkboxAllowed.isChecked) {
                    listeners.onCheck(binding.checkboxAllowed, friend.id)
                } else {
                    listeners.onUncheck(binding.checkboxAllowed, friend.id)
                }
            }

            if(listOfNotAllowed.contains(friend.id)) {
                binding.checkboxAllowed.isChecked = false
            }

        }
    }

    private lateinit var friendsList: List<ParticipantModel>

    private lateinit var listeners: IOnClickCheck
    private lateinit var listeOfNotAllowed: List<Int>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val binding =
            FriendsRowAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendsViewHolder(binding, listeners, listeOfNotAllowed)
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

    fun attachListeners(listeners: IOnClickCheck) {
        this.listeners = listeners
    }

    fun attachListOfNotAllowed(list: List<Int>) {
        this.listeOfNotAllowed = list
    }

}