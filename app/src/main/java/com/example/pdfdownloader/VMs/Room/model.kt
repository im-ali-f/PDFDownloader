package com.example.pdfdownloader.VMs.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RoomModel")
data class RoomModel(
    @PrimaryKey(autoGenerate = true)
    val id :Int,
    val name : String,
    val url : String,
    var downloadedList : String,

    )

