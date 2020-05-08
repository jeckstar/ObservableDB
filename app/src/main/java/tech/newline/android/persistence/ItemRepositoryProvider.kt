package tech.newline.android.persistence

import android.content.Context
import tech.newline.android.domain.ItemsRepository

object ItemRepositoryProvider {

    private var instance: ItemsRepository? = null

    fun getInstance(context: Context): ItemsRepository {
        return if (instance == null) {
            instance = InMemoryItemRepository()
            instance!!
        } else {
            instance!!
        }
    }
}