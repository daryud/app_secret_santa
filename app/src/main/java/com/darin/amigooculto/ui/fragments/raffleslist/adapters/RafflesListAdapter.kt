package com.darin.amigooculto.ui.fragments.raffleslist.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.darin.amigooculto.databinding.RafflesRowAdapterBinding
import com.darin.amigooculto.service.constants.ParticipantModelConstants
import com.darin.amigooculto.service.models.objects.RaffledObject
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel
import com.darin.amigooculto.service.utils.AESEncryption
import com.darin.amigooculto.ui.components.dialogs.mysanta.MySantaDialog
import com.darin.amigooculto.ui.components.dialogs.mysanta.listeners.IOnRevealSanta

class RafflesListAdapter(private val activityContext: AppCompatActivity, private val setSantaStatus: (participant: ParticipantModel) -> Unit) : RecyclerView.Adapter<RafflesListAdapter.RafflesListViewHolder>() {

    class RafflesListViewHolder(private val binding: RafflesRowAdapterBinding, private val setSantaStatus: (participant: ParticipantModel) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(raffled: RaffledObject, activityContext: AppCompatActivity) {
            binding.txtParticipantName.text = raffled.participant.name
            binding.txtSantaStatus.text = raffled.participant.santaStatus

            binding.ivShareSantaCode.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT, "Use o código a seguir para revelar o seu amigo secreto: ${AESEncryption.encrypt(raffled.santa.name)}")
                intent.type = "text/plain"
                activityContext.startActivity(Intent.createChooser(intent, "share_to"))
            }

            if(raffled.participant.santaStatus == ParticipantModelConstants.SantaStatus.NOT_VISUALIZED){

                binding.llRafflesRow.setOnLongClickListener {
                    MySantaDialog.newInstance(raffled.participant.name, raffled.santa.name, onRevealSanta(raffled.participant)).show(activityContext.supportFragmentManager, "my_santa_dialog")
                    true
                }

            } else {

                binding.llRafflesRow.setOnLongClickListener {
                    false
                }

            }

        }

        private fun onRevealSanta(participant: ParticipantModel): IOnRevealSanta {
            return object : IOnRevealSanta {
                override fun onReveal() {
                    setSantaStatus.invoke(participant)
                }
            }
        }

    }

    private var raffledList: List<RaffledObject> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RafflesListViewHolder {
        val binding =
            RafflesRowAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RafflesListViewHolder(binding, setSantaStatus)
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