package ir.dehghanifard.kotlin_mvi.models

sealed class DataState<T> {
    data class Loading<T>(val isLoading : Boolean) : DataState<T>()
    data class Success<T>(val posts : List<Post>) : DataState<T>()
    data class Error<T>(val uiComponent: UiComponent) : DataState<T>()
}

sealed class UiComponent{
    data class Toast(val message : String) : UiComponent()
}