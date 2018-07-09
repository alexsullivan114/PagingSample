package alexsullivan.com.pagingfun.networking

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditService {
  @GET("/r/aww/hot.json")
  fun getPosts(@Query("limit") limit: Int = 30): Call<RedditApiResponse>

  companion object {
    const val BASE_URL = "https://www.reddit.com/"

    fun createService(): RedditService {
      return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RedditService::class.java)
    }
  }
}