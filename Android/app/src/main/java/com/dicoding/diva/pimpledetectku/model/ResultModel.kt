package com.dicoding.diva.pimpledetectku.model

import com.dicoding.diva.pimpledetectku.api.AcneItemsResult

data class ResultModel(
    val id : Int,
    val type : String,
    val name : String,
    val description : String,
    val cause : String,
    val solution : String,
    val image : String,
    val reference : String,
    val created_at : String,
    val updated_at: String,
    val success : Boolean,
    val message: String
)
