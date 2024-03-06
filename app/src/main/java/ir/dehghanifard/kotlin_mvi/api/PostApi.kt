package ir.dehghanifard.kotlin_mvi.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import ir.dehghanifard.kotlin_mvi.models.Post
import kotlinx.serialization.json.Json

interface PostApi {
    suspend fun getAllPostApi() : List<Post>

    //provide api
    companion object {
        private val httpClient = HttpClient(Android){
            install(ContentNegotiation) {
                json(
                    Json {
                        this.ignoreUnknownKeys = true
                    }
                )
            }
        }

        fun providePostApi() : PostApi = PostApiImpl(httpClient)
    }
}