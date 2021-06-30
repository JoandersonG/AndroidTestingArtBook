package com.joanderson.artbook.repo

import androidx.lifecycle.LiveData
import com.joanderson.artbook.api.RetrofitAPI
import com.joanderson.artbook.model.ImageResponse
import com.joanderson.artbook.roomdb.Art
import com.joanderson.artbook.roomdb.ArtDao
import com.joanderson.artbook.util.Resource
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject


class ArtRepository @Inject constructor(
    private val artDao: ArtDao,
    private val retrofitAPI: RetrofitAPI
) : ArtRepositoryInterface{
    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = retrofitAPI.imageSearch(imageString)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("Error", null)
            }
        } catch (e: Exception) {
            Resource.error("No data!", null)
        }
    }
}