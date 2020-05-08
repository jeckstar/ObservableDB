package tech.newline.android.domain

import io.reactivex.Completable

interface IAddItemUseCase {

    fun add(dto: ItemDto): Completable
}
