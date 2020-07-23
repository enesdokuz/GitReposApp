package com.enesdokuz.gitrepoapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Repo(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("open_issues")
    val openIssueCount: Long,
    @SerializedName("stargazers_count")
    val starCount: Long,
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("isFavorite")
    var isFavorite: Boolean = false
) : Serializable
