package alexsullivan.com.pagingfun.networking

import com.google.gson.annotations.SerializedName

class RedditApiResponse(val data: RedditListing)

class RedditListing(
  val children: List<PostContainer>,
  val after: String?,
  val before: String?
)

data class PostContainer(val data: RedditPost)

data class RedditPost(
  @SerializedName("title")
  val title: String,
  @SerializedName("score")
  val score: Int,
  @SerializedName("author")
  val author: String,
  @SerializedName("num_comments")
  val commentCount: Int)