package com.invo.nflphotosharingdomain.usecase

import com.invo.nflphotosharingdata.repository.PhotoRepository
import com.invo.nflphotosharingdomain.model.Photo
import javax.inject.Inject

class GetPhotosUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    operator fun invoke(): List<Photo> = repository.getPhotos().map { Photo(it.id, it.url) }
}