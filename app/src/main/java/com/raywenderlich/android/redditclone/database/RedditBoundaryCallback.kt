package com.raywenderlich.android.redditclone.database

import android.arch.paging.PagedList
import com.raywenderlich.android.redditclone.networking.RedditPost
import com.raywenderlich.android.redditclone.networking.RedditService
import com.raywenderlich.android.redditclone.utils.PagingRequestHelper
import java.util.concurrent.Executors

class RedditBoundaryCallback(private val db: RedditDb): PagedList.BoundaryCallback<RedditPost>() {

  private val api = RedditService.createService()
  private val executor = Executors.newSingleThreadExecutor()
  private val helper = PagingRequestHelper(executor)

  override fun onZeroItemsLoaded() {
    super.onZeroItemsLoaded()
  }

  override fun onItemAtEndLoaded(itemAtEnd: RedditPost) {
    super.onItemAtEndLoaded(itemAtEnd)
  }
}
