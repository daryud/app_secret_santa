package com.darin.amigooculto.ui.fragments.participantlist.objects

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

class ParticipantModelWithConstraints {

    var id: Int = 0

    var name: String = ""

    var constraints: List<Int> = listOf()

}