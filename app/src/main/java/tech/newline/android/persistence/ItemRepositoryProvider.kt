package tech.newline.android.persistence

import android.content.Context
import tech.newline.android.domain.ItemsRepository
import tech.newline.android.room.RoomItemsDatabase
import tech.newline.android.room.item.RoomsItemRepository

object ItemRepositoryProvider {

    private var instance: ItemsRepository? = null

    fun getInstance(context: Context): ItemsRepository {
        return if (instance == null) {
            instance = RoomsItemRepository(
                RoomItemsDatabase.createDbBuilder(context).build().getRoomItemDao()
            )
            instance!!
        } else {
            instance!!
        }
    }
}