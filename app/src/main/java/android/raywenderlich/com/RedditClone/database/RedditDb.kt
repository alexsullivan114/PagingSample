package android.raywenderlich.com.RedditClone.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.raywenderlich.com.RedditClone.networking.RedditPost

@Database(
  entities = [RedditPost::class],
  version = 1,
  exportSchema = false
)
abstract class RedditDb : RoomDatabase() {
  companion object {
    fun create(context: Context): RedditDb {
      val databaseBuilder = Room.databaseBuilder(context, RedditDb::class.java, "reddit.db")
      return databaseBuilder.build()
    }
  }

  abstract fun postDao(): RedditPostDao
}
