package com.joanderson.artbook.repo

import androidx.lifecycle.LiveData
import com.joanderson.artbook.model.ImageResponse
import com.joanderson.artbook.roomdb.Art
import com.joanderson.artbook.util.Resource

interface ArtRepositoryInterface {
    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art: Art)

    fun getArt() : LiveData<List<Art>>

    suspend fun searchImage(imageString: String) : Resource<ImageResponse>

}