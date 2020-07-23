package com.enesdokuz.gitrepoapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "Repo")
data class RepoDTO(
    @SerializedName("id")
    @ColumnInfo(name = "id")
    val id: String,
    @SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String,
    @SerializedName("open_issues")
    @ColumnInfo(name = "open_issues")
    val openIssueCount: Long,
    @SerializedName("stargazers_count")
    @ColumnInfo(name = "stargazers_count")
    val starCount: Long,
    @SerializedName("isFavorite")
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false,
    @SerializedName("ownerUsername")
    @ColumnInfo(name = "ownerUsername")
    val ownerUsername: String,
    @SerializedName("ownerAvatarUrl")
    @ColumnInfo(name = "ownerAvatarUrl")
    val ownerAvatarUrl: String
) : Serializable {

    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}
