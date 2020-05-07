package tech.newline.android.domain

import io.reactivex.Flowable

class ObserveAllItemsFacade(private val repository: ItemsRepository) : IObserveAllItemsFacade {

    override fun observeItems(): Flowable<List<ItemDto>> = repository.observeAll()

}