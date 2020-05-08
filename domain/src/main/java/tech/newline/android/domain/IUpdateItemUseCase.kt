package tech.newline.android.domain

import io.reactivex.Completable

interface IUpdateItemUseCase {

    fun update(dto: ItemDto): Completable
}
