package alexsullivan.com.pagingfun

import alexsullivan.com.pagingfun.database.RedditDb
import alexsullivan.com.pagingfun.networking.RedditPost
import alexsullivan.com.pagingfun.networking.RedditService
import android.arch.lifecycle.Observer
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  val adapter = RedditAdapter()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    list.layoutManager = LinearLayoutManager(this)
    list.adapter = adapter

    val database = RedditDb.create(this)

    val config = PagedList.Config.Builder()
      .setPageSize(30)
      .setEnablePlaceholders(false)
      .build()

    val livePagedListBuilder = LivePagedListBuilder<Int, RedditPost>(database.posts().posts(), config)
    livePagedListBuilder.setBoundaryCallback(RedditBoundaryCallback(database, RedditService.createService()))
    val liveData = livePagedListBuilder.build()
    liveData
      .observe(this, Observer<PagedList<RedditPost>> { pagedList ->
        adapter.submitList(pagedList)
      })
  }
}
