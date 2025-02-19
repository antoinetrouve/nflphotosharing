package com.invo.nflphotosharingdata.repository

import com.invo.nflphotosharingdata.model.Photo

interface PhotoRepository {
    fun getPhotos(): List<Photo>
}