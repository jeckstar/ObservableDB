package tech.newline.android.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import tech.newline.android.room.RoomItemsDatabase.Companion.VERSION
import tech.newline.android.room.item.IRoomItemDao
import tech.newline.android.room.item.RoomItemEntity

@Database(
    entities = [RoomItemEntity::class],
    version = VERSION
)
abstract class RoomItemsDatabase : RoomDatabase() {
    companion object {
        internal const val VERSION = 1
        private const val DB_NAME = "observable_db"
        fun createDbBuilder(context: Context) =
            Room.databaseBuilder(context, RoomItemsDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
    }

    abstract fun getRoomItemDao(): IRoomItemDao
}