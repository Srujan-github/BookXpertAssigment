package labs.creative.bookxpertassigment.data.remote.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import labs.creative.bookxpertassigment.common.utils.toJson
import labs.creative.bookxpertassigment.data.local.dao.UserActDao
import labs.creative.bookxpertassigment.data.remote.api.UserActService
import labs.creative.bookxpertassigment.data.remote.model.UserActDtoItem
import labs.creative.bookxpertassigment.domain.mappers.toDomain
import labs.creative.bookxpertassigment.domain.model.UserActDetails
import labs.creative.bookxpertassigment.domain.repository.UserActRepository
import labs.creative.bookxpertassigment.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserActRepositoryImpl @Inject constructor(
    private val apiService: UserActService,
    private val userActDao: UserActDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserActRepository {

    override fun getUserActs(): Flow<Resource<List<UserActDetails>>> = flow {
        try {
            val cachedUserActs = userActDao.getAllUserActs()
            if (cachedUserActs.first().isNotEmpty()) {
                emit(Resource.Success(cachedUserActs.first().map { it.toDomain() }))
            } else {
                emit(Resource.Loading)
                val response = apiService.getUserActs().toJson()
                response.forEach { userAct ->
                    insert(userAct)
                }
                emit(Resource.Success(response.map { it.toDomain() }))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }


    override fun getAllUserActs(): Flow<List<UserActDtoItem>> = userActDao.getAllUserActs()

    override suspend fun insert(userAct: UserActDtoItem): Boolean = withContext(ioDispatcher) {
        return@withContext try {
            userActDao.insert(userAct)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun deleteById(actId: Int): Boolean = withContext(ioDispatcher) {
        return@withContext try {
            userActDao.delete(actId)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun updateNameById(
        actId: Int,
        actName: String,
        actAlternativeName: String
    ): Boolean = withContext(ioDispatcher) {
        return@withContext try {
            userActDao.updateNameById(actId, actName, actAlternativeName)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun deleteAll(): Boolean = withContext(ioDispatcher) {
        return@withContext try {
            userActDao.deleteAll()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    companion object {
        const val TAG = "UserActRepositoryImpl"
    }
}