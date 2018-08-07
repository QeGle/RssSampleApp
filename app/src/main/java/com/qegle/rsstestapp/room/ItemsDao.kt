package com.qegle.rsstestapp.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.qegle.rsstestapp.model.room.Item
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface ItemsDao {

	@Query("SELECT * from Item WHERE id LIKE :id")
	fun get(id: Int?): Maybe<Item>
	
	@Query("SELECT * from Item WHERE channelId LIKE :id")
	fun getAllByChannel(id: String): Flowable<List<Item>>
	
	
	@Query("SELECT * from Item WHERE title LIKE :title AND channelId LIKE :channelId")
	fun getByTitleAndChannel(title:String, channelId:String):List<Item>
	
	@Insert(onConflict = REPLACE)
	fun insertOrReplace(item: Item)
	
	@Delete
	fun delete(item: Item)
	
	@Query("DELETE from Item")
	fun deleteAll()
}