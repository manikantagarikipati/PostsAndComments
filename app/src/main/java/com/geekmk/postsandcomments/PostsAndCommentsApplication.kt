package com.geekmk.postsandcomments

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class PostsAndCommentsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidContext(this@PostsAndCommentsApplication)
            modules(listOf(dataSourceModule, viewmodelModule))
        }
    }
}