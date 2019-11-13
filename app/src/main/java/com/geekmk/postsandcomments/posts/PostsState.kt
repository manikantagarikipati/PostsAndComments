package com.geekmk.postsandcomments.posts

import com.geekmk.postsandcomments.data.Post

sealed class PostsState {
    object Loading : PostsState()
    data class Error(val message: String?) : PostsState()
    data class PostsLoaded(val posts: List<Post>) : PostsState()
}