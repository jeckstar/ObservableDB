package tech.newline.android.domain

import io.reactivex.Maybe

interface IGetItemUseCase {
    fun getById(id: Int): Maybe<ItemDto>
}