package `in`.ev.pediadata.di.module

import `in`.ev.pediadata.BuildConfig
import `in`.ev.pediadata.di.qualifier.CatNetwork
import `in`.ev.pediadata.di.qualifier.DogNetwork
import `in`.ev.pediadata.di.qualifier.QualifierType
import `in`.ev.pediadata.network.interceptors.NetworkInterceptor
import `in`.ev.pediadata.network.interceptors.OfflineCacheInterceptor
import `in`.ev.pediadata.utils.NetworkConstants
import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(cache: Cache, networkInterceptor: NetworkInterceptor,
                            offlineCacheInterceptor: OfflineCacheInterceptor):
            OkHttpClient {
        val httpClient = OkHttpClient().newBuilder()
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            return httpClient.addInterceptor(interceptor).build()
        }
        httpClient.addNetworkInterceptor(networkInterceptor)
        httpClient.addInterceptor(offlineCacheInterceptor)
        return httpClient.build()
    }

    @Provides
    @Singleton
    @DogNetwork(QualifierType.RETROFIT)
    fun provideDogRetrofitClient(
        okHttpClient: OkHttpClient,
        converterFactory: MoshiConverterFactory,
        @DogNetwork(QualifierType.URL) url: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    @Singleton
    @CatNetwork(QualifierType.RETROFIT)
    fun provideCatRetrofitClient(
        okHttpClient: OkHttpClient, converterFactory:
        MoshiConverterFactory, @CatNetwork(QualifierType.URL) url: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }


    @Provides
    @Singleton
    @DogNetwork(QualifierType.URL)
    fun provideDogUrl(): String {
        return NetworkConstants.ENDPOINT_DOG
    }

    @Provides
    @Singleton
    @CatNetwork(QualifierType.URL)
    fun provideCatUrl(): String {
        return NetworkConstants.ENDPOINT_CAT
    }

    @Provides
    @Singleton
    fun provideResponseCache(@ApplicationContext context: Context): Cache {
        val cacheSize = 5*1024*1024
        return Cache(context.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideNetworkInterceptor(): NetworkInterceptor {
        return NetworkInterceptor()
    }

    @Provides
    @Singleton
    fun provideOfflineInterceptor(@ApplicationContext context: Context): OfflineCacheInterceptor {
        val cacheSize = 5*1024*1024
        return OfflineCacheInterceptor(context)
    }
}