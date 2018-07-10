package alexsullivan.com.pagingfun

import alexsullivan.com.pagingfun.database.RedditDb
import alexsullivan.com.pagingfun.networking.RedditApiResponse
import alexsullivan.com.pagingfun.networking.RedditPost
import alexsullivan.com.pagingfun.networking.RedditService
import alexsullivan.com.pagingfun.utils.PagingRequestHelper
import android.arch.paging.PagedList
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

class RedditBoundaryCallback(
  private val db: RedditDb,
  private val service: RedditService
) : PagedList.BoundaryCallback<RedditPost>() {
  private val executor = Executors.newSingleThreadExecutor()
  private val helper = PagingRequestHelper(executor)

  override fun onZeroItemsLoaded() {
    super.onZeroItemsLoaded()
    helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
      service.getPosts()
        .enqueue(buildApiCallback(it))
    }
  }

  override fun onItemAtEndLoaded(itemAtEnd: RedditPost) {
    super.onItemAtEndLoaded(itemAtEnd)
    helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
      service.getPosts(after = itemAtEnd.key)
        .enqueue(buildApiCallback(it))
    }
  }

  private fun buildApiCallback(helperCallback: PagingRequestHelper.Request.Callback): Callback<RedditApiResponse> {
    return object : Callback<RedditApiResponse> {
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
          db.posts().insert(posts ?: listOf())
          helperCallback.recordSuccess()
        }
      }
    }
  }
}