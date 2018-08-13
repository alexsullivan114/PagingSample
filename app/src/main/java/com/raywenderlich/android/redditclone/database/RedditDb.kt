package com.raywenderlich.android.redditclone.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.raywenderlich.android.redditclone.networking.RedditPost

@Database(
    entities = [RedditPost::class],
    version = 1,
    exportSchema = false
)
abstract class RedditDb : RoomDatabase() {
  companion object {
    fun create(context: Context): RedditDb {
      val databaseBuilder = Room.databaseBuilder(context, RedditDb::class.java, "redditclone.db")
      return databaseBuilder.build()
    }
  }
}
