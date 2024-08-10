package com.hulusimsek.a3_artbook.repo

import androidx.lifecycle.LiveData
import com.hulusimsek.a3_artbook.model.ImageResponse
import com.hulusimsek.a3_artbook.roomdb.Art
import com.hulusimsek.a3_artbook.util.Resource

interface ArtRepositoryInterface {
    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art: Art)

    fun getArt(): LiveData<List<Art>>

    suspend fun searchImage(imageString: String): Resource<ImageResponse>
}