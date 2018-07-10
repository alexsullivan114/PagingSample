package alexsullivan.com.pagingfun.networking

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

class RedditApiResponse(val data: RedditListing)
class RedditListing(val children: List<PostContainer>)
class PostContainer(val data: RedditPost)

@Entity
data class RedditPost(
  @SerializedName("name")
  val key: String,
  @SerializedName("title")
  @PrimaryKey
  val title: String,
  @SerializedName("score")
  val score: Int,
  @SerializedName("author")
  val author: String,
  @SerializedName("num_comments")
  val commentCount: Int)