package labs.creative.bookxpertassigment.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import labs.creative.bookxpertassigment.data.local.dao.UserActDao
import labs.creative.bookxpertassigment.data.remote.model.UserActDtoItem

@Database(
    entities = [UserActDtoItem::class],
    version = 1
)
abstract class UserActDatabase : RoomDatabase() {
    abstract fun userActDao(): UserActDao
}