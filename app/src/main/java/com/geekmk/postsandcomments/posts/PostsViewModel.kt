package com.geekmk.postsandcomments.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekmk.postsandcomments.utils.CouroutineViewModel
import com.geekmk.postsandcomments.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class PostsViewModel(private val repository: Repository, uiContext: CoroutineContext = Dispatchers.Main) :
    CouroutineViewModel(uiContext) {

    private val privateState = MutableLiveData<PostsState>()

    val postLiveData: LiveData<PostsState> = privateState

    fun refreshPosts() = launch {
        privateState.value = PostsState.Loading
        Timber.d("getPostsState() called....")
        privateState.value = repository.getPostsState()
        Timber.d("repository.getPostsState() executed....")
    }

}