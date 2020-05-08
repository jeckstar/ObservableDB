package tech.newline.android.domain

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface ItemsRepository {

    fun getById(id: Int): Single<ItemDto>

    fun observeAll(): Flowable<List<ItemDto>>

    fun add(dto: ItemDto): Completable

    fun update(dto: ItemDto): Completable

    fun delete(dto: ItemDto): Completable
}
