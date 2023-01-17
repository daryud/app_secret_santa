package com.darin.amigooculto.service.repository.local.databasemodels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "santas", foreignKeys = [
        ForeignKey(
            entity = ParticipantModel::class,
            childColumns = ["participant_id"],
            parentColumns = ["id"]
        ),
        ForeignKey(
            entity = ParticipantModel::class,
            childColumns = ["santa_id"],
            parentColumns = ["id"]
        )
    ],
    indices = [
        Index("participant_id"),
        Index("santa_id")
    ]
)
class SantaModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "participant_id")
    var participantId: Int = 0

    @ColumnInfo(name = "santa_id")
    var santaId: Int = 0

}