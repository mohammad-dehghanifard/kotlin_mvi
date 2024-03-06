package ir.dehghanifard.kotlin_mvi.models

data class PostState(
    var progressBar : Boolean = false,
    var posts : List<Post> = emptyList()
)
