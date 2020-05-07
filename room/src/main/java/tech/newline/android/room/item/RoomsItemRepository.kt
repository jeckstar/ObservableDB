package tech.newline.android.room.item

import io.reactivex.Observable
import tech.newline.android.domain.ItemDto
import tech.newline.android.domain.ItemsRepository

class RoomsItemRepository(private val itemDao: IRoomItemDao) : ItemsRepository {

    override fun update(dto: ItemDto) {
        itemDao.update(dto.toEntity(dto))
    }

    override fun delete(dto: ItemDto) {
        itemDao.delete(dto.toEntity(dto))
    }

    override fun getById(id: Int) =
         itemDao.getById(id).map {it.toDomain(it)}

    override fun observeAll() =
         itemDao.getAll()
            .flatMapSingle{ it ->
                Observable.fromIterable(it)
                    .map { it.toDomain(it) }
                    .toList()
            }

    override fun add(dto: ItemDto) {
        val itemEntity = dto.toEntity(dto)
        itemDao.add(itemEntity)
    }
}

