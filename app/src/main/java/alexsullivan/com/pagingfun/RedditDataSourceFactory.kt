package alexsullivan.com.pagingfun

import alexsullivan.com.pagingfun.networking.RedditPost
import android.arch.paging.DataSource


class RedditDataSourceFactory: DataSource.Factory<String, RedditPost>() {
  override fun create(): DataSource<String, RedditPost> {
    return RedditDataSource()
  }
}