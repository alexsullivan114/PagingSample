package android.raywenderlich.com.RedditClone.database

import android.arch.paging.PagedList
import android.raywenderlich.com.RedditClone.networking.RedditPost
import android.raywenderlich.com.RedditClone.networking.RedditService
import android.raywenderlich.com.RedditClone.utils.PagingRequestHelper
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

  override fun onItemAtFrontLoaded(itemAtFront: RedditPost) {
    super.onItemAtFrontLoaded(itemAtFront)
  }
}
