package com.darin.amigooculto.ui.fragments.participantlist.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.darin.amigooculto.R
import com.darin.amigooculto.databinding.ActivityMainBinding
import com.darin.amigooculto.databinding.ParticipantRowAdapterBinding
import com.darin.amigooculto.service.models.listeners.IExcludeOrEditListener
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel
import com.darin.amigooculto.ui.components.dialogs.participantoptions.ParticipantOptionsDialog

class ParticipantListAdapter(private val activityContext: AppCompatActivity) :
    RecyclerView.Adapter<ParticipantListAdapter.ParticipantListViewHolder>() {

    class ParticipantListViewHolder(private val binding: ParticipantRowAdapterBinding, private val activityContext: AppCompatActivity) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(participant: ParticipantModel) {
            binding.txtParticipantName.text = participant.name

            binding.llParticipantRow.setOnClickListener {
                ParticipantOptionsDialog.newInstance(participant, object : IExcludeOrEditListener {
                    override fun onExclude() {
                        Toast.makeText(activityContext, "Excluido! Ou não...", Toast.LENGTH_SHORT).show()
                    }

                    override fun onEdit() {
                        Toast.makeText(activityContext, "Editando! Ou não...", Toast.LENGTH_SHORT).show()
                    }
                }).show(activityContext.supportFragmentManager, "dialog")
            }
        }
    }

    private var participantList: List<ParticipantModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantListViewHolder {
        val binding =
            ParticipantRowAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParticipantListViewHolder(binding, activityContext)
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