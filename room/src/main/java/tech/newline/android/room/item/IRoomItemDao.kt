package tech.newline.android.room.item

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface IRoomItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(entity: RoomItemEntity)

    @Query("SELECT * FROM my_items")
    fun getAll(): Flowable<List<RoomItemEntity>>

    @Query("SELECT * FROM my_items WHERE id == :id")
    fun getById(id: Int): Single<RoomItemEntity>

    @Update
    fun update(dto: RoomItemEntity)

    @Delete
    fun delete(dto: RoomItemEntity)

}
