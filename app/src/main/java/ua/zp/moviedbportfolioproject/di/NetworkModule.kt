package ua.zp.moviedbportfolioproject.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ua.zp.moviedbportfolioproject.Confiq
import ua.zp.moviedbportfolioproject.data.network.Api
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
//    private val gson: Gson = provideGson()
//    private val okHttpClient = provideClient()
//    private val retrofit = provideRetrofit(gson, okHttpClient)

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit):Api{
        return retrofit.create(Api::class.java)
    }
    @Provides
    @Singleton
    fun provideGson():Gson{
        return GsonBuilder().create()
    }
    @Provides
    @Singleton
    fun provideClient():OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(createLoggingInterceptor())
            .build()
    }
    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Confiq.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun createLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}