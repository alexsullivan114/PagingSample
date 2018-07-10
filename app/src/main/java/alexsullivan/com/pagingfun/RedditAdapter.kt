package alexsullivan.com.pagingfun

import alexsullivan.com.pagingfun.networking.RedditPost
import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.adapter_row.view.*

class RedditAdapter :
  PagedListAdapter<RedditPost, RedditViewHolder>(RedditDiffUtilCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RedditViewHolder {
    val layout = LayoutInflater.from(parent.context).inflate(R.layout.adapter_row, parent, false)
    return RedditViewHolder(layout)
  }

  override fun onBindViewHolder(holder: RedditViewHolder, position: Int) {
    val item = getItem(position)
    holder.itemView.title.text = item?.title
    holder.itemView.score.text =
        holder.itemView.context.resources.getString(R.string.score, item?.score)
    holder.itemView.comments.text =
        holder.itemView.context.resources.getString(R.string.comments, item?.commentCount)
  }
}

class RedditViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class RedditDiffUtilCallback: DiffUtil.ItemCallback<RedditPost>() {
  override fun areItemsTheSame(oldItem: RedditPost?, newItem: RedditPost?): Boolean {
    return oldItem == newItem
  }

  override fun areContentsTheSame(oldItem: RedditPost?, newItem: RedditPost?): Boolean {
    return oldItem?.title == newItem?.title
  }
}