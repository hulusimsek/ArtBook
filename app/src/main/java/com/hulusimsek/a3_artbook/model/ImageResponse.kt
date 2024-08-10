package com.hulusimsek.a3_artbook.model

data class ImageResponse(
    val hits: List<ImageResult>,
    val total: Int,
    val totalHits: Int

)
