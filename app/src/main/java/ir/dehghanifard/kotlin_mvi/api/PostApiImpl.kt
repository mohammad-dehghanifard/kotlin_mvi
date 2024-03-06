package ir.dehghanifard.kotlin_mvi.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import ir.dehghanifard.kotlin_mvi.models.Post

class PostApiImpl(private val httpClient: HttpClient) : PostApi {
    override suspend fun getAllPostApi(): List<Post> {
        return httpClient.get("https://jsonplaceholder.typicode.com/posts")
            .body<List<Post>>()
    }

}