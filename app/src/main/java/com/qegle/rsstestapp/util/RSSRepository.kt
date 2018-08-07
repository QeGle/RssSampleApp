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
		
		thread(start = true) {
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
		thread(start = true) { database?.channelsDao()?.insertOrReplace(channel) }
	}
	
	fun deleteChannel(channel: Channel) {
		thread(start = true) { database?.channelsDao()?.delete(channel) }
	}
	
	fun deleteChannelById(id: String) {
		thread(start = true) {
			val list = database?.channelsDao()?.get(id)
			if ((list != null && list.isNotEmpty())) {
				database?.channelsDao()?.delete(list[0])
			}
		}
	}
	
	fun getMessages(link: String): LiveData<ArrayList<Item>> {
		val data = MutableLiveData<ArrayList<Item>>()
		setValue(data, arrayListOf())
		
		thread(start = true) {
			val controller = RssController(link)
			val list = database?.itemsDao()?.getAllByChannel(link) ?: emptyList()
			setValue(data, list)
			
			controller.rssService.getFeed(object : OnFeedRequestSuccessListener {
				override fun success(feeds: ArrayList<FeedItem>) {
					thread(start = true) {
						val itemsNetwork = arrayListOf<Item>()
						feeds.forEach {
							itemsNetwork.add(Item(it.pubDate, it.title, it.link, it.description, link))
						}
						
						itemsNetwork.forEach {
							if (!isExist(it))
								database?.itemsDao()?.insertOrReplace(it)
						}
						
						
						val list = database?.itemsDao()?.getAllByChannel(link) ?: emptyList()
						setValue(data, list)
					}
				}
			})
		}
		return data
	}
	
	
	fun setValue(data: MutableLiveData<ArrayList<Item>>, list: List<Item>) {
		Handler(context.mainLooper).post { data.value = ArrayList(list) }
	}
}