package com.hulusimsek.a3_artbook.repo

import androidx.lifecycle.LiveData
import com.hulusimsek.a3_artbook.api.RetrofitAPI
import com.hulusimsek.a3_artbook.model.ImageResponse
import com.hulusimsek.a3_artbook.roomdb.Art
import com.hulusimsek.a3_artbook.roomdb.ArtDao
import com.hulusimsek.a3_artbook.util.Resource
import retrofit2.Retrofit
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao : ArtDao,
    private val retrofitAPI: RetrofitAPI
) : ArtRepositoryInterface {
    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observerArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = retrofitAPI.imageSearch(imageString)
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            }
            else{
                Resource.error("Error",null)
            }
        } catch (e: Exception) {
            Resource.error("No data!",null)
        }
    }
}