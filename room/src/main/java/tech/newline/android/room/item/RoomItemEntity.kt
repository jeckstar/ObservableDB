package tech.newline.android.room.item

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import tech.newline.android.domain.ItemDto

@Entity(tableName = "my_items")
class RoomItemEntity(
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "content") var content: String
)

fun ItemDto.toEntity(dto: ItemDto) =
    RoomItemEntity(
        dto.id,
        dto.content
    )

fun RoomItemEntity.toDomain(itemEntity: RoomItemEntity) =
    ItemDto(
        itemEntity.id,
        itemEntity.content
    )