package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Repos::class], version = 1)
 abstract class Database : RoomDatabase(){
    abstract fun ReposDao(): ReposDao

    companion object {

        @Volatile
        private var instance: com.example.myapplication.database.Database? = null

        fun getDatabaseInstance(context: Context) : com.example.myapplication.database.Database {
            if(instance == null){
                synchronized(this){
                    instance = Room.databaseBuilder(context.applicationContext, com.example.myapplication.database.Database::class.java, "database").build()
                }
            }
            return instance!!
        }

    }
}