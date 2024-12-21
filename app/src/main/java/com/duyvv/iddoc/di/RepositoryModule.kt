package com.duyvv.iddoc.di

import com.duyvv.iddoc.data.remote.service.FormManagementService
import com.duyvv.iddoc.data.remote.service.OCRService
import com.duyvv.iddoc.data.repository.AuthRepository
import com.duyvv.iddoc.data.repository.AuthRepositoryImpl
import com.duyvv.iddoc.data.repository.FormManagementRepository
import com.duyvv.iddoc.data.repository.FormManagementRepositoryImpl
import com.duyvv.iddoc.data.repository.OCRFormRepository
import com.duyvv.iddoc.data.repository.OCRFormRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(formManagementService: FormManagementService): AuthRepository {
        return AuthRepositoryImpl(formManagementService)
    }

    @Provides
    @Singleton
    fun provideFormManagementRepository(formManagementService: FormManagementService): FormManagementRepository {
        return FormManagementRepositoryImpl(formManagementService)
    }

    @Provides
    @Singleton
    fun provideOCRFormRepository(ocrService: OCRService): OCRFormRepository {
        return OCRFormRepositoryImpl(ocrService)
    }
}