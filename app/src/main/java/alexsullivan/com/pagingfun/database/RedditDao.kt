package alexsullivan.com.pagingfun.database

import alexsullivan.com.pagingfun.networking.RedditPost
import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface RedditPostDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(posts : List<RedditPost>)

  @Query("SELECT * FROM RedditPost")
  fun posts() : DataSource.Factory<Int, RedditPost>
}