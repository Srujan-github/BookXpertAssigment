package labs.creative.bookxpertassigment.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import labs.creative.bookxpertassigment.data.remote.model.UserActDtoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface UserActDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userActDtoItem: UserActDtoItem)

    @Query("SELECT * FROM user_act_table")
    fun getAllUserActs(): Flow<List<UserActDtoItem>>

    @Query("DELETE FROM user_act_table WHERE actId = :actId")
    suspend fun delete(actId: Int)

    @Query("UPDATE user_act_table SET actName = :actName, actAlternativeName = :actAlternativeName WHERE actId = :actId")
    suspend fun updateNameById(actId: Int, actName: String, actAlternativeName: String)

    @Query("DELETE FROM user_act_table")
    suspend fun deleteAll()
}