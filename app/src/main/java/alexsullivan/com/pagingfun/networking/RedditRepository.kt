package alexsullivan.com.pagingfun.networking

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RedditRepository {
  private val redditService = RedditService.createService()

  fun getPosts(callback: RedditCallback) {
    return redditService
      .getPosts()
      .enqueue(object : Callback<RedditApiResponse> {
        override fun onFailure(call: Call<RedditApiResponse>?, t: Throwable?) {
          callback.onError()
        }

        override fun onResponse(call: Call<RedditApiResponse>?, response: Response<RedditApiResponse>) {
          val body = response.body()
          if (!response.isSuccessful || body == null) {
            callback.onError()
          } else {
            callback.onSuccess(body.data.children.map { it.data })
          }
        }
      })
  }

  interface RedditCallback {
    fun onError()
    fun onSuccess(redditPosts: List<RedditPost>)
  }
}