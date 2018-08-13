package android.raywenderlich.com.RedditClone

import android.raywenderlich.com.RedditClone.networking.RedditPost
import android.support.v7.util.DiffUtil

class RedditDiffUtilCallback: DiffUtil.ItemCallback<RedditPost>() {

  override fun areItemsTheSame(oldItem: RedditPost?, newItem: RedditPost?): Boolean {
    return oldItem?.title == newItem?.title
  }

  override fun areContentsTheSame(oldItem: RedditPost?, newItem: RedditPost?): Boolean {
    return oldItem == newItem
  }
}
