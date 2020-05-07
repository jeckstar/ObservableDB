package tech.newline.android.domain

import io.reactivex.Single

class GetItemUseCase(private val repository: ItemsRepository) : IGetItemUseCase {

    override fun getById(id: Int): Single<ItemDto> {
        return repository.getById(id)
    }
}