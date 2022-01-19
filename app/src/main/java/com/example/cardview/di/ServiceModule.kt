package com.example.cardview.di

import android.content.Context
import com.example.cardview.data.TarefaDao
import com.example.cardview.data.TarefaDataBase
import com.example.cardview.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {


    @Provides
    @Singleton
    fun provideDao(@ApplicationContext context: Context): TarefaDao{
        return TarefaDataBase.getDatabase(context).TarefaDao()
    }

    @Provides
    @Singleton
    fun provideTaskService(tarefaDao: TarefaDao): Repository {
        return Repository(tarefaDao)
    }

}