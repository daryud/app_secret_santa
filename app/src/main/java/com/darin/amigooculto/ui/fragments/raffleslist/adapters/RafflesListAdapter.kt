package com.darin.amigooculto.ui.fragments.raffleslist.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.darin.amigooculto.databinding.RafflesRowAdapterBinding
import com.darin.amigooculto.service.models.objects.RaffledObject
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel
import com.darin.amigooculto.ui.components.dialogs.information.InformationDialog

class RafflesListAdapter(private val activityContext: AppCompatActivity) : RecyclerView.Adapter<RafflesListAdapter.RafflesListViewHolder>() {

    class RafflesListViewHolder(private val binding: RafflesRowAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(raffled: RaffledObject, activityContext: AppCompatActivity) {
            binding.txtParticipantName.text = raffled.participant.name
            binding.txtFriendName.text = raffled.santa.name

            binding.llRafflesRow.setOnLongClickListener {
                InformationDialog.newInstance("Seu amigo oculto é:\n\n${raffled.santa.name}").show(activityContext.supportFragmentManager, "informative_dialog")
                true
            }

        }
    }

    private var raffledList: List<RaffledObject> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RafflesListViewHolder {
        val binding =
            RafflesRowAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RafflesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RafflesListViewHolder, position: Int) {
        holder.bind(raffledList[position], activityContext)
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