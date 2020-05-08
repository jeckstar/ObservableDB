package tech.newline.android.room.item

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface IRoomItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(entity: RoomItemEntity): Completable

    @Query("SELECT * FROM my_items")
    fun getAll(): Flowable<List<RoomItemEntity>>

    @Query("SELECT * FROM my_items WHERE id == :id")
    fun getById(id: Int): Maybe<RoomItemEntity>

    @Update
    fun update(dto: RoomItemEntity): Completable

    @Delete
    fun delete(dto: RoomItemEntity): Completable

}
