package com.geekmk.postsandcomments

import com.geekmk.postsandcomments.comments.CommentsViewModel
import com.geekmk.postsandcomments.data.Api
import com.geekmk.postsandcomments.data.Repository
import com.geekmk.postsandcomments.data.RepositoryImpl
import com.geekmk.postsandcomments.posts.PostsViewModel
import com.geekmk.postsandcomments.utils.NetworkState
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataSourceModule = module {

    single { createRetrofitInstance() }

    single { get<Retrofit>().create(Api::class.java) }

    single { NetworkState(androidApplication()) }

    single { RepositoryImpl(get(), get(), androidApplication()) as Repository }

}

val viewmodelModule = module {
    viewModel { PostsViewModel(get()) }

    viewModel { CommentsViewModel(get()) }
}

fun createRetrofitInstance(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}