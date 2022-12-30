package com.darin.amigooculto.ui.fragments.participantlist.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Handler.Callback
import android.os.Message
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.darin.amigooculto.R
import com.darin.amigooculto.databinding.ActivityMainBinding
import com.darin.amigooculto.databinding.ParticipantRowAdapterBinding
import com.darin.amigooculto.service.models.listeners.IConcludeOrCancelListener
import com.darin.amigooculto.service.models.listeners.IExcludeOrEditListener
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel
import com.darin.amigooculto.ui.components.dialogs.newparticipant.NewParticipantDialog
import com.darin.amigooculto.ui.components.dialogs.participantoptions.ParticipantOptionsDialog

class ParticipantListAdapter(private val activityContext: AppCompatActivity, private val parentUpdateList: () -> Unit) :
    RecyclerView.Adapter<ParticipantListAdapter.ParticipantListViewHolder>() {

    class ParticipantListViewHolder(private val binding: ParticipantRowAdapterBinding, private val activityContext: AppCompatActivity, private val parentUpdateList: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(participant: ParticipantModel) {
            binding.txtParticipantName.text = participant.name

            binding.llParticipantRow.setOnClickListener {
                ParticipantOptionsDialog.newInstance(participant, object : IExcludeOrEditListener {
                    override fun onExclude() {
                        parentUpdateList.invoke()
                    }

                    override fun onEdit() {
                        NewParticipantDialog.newInstance(object : IConcludeOrCancelListener {
                            override fun onConclude() {
                                Toast.makeText(activityContext, "Alterado com sucesso!", Toast.LENGTH_SHORT).show()
                                parentUpdateList.invoke()
                            }

                            override fun onCancel() {}

                        }, participant).show(activityContext.supportFragmentManager, "dialog")
                    }
                }).show(activityContext.supportFragmentManager, "dialog")
            }
        }
    }

    private var participantList: List<ParticipantModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantListViewHolder {
        val binding =
            ParticipantRowAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParticipantListViewHolder(binding, activityContext, parentUpdateList)
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