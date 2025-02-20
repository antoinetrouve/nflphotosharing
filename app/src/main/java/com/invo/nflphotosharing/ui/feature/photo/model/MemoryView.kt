package com.invo.nflphotosharing.ui.feature.photo.model

import androidx.annotation.DrawableRes
import com.invo.nflphotosharingdomain.model.Memory

data class MemoryView(
    @DrawableRes val id: Int,
    val imageName: String,
    val userName: String
)

fun Memory.toView(): MemoryView {
    return MemoryView(
        id = id,
        imageName = imageName,
        userName = userName
    )
}

fun List<Memory>.toViewList(): List<MemoryView> {
    return this.map { it.toView() }
}