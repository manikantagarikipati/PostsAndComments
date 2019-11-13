package com.geekmk.postsandcomments.comments

import com.geekmk.postsandcomments.data.Comment

sealed class CommentsState {
    object Loading : CommentsState()
    data class Error(val message: String?) : CommentsState()
    data class PostsLoaded(val comments: List<Comment>) : CommentsState()
}