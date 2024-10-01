package com.example.marsphotos.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Annotating the class as Serializable for Kotlinx Serialization
@Serializable
data class MarsPhoto(
    // Mars photo's unique identifier
    val id: String,

    // SerialName annotation specifies the JSON property name for deserialization
    @SerialName(value = "img_src")
    // URL of the Mars photo image
    val imgSrc: String
)