package com.darin.amigooculto.service.repository.local.daos

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.DeleteTable
import androidx.room.Insert
import androidx.room.Query
import com.darin.amigooculto.service.models.objects.RaffledObject
import com.darin.amigooculto.service.repository.local.databasemodels.ParticipantModel
import com.darin.amigooculto.service.repository.local.databasemodels.SantaModel

@Dao
interface SantaDAO {

    @Insert
    fun insert(santa: SantaModel): Long

    @Insert
    fun insert(santaList: List<SantaModel>): List<Long>

    @Query("DELETE FROM santas")
    fun clearTable(): Int

    @Query("SELECT * FROM santas")
    fun getAllSantas(): List<SantaModel>

}