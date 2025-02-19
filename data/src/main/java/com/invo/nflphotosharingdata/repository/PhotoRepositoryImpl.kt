package com.invo.nflphotosharingdata.repository

import com.invo.nflphotosharingdata.model.Photo
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor() : PhotoRepository {
    override fun getPhotos(): List<Photo> {
        return listOf(
            Photo(id = 1, url = "https://example.com/nfl1.jpg"),
            Photo(id = 2, url = "https://example.com/nfl2.jpg")
        )
    }
}