package com.qegle.rsstestapp.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.qegle.rsstestapp.model.room.Channel
import com.qegle.rsstestapp.model.room.Item

@Database(entities = [Channel::class, Item::class], version = 2)
abstract class RSSDatabase : RoomDatabase() {
	
	abstract fun channelsDao(): ChannelsDao
	abstract fun itemsDao(): ItemsDao
	
	
	companion object {
		private var INSTANCE: RSSDatabase? = null
		
		fun getInstance(context: Context): RSSDatabase? {
			if (INSTANCE == null) {
				synchronized(RSSDatabase::class) {
					INSTANCE = Room.databaseBuilder(context.applicationContext,
							RSSDatabase::class.java, "rss.db")
							.build()
				}
			}
			return INSTANCE
		}
		
		fun destroyInstance() {
			INSTANCE = null
		}
	}
}