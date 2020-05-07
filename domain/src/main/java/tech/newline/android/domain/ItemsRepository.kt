package tech.newline.android.domain

import io.reactivex.Flowable
import io.reactivex.Single

interface ItemsRepository {

    fun getById(id: Int): Single<ItemDto>

    fun observeAll(): Flowable<List<ItemDto>>

    fun add(dto: ItemDto)

    fun update(dto: ItemDto)

    fun delete(dto: ItemDto)
}
