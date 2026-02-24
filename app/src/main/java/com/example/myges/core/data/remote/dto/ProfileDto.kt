package com.example.myges.core.data.remote.dto

import com.example.myges.core.domain.model.Profile
import com.google.gson.annotations.SerializedName

data class ProfileDto(
    @SerializedName("uid") val uid: String?,
    @SerializedName("firstname") val firstname: String?,
    @SerializedName("lastname") val lastname: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("photo_url") val photoUrl: String?
) {
    fun toDomain() = Profile(
        uid = uid.orEmpty(),
        firstname = firstname.orEmpty(),
        lastname = lastname.orEmpty(),
        email = email.orEmpty(),
        photoUrl = photoUrl
    )
}
