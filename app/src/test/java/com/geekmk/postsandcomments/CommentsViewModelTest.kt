package com.geekmk.postsandcomments

import androidx.lifecycle.Observer
import com.geekmk.postsandcomments.comments.CommentsState
import com.geekmk.postsandcomments.comments.CommentsViewModel
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.koin.test.get
import timber.log.Timber

class CommentsViewModelTest : BaseTestClass() {

    private lateinit var commentsViewModel: CommentsViewModel
    @RelaxedMockK
    lateinit var mockObserver: Observer<CommentsState>

    @Before
    override fun before() {
        super.before()
        commentsViewModel = get()
    }

    @Test
    fun testGetCommentsDataIsSuccessful() =
        runBlocking {
            commentsViewModel.commentsLiveData.observeForever(mockObserver)
            assert(commentsViewModel.commentsLiveData.value == null)
            commentsViewModel.getComments(1).join()
            val value = commentsViewModel.commentsLiveData.value
            println("called..... suspend function")

            assert(value != null)
            when (value) {
                is CommentsState.PostsLoaded -> {
                    assert(value.comments.size >= 0)
                }
                is CommentsState.Error -> {
                    Timber.e(value.message)
                    assert(false)
                }
            }
        }

}