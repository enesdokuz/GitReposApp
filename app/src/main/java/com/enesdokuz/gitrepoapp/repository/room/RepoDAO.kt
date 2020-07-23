package com.enesdokuz.gitrepoapp.repository.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.enesdokuz.gitrepoapp.model.RepoDTO

@Dao
interface RepoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg repos: RepoDTO) : List<Long>

    @Query("SELECT * FROM Repo")
    suspend fun getAllRepos(): List<RepoDTO>

    @Query("DELETE FROM Repo")
    suspend fun deleteAllRepos()

    @Query("UPDATE Repo SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateRepo(id: String, isFavorite: Boolean)
}