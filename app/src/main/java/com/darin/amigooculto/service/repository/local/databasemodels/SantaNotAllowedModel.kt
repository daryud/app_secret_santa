package com.darin.amigooculto.service.repository.local.databasemodels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "santas_not_allowed", foreignKeys = [
        ForeignKey(
            entity = ParticipantModel::class,
            childColumns = ["participant_id"],
            parentColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ParticipantModel::class,
            childColumns = ["not_allowed_id"],
            parentColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("participant_id"),
        Index("not_allowed_id")
    ]
)
class SantaNotAllowedModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "participant_id")
    var participantId: Int = 0

    @ColumnInfo(name = "not_allowed_id")
    var notAllowedId: Int = 0

}