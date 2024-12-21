package com.duyvv.iddoc.di

import com.duyvv.iddoc.data.remote.service.FormManagementService
import com.duyvv.iddoc.data.remote.service.OCRService
import com.duyvv.iddoc.util.SharePreferenceExt
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpRedirect
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.gson.gson
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    @FormManagementClient
    fun provideFormManagementHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                gson()
            }

            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        println("HTTP Client: $message")
                    }
                }
            }

            defaultRequest {
                url("http://${SharePreferenceExt.lastDomain}:4000/")
                header("Authorization", "Bearer ${SharePreferenceExt.token}")
                contentType(ContentType.Application.Json)
            }
        }
    }

    @Singleton
    @Provides
    @OCRClient
    fun provideOCRHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                gson()
            }

            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        println("HTTP Client: $message")
                    }

                }
            }

            install(HttpRedirect)

            defaultRequest {
                url("http://${SharePreferenceExt.lastDomain}:8000/")
                contentType(ContentType.Application.Json)
            }
        }
    }

    @Provides
    @Singleton
    fun provideFormManagementService(@FormManagementClient client: HttpClient): FormManagementService {
        return FormManagementService(client)
    }

    @Provides
    @Singleton
    fun provideOCRService(@OCRClient client: HttpClient): OCRService {
        return OCRService(client)
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FormManagementClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OCRClient