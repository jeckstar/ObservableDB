package tech.newline.android.domain

class UpdateItemUseCase(private val repository: ItemsRepository) : IUpdateItemUseCase {

    override fun update(dto: ItemDto) =
        repository.update(dto)
}