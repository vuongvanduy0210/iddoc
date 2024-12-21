package com.duyvv.kmadoc.di

import com.duyvv.kmadoc.data.remote.service.FormManagementService
import com.duyvv.kmadoc.data.remote.service.OCRService
import com.duyvv.kmadoc.data.repository.AuthRepository
import com.duyvv.kmadoc.data.repository.AuthRepositoryImpl
import com.duyvv.kmadoc.data.repository.FormManagementRepository
import com.duyvv.kmadoc.data.repository.FormManagementRepositoryImpl
import com.duyvv.kmadoc.data.repository.OCRFormRepository
import com.duyvv.kmadoc.data.repository.OCRFormRepositoryImpl
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