package com.erikriosetiawan.githubstarter.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("login")
    var username: String = "",
    @SerializedName("id")
    var id: Long = -1,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("location")
    var location: String? = null,
    @SerializedName("public_repos")
    var publicRepositories: Int? = null,
    @SerializedName("public_gists")
    var publicGists: Int? = null,
    @SerializedName("company")
    var company: String? = null,
    @SerializedName("followers")
    var followers: Int? = null,
    @SerializedName("following")
    var following: Int? = null,
    @SerializedName("avatar_url")
    var avatarUrl: String = "",
    @SerializedName("type")
    var type: String = "User",
    @SerializedName("html_url")
    var htmlUrl: String = "",
    @SerializedName("blog")
    var blog: String? = null,
    @SerializedName("bio")
    var bio: String? = null,
    @SerializedName("hireable")
    var hireable: Boolean? = false,
    @SerializedName("created_at")
    var createdAt: String? = null,
    @SerializedName("updated_at")
    var updatedAt: String? = null
) : Parcelable
