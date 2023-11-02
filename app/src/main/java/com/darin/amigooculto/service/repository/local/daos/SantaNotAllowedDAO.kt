package com.darin.amigooculto.service.repository.local.daos

import androidx.room.*
import com.darin.amigooculto.service.repository.local.databasemodels.SantaNotAllowedModel

@Dao
interface SantaNotAllowedDAO {

    @Insert
    fun insert(santaNotAllowedModel: SantaNotAllowedModel): Long

    @Update
    fun update(santaNotAllowedModel: SantaNotAllowedModel): Int

    @Delete
    fun delete(santaNotAllowedModel: SantaNotAllowedModel): Int

    @Query("DELETE FROM santas_not_allowed WHERE participant_id = :participantId AND not_allowed_id = :santaNotAllowedId")
    fun deleteWhere(participantId: Int, santaNotAllowedId: Int): Int

    @Query("SELECT not_allowed_id FROM santas_not_allowed WHERE participant_id = :participantId")
    fun getAllNotAllowed(participantId: Int): List<Int>

    @Query("SELECT p.id FROM participants p WHERE p.id NOT IN " +
            "(SELECT s.not_allowed_id FROM santas_not_allowed s WHERE s.participant_id = :participantId)")
    fun getAllAllowed(participantId: Int): List<Int>

}