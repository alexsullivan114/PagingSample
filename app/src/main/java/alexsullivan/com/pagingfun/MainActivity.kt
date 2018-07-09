package alexsullivan.com.pagingfun

import alexsullivan.com.pagingfun.networking.RedditPost
import alexsullivan.com.pagingfun.networking.RedditRepository
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    list.layoutManager = LinearLayoutManager(this)
    RedditRepository()
      .getPosts(object: RedditRepository.RedditCallback {
        override fun onError() {
          TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onSuccess(redditPosts: List<RedditPost>) {
          list.adapter = RedditAdapter(redditPosts)
        }
      })
  }
}
