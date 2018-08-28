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

import android.arch.paging.PageKeyedDataSource
import android.util.Log
import com.raywenderlich.android.redditclone.networking.RedditApiResponse
import com.raywenderlich.android.redditclone.networking.RedditPost
import com.raywenderlich.android.redditclone.networking.RedditService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RedditDataSource : PageKeyedDataSource<String, RedditPost>() {

  private val api = RedditService.createService()

  override fun loadInitial(
      params: LoadInitialParams<String>,
      callback: LoadInitialCallback<String, RedditPost>
  ) {
    api.getPosts(loadSize = params.requestedLoadSize)
        .enqueue(object : Callback<RedditApiResponse> {
          override fun onFailure(call: Call<RedditApiResponse>?, t: Throwable?) {
            Log.e("RedditDataSource", "Failed to fetch data!")
          }

          override fun onResponse(
              call: Call<RedditApiResponse>?,
              response: Response<RedditApiResponse>
          ) {
            val listing = response.body()?.data
            val redditPosts = listing?.children?.map { it.data }
            callback.onResult(redditPosts ?: listOf(), listing?.before, listing?.after)
          }
        })
  }

  override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, RedditPost>) {
    api.getPosts(
        loadSize = params.requestedLoadSize,
        after = params.key
    )
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

  override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, RedditPost>) {
    api.getPosts(
        loadSize = params.requestedLoadSize,
        before = params.key
    )
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
