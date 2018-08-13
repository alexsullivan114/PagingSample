package com.raywenderlich.android.redditclone

import android.support.v7.util.DiffUtil
import com.raywenderlich.android.redditclone.networking.RedditPost

class RedditDiffUtilCallback : DiffUtil.ItemCallback<RedditPost>() {

  override fun areItemsTheSame(oldItem: RedditPost?, newItem: RedditPost?): Boolean {
    TODO("not implemented")
  }

  override fun areContentsTheSame(oldItem: RedditPost?, newItem: RedditPost?): Boolean {
    TODO("not implemented")
  }
}
