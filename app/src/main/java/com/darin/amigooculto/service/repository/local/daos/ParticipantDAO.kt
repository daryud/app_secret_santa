package com.darin.amigooculto.service.repository.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel

@Dao
interface ParticipantDAO {

    @Insert
    fun insert(participant: ParticipantModel): Long

    @Update
    fun update(participant: ParticipantModel): Int

    @Delete
    fun delete(participant: ParticipantModel): Int

    @Query("SELECT * FROM participants WHERE id = :id")
    fun getParticipant(id: Int): ParticipantModel

    @Query("SELECT * FROM participants")
    fun getAll(): List<ParticipantModel>

}