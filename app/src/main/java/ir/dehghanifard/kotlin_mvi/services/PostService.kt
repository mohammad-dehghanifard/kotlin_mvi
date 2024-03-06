package ir.dehghanifard.kotlin_mvi.services

import ir.dehghanifard.kotlin_mvi.api.PostApi
import ir.dehghanifard.kotlin_mvi.models.DataState
import ir.dehghanifard.kotlin_mvi.models.Post
import ir.dehghanifard.kotlin_mvi.models.UiComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PostService(private val postApi: PostApi) {
    fun getPosts() : Flow<DataState<List<Post>>> {
        return flow {
            emit(DataState.Loading(true))
            try {
                val posts = postApi.getAllPostApi()
                emit(DataState.Success(posts))
            } catch (e : Exception) {
                emit(DataState.Error(UiComponent.Toast(e.message ?: "Error")))
            } finally {
                emit(DataState.Loading(false))
            }
        }
    }
}