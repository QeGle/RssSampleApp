package com.qegle.rsstestapp.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.qegle.rsstestapp.model.room.Channel

@Dao
interface ChannelsDao {
	
	@Query("SELECT * from Channel WHERE link LIKE :link")
	fun get(link: String): List<Channel>
	
	@Query("SELECT * from Channel")
	fun getAll(): List<Channel>
	
	@Insert(onConflict = REPLACE)
	fun insertOrReplace(channel: Channel)
	
	@Delete
	fun delete(channel: Channel)
	
	@Query("DELETE from Channel")
	fun deleteAll()
}