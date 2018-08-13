/*
 * Copyright (c) 2017 Razeware LLC
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

package android.raywenderlich.com.RedditClone

import android.arch.lifecycle.Observer
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.os.Bundle
import android.os.Handler
import android.raywenderlich.com.R
import android.raywenderlich.com.RedditClone.database.RedditBoundaryCallback
import android.raywenderlich.com.RedditClone.database.RedditDb
import android.raywenderlich.com.RedditClone.networking.RedditPost
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.list

class MainActivity : AppCompatActivity() {

  val adapter = RedditAdapter()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    initializeList()
  }

  private fun initializeList() {
    list.layoutManager = LinearLayoutManager(this)
    list.adapter = adapter


    val config = PagedList.Config.Builder()
      .setPageSize(30)
      .setEnablePlaceholders(false)
      .build()

    val database = RedditDb.create(this)

    val livePageListBuilder = LivePagedListBuilder<Int, RedditPost>(database.postDao().posts(), config)
    livePageListBuilder.setBoundaryCallback(RedditBoundaryCallback(database))
    val liveData = livePageListBuilder.build()

    liveData
      .observe(this, Observer<PagedList<RedditPost>> { pagedList ->
        adapter.submitList(pagedList)
      })
  }
}
