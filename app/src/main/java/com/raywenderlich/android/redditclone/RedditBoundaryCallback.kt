/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */


package com.raywenderlich.android.redditclone

import android.arch.paging.PagedList
import android.util.Log
import com.raywenderlich.android.redditclone.database.RedditDb
import com.raywenderlich.android.redditclone.networking.RedditApiResponse
import com.raywenderlich.android.redditclone.networking.RedditPost
import com.raywenderlich.android.redditclone.networking.RedditService
import com.raywenderlich.android.redditclone.utils.PagingRequestHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

class RedditBoundaryCallback(private val db: RedditDb) : PagedList.BoundaryCallback<RedditPost>() {

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
