package com.raywenderlich.android.redditclone

import android.arch.paging.PageKeyedDataSource
import com.raywenderlich.android.redditclone.networking.RedditPost

class RedditDataSource : PageKeyedDataSource<String, RedditPost>() {
  
  override fun loadInitial(
    params: LoadInitialParams<String>,
    callback: LoadInitialCallback<String, RedditPost>
  ) {
    TODO("not implemented")
  }

  override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, RedditPost>) {
    TODO("not implemented")
  }

  override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, RedditPost>) {
    TODO("not implemented")
  }
}
