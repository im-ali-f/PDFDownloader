package com.example.pdfdownloader.VMs.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



@Database(
    entities = [RoomModel::class],
    version = 3,
    exportSchema = true
)
abstract class db : RoomDatabase() {

    //inja interface ha moarefi shan
    abstract fun PDFDAO(): pdfDAO

    // end moarefi
    companion object {
        @Volatile
        private var INSTANCE: db? = null

        fun getInstance(context: Context): db {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        db::class.java,
                        "localDB"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }

        }
    }
}