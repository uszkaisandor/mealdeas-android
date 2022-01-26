package com.uszkaisandor.mealdeas.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.uszkaisandor.mealdeas.api.AuthInterceptor
import com.uszkaisandor.mealdeas.api.ClientPropertiesInterceptor
import com.uszkaisandor.mealdeas.data.MealDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(): AuthInterceptor =
        AuthInterceptor()

    @Singleton
    @Provides
    fun provideClientPropertiesInterceptor() = ClientPropertiesInterceptor()

    @Provides
    @Singleton
    fun provideRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient.Builder,
        authInterceptor: AuthInterceptor,
        clientPropertiesInterceptor: ClientPropertiesInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): Retrofit {

        okHttpClient.addInterceptor(clientPropertiesInterceptor)
        okHttpClient.addInterceptor(authInterceptor)
        okHttpClient.addInterceptor(loggingInterceptor)

        return Retrofit.Builder()
            .baseUrl("") // todo later
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient.build())
            .build()
    }


    @Provides
    @Singleton
    fun provideDatabase(app: Application): MealDatabase =
        Room.databaseBuilder(app, MealDatabase::class.java, "meal_database")
            .fallbackToDestructiveMigration()
            .build()

}