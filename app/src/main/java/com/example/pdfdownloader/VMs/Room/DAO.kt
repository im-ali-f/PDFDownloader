package com.example.pdfdownloader.VMs.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface pdfDAO{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertPDF(roomModel:RoomModel)

    @Query("SELECT * From RoomModel")
    suspend fun GetAllPDF():List<RoomModel>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun UpdatePDF(roomModel:RoomModel)


}
