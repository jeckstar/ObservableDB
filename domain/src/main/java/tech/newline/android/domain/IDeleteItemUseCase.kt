package tech.newline.android.domain

import io.reactivex.Completable

interface IDeleteItemUseCase {

    fun delete(dto: ItemDto): Completable
}
