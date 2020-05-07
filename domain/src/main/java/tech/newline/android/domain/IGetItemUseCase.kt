package tech.newline.android.domain

import io.reactivex.Single

interface IGetItemUseCase {
    fun getById(id: Int): Single<ItemDto>
}