package com.darin.amigooculto.service.repository.local.databasemodels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.darin.amigooculto.service.constants.ParticipantModelConstants

@Entity(tableName = "participants")
class ParticipantModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "name")
    var name: String = ""

    @ColumnInfo(name = "santa_status", defaultValue = ParticipantModelConstants.SantaStatus.NOT_VISUALIZED)
    var santaStatus: String? = null

}