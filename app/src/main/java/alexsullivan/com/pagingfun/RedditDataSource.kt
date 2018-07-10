package alexsullivan.com.pagingfun

import alexsullivan.com.pagingfun.networking.RedditApiResponse
import alexsullivan.com.pagingfun.networking.RedditPost
import alexsullivan.com.pagingfun.networking.RedditService
import android.arch.paging.PageKeyedDataSource
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RedditDataSource : PageKeyedDataSource<String, RedditPost>() {

  private val api = RedditService.createService()

  override fun loadInitial(
    params: LoadInitialParams<String>,
    callback: LoadInitialCallback<String, RedditPost>
  ) {
    api.getPosts()
      .enqueue(object : Callback<RedditApiResponse> {
        override fun onFailure(call: Call<RedditApiResponse>?, t: Throwable?) {
          Log.e("RedditDataSource", "Failed to fetch data!")
        }

        override fun onResponse(
          call: Call<RedditApiResponse>?,
          response: Response<RedditApiResponse>
        ) {
          val listing = response.body()?.data
          val items = listing?.children?.map { it.data }
          callback.onResult(items ?: listOf(), listing?.before, listing?.after)
        }
      })
  }

  override fun loadAfter(
    params: LoadParams<String>,
    callback: LoadCallback<String, RedditPost>
  ) {
    api.getPosts(after = params.key)
      .enqueue(object : Callback<RedditApiResponse> {
        override fun onFailure(call: Call<RedditApiResponse>?, t: Throwable?) {
          Log.e("RedditDataSource", "Failed to fetch data!")
        }

        override fun onResponse(
          call: Call<RedditApiResponse>?,
          response: Response<RedditApiResponse>
        ) {
          val listing = response.body()?.data
          val items = listing?.children?.map { it.data }
          callback.onResult(items ?: listOf(), listing?.after)
        }
      })
  }

  override fun loadBefore(
    params: LoadParams<String>,
    callback: LoadCallback<String, RedditPost>
  ) {
    api.getPosts(before = params.key)
      .enqueue(object : Callback<RedditApiResponse> {
        override fun onFailure(call: Call<RedditApiResponse>?, t: Throwable?) {
          Log.e("RedditDataSource", "Failed to fetch data!")
        }

        override fun onResponse(
          call: Call<RedditApiResponse>?,
          response: Response<RedditApiResponse>
        ) {
          val listing = response.body()?.data
          val items = listing?.children?.map { it.data }
          callback.onResult(items ?: listOf(), listing?.before)
        }
      })

  }
}