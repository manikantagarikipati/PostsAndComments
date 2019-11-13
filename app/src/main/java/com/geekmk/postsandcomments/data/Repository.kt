package com.geekmk.postsandcomments.data

import android.app.Application
import com.geekmk.postsandcomments.R
import com.geekmk.postsandcomments.comments.CommentsState
import com.geekmk.postsandcomments.posts.PostsState
import com.geekmk.postsandcomments.utils.NetworkState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class RepositoryImpl(
    private val postApi: Api,
    private val networkState: NetworkState,
    private val application: Application,
    private val backgroundDispatcher: CoroutineDispatcher = Dispatchers.Default
) : Repository {

    //Switched context to background thread
    override suspend fun getPostsState(): PostsState = withContext(backgroundDispatcher) {
        if (!networkState.isConnected()) {
            return@withContext PostsState.Error(application.getString(R.string.not_connected))
        }

        val response = try {
            postApi.getPostsResponse()
        } catch (e:Throwable){
            //Sending a generic exception to the view for now

            Timber.e(e)
            return@withContext PostsState.Error(application.getString(R.string.network_error))
        }

        return@withContext if (response.isSuccessful) {
            response.body()?.let {
                PostsState.PostsLoaded(it)
            } ?: PostsState.Error(application.getString(R.string.emty_body))
        } else {
            Timber.e(response.message())
            PostsState.Error(response.message())
        }
    }

    //Switched context to background thread
    override suspend fun getCommentsForPosts(postId: Int): CommentsState = withContext(backgroundDispatcher) {
        if (!networkState.isConnected()) {
            return@withContext CommentsState.Error(application.getString(R.string.not_connected))
        }

        val response =try {
            postApi.getComments(postId)
        } catch (e:Throwable){
            //Sending a generic exception to the view for now
            Timber.e(e)
            return@withContext CommentsState.Error(application.getString(R.string.network_error))
        }

        return@withContext if (response.isSuccessful) {
            response.body()?.let {
                CommentsState.PostsLoaded(it)
            } ?: CommentsState.Error(application.getString(R.string.emty_body))
        } else {
            Timber.e(response.message())
            CommentsState.Error(response.message())
        }
    }

}

interface Repository {
    suspend fun getPostsState(): PostsState
    suspend fun getCommentsForPosts(postId: Int): CommentsState
}