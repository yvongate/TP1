package com.uici.repertoire

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    val id: String,
    val nom: String,
    val prenoms: String,
    val email: String,
    val telephone: String,
    val lieuHabitation: String,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
) : Parcelable
