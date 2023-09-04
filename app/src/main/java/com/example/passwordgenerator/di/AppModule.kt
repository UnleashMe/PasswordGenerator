package com.example.passwordgenerator.di

import android.content.Context
import androidx.room.Room
import com.example.passwordgenerator.data.database.PasswordDao
import com.example.passwordgenerator.data.database.PasswordDatabase
import com.example.passwordgenerator.data.repositories.PasswordRepositoryImpl
import com.example.passwordgenerator.domain.repositories.PasswordRepository
import com.example.passwordgenerator.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesPasswordDao(@ApplicationContext context: Context): PasswordDao {
        val database =
            Room.databaseBuilder(context, PasswordDatabase::class.java, DATABASE_NAME).build()
        return database.getPasswordDao()
    }

    @Provides
    @Singleton
    fun providesPasswordRepository(
        passwordDao: PasswordDao,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): PasswordRepository {
        return PasswordRepositoryImpl(passwordDao, coroutineDispatcher)
    }

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @MainImmediateDispatcher
    @Provides
    fun providesMainImmediateDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate
}