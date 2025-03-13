package labs.creative.bookxpertassigment.di

import android.content.Context
import androidx.room.Room
import labs.creative.bookxpertassigment.BuildConfig
import labs.creative.bookxpertassigment.common.Constants.BASE_URL
import labs.creative.bookxpertassigment.common.Constants.CACHE_SIZE
import labs.creative.bookxpertassigment.data.local.dao.UserActDao
import labs.creative.bookxpertassigment.data.local.database.UserActDatabase
import labs.creative.bookxpertassigment.data.remote.api.UserActService
import labs.creative.bookxpertassigment.data.remote.repository.UserActRepositoryImpl
import labs.creative.bookxpertassigment.domain.repository.UserActRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserActDatabase(@ApplicationContext appContext: Context): UserActDatabase {
        return Room.databaseBuilder(
            appContext,
            UserActDatabase::class.java,
            "user_act_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserActDao(appDatabase: UserActDatabase): UserActDao {
        return appDatabase.userActDao()
    }


    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        cache: Cache
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache {
        val cacheDir = File(context.cacheDir, "http_cache")
        return Cache(cacheDir, CACHE_SIZE)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): UserActService {
        return retrofit.create(UserActService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserActRepository(
        apiService: UserActService,
        userActDao: UserActDao
    ): UserActRepository = UserActRepositoryImpl(apiService, userActDao)

}
