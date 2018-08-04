package com.qegle.rsstestapp.room

import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.qegle.rsstestapp.model.room.Channel

interface RSSDao {
	
	@Query("SELECT * from Channel")
	fun get(key: String): List<Channel>
	
	@Query("SELECT * from Channel")
	fun getAll(): List<Channel>
	
	@Insert(onConflict = REPLACE)
	fun insert(channel: Channel)
	
	@Query("DELETE from Channel")
	fun deleteAll()
}