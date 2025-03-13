package labs.creative.bookxpertassigment.domain.repository

import labs.creative.bookxpertassigment.data.remote.model.UserActDtoItem
import labs.creative.bookxpertassigment.domain.model.UserActDetails
import labs.creative.bookxpertassigment.common.Resource
import kotlinx.coroutines.flow.Flow

interface UserActRepository {
    fun getUserActs(): Flow<Resource<List<UserActDetails>>>
    fun getAllUserActs(): Flow<List<UserActDtoItem>>
    suspend fun insert(userAct: UserActDtoItem): Boolean
    suspend fun deleteById(actId: Int): Boolean
    suspend fun updateNameById(actId: Int, actName: String, actAlternativeName: String): Boolean
    suspend fun deleteAll(): Boolean
}