package ir.dehghanifard.kotlin_mvi.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.dehghanifard.kotlin_mvi.api.PostApi
import ir.dehghanifard.kotlin_mvi.models.DataState
import ir.dehghanifard.kotlin_mvi.models.PostState
import ir.dehghanifard.kotlin_mvi.models.UiComponent
import ir.dehghanifard.kotlin_mvi.services.PostService
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class PostViewModel : ViewModel(),ContainerHost<PostState,UiComponent>{
    override val container: Container<PostState, UiComponent> = container(PostState())

    private val postService = PostService(PostApi.providePostApi())

    init {
        getPosts()
    }

    fun getPosts() {
        intent {
            val posts = postService.getPosts()
            posts
                .onEach { dataState ->
                    when(dataState){
                        is DataState.Loading -> {
                            reduce {
                                state.copy(progressBar = dataState.isLoading)
                            }
                        }
                        is DataState.Success -> {
                            reduce {
                                state.copy(posts = dataState.posts)
                            }
                        }
                        is DataState.Error -> {
                            when(dataState.uiComponent){
                                is UiComponent.Toast -> {
                                    postSideEffect(UiComponent.Toast(dataState.uiComponent.message))
                                }
                            }
                        }
                    }
                }
                .launchIn(viewModelScope)
        }
    }

}