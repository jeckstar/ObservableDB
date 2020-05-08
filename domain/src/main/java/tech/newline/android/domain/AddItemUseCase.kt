package tech.newline.android.domain

class AddItemUseCase(private val repository: ItemsRepository): IAddItemUseCase {

    override fun add(dto: ItemDto) = repository.add(dto)

}
