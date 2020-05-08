package tech.newline.android.domain

import io.reactivex.Maybe

class GetItemUseCase(private val repository: ItemsRepository) : IGetItemUseCase {

    override fun getById(id: Int): Maybe<ItemDto> {
        return repository.getById(id)
    }
}