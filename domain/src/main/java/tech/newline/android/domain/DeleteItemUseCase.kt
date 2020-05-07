package tech.newline.android.domain

class DeleteItemUseCase(private val repository: ItemsRepository) : IDeleteItemUseCase {

    override fun delete(dto: ItemDto) {
        repository.delete(dto)
    }
}