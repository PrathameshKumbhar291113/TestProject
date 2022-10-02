package com.example.testproject.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Data(
    val avatar: String?,
    val email: String?,
    val first_name: String?,
    val id: Int?,
    val last_name: String?
) : Parcelable