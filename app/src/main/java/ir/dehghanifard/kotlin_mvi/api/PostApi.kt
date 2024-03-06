package ir.dehghanifard.kotlin_mvi.api

import ir.dehghanifard.kotlin_mvi.models.Post

interface PostApi {
    suspend fun getAllPostApi() : List<Post>
}