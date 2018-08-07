package com.qegle.rsstestapp.room

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.qegle.rsstestapp.model.room.Channel
import io.reactivex.Flowable

@Dao
interface ChannelsDao {
	
	@Query("SELECT * from Channel WHERE link LIKE :link")
	fun get(link: String): Channel
	
	@Query("SELECT * from Channel")
	fun getAll(): Flowable<List<Channel>>
	
	@Insert(onConflict = REPLACE)
	fun insertOrReplace(channel: Channel)
	
	@Delete
	fun delete(channel: Channel)
	
	@Query("DELETE from Channel")
	fun deleteAll()
	
	@Update
	fun update(channel: Channel)
}