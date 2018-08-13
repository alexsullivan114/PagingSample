package android.raywenderlich.com.RedditClone.database

import android.arch.paging.PagedList
import android.raywenderlich.com.RedditClone.networking.RedditApiResponse
import android.raywenderlich.com.RedditClone.networking.RedditPost
import android.raywenderlich.com.RedditClone.networking.RedditService
import android.raywenderlich.com.RedditClone.utils.PagingRequestHelper
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

class RedditBoundaryCallback(private val db: RedditDb): PagedList.BoundaryCallback<RedditPost>() {

  private val api = RedditService.createService()
  private val executor = Executors.newSingleThreadExecutor()
  private val helper = PagingRequestHelper(executor)

  override fun onZeroItemsLoaded() {
    super.onZeroItemsLoaded()
    helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) { helperCallback ->
      api.getPosts()
        .enqueue(object : Callback<RedditApiResponse> {
          override fun onFailure(call: Call<RedditApiResponse>?, t: Throwable) {
            Log.e("RedditBoundaryCallback", "Failed to load data!")
            helperCallback.recordFailure(t)
          }

          override fun onResponse(
            call: Call<RedditApiResponse>?,
            response: Response<RedditApiResponse>
          ) {
            val posts = response.body()?.data?.children?.map { it.data }
            executor.execute {
              db.postDao().insert(posts ?: listOf())
              helperCallback.recordSuccess()
            }
          }
        })
    }
  }
  override fun onItemAtEndLoaded(itemAtEnd: RedditPost) {
    super.onItemAtEndLoaded(itemAtEnd)
    helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) { helperCallback ->
      api.getPosts(
        after = itemAtEnd.key
      )
        .enqueue(object : Callback<RedditApiResponse> {
          override fun onFailure(call: Call<RedditApiResponse>?, t: Throwable) {
            Log.e("RedditBoundaryCallback", "Failed to load data!")
            helperCallback.recordFailure(t)
          }

          override fun onResponse(
            call: Call<RedditApiResponse>?,
            response: Response<RedditApiResponse>
          ) {
            val posts = response.body()?.data?.children?.map { it.data }
            executor.execute {
              db.postDao().insert(posts ?: listOf())
              helperCallback.recordSuccess()
            }
          }
        })
    }
  }
}
