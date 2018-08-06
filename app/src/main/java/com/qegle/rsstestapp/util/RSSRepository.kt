package com.qegle.rsstestapp.util

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.os.Handler
import com.qegle.rsstestapp.model.parser.FeedItem
import com.qegle.rsstestapp.model.room.Channel
import com.qegle.rsstestapp.model.room.Item
import com.qegle.rsstestapp.network.RssController
import com.qegle.rsstestapp.room.RSSDatabase
import kotlin.concurrent.thread


class RSSRepository(val context: Context) {
	
	val database = RSSDatabase.getInstance(context)
	
	fun getChannels(): LiveData<ArrayList<Channel>> {
		val data = MutableLiveData<ArrayList<Channel>>()
		data.value = arrayListOf()
		
		thread {
			val list = database?.channelsDao()?.getAll() ?: emptyList()
			Handler(context.mainLooper).post { data.value = ArrayList(list) }
		}
		
		return data
	}
	
	fun isExist(channel: Channel): Boolean {
		val list = database?.channelsDao()?.get(channel.link)
		return list != null && list.isNotEmpty()
	}
	
	fun isExist(item: Item): Boolean {
		val list = database?.itemsDao()?.getByTitleAndChannel(item.title, item.channelId)
		return list != null && list.isNotEmpty()
	}
	
	fun addChannel(channel: Channel) {
		database?.channelsDao()?.insertOrReplace(channel)
	}
	
	fun deleteChannel(channel: Channel) {
		database?.channelsDao()?.delete(channel)
	}
	
	fun getMessages(id: String): LiveData<ArrayList<Item>> {
		val data = MutableLiveData<ArrayList<Item>>()
		
		val controller = RssController("https://habr.com/rss/feed/posts/9b31673a34bb9a38bc2e1162d330534f/")
		
		val list = database?.itemsDao()?.getAll() ?: emptyList()
		data.value = ArrayList(list)
		
		controller.rssService.getFeed(object : OnFeedRequestSuccessListener {
			override fun success(feeds: ArrayList<FeedItem>) {
				val itemsNetwork = arrayListOf<Item>()
				feeds.forEach {
					itemsNetwork.add(Item(it.pubDate, it.title, it.link, it.description, id))
				}
				
				itemsNetwork.forEach {
					if (!isExist(it))
						database?.itemsDao()?.insertOrReplace(it)
				}
				
				
				val list = database?.itemsDao()?.getAll() ?: emptyList()
				data.value = ArrayList(list)
			}
		})
		return data
	}
}