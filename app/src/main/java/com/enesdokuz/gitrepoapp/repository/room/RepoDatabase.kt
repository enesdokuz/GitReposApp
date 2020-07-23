package com.enesdokuz.gitrepoapp.repository.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.enesdokuz.gitrepoapp.model.Owner
import com.enesdokuz.gitrepoapp.model.RepoDTO

@Database(entities = [RepoDTO::class], version = 1, exportSchema = false)
abstract class RepoDatabase : RoomDatabase() {

    abstract fun repoDao(): RepoDAO

    companion object {

        @Volatile
        private var instance: RepoDatabase? = null
        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, RepoDatabase::class.java, "repoDatabase"
        ).build()
    }
}