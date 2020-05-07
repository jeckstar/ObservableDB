package tech.newline.android.domain

import io.reactivex.Flowable

interface IObserveAllItemsFacade {
    fun observeItems(): Flowable<List<ItemDto>>
}