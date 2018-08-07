package com.qegle.rsstestapp.util

import android.content.Context
import com.qegle.rsstestapp.model.room.Channel
import com.qegle.rsstestapp.model.room.Item
import com.qegle.rsstestapp.network.RssLoader
import com.qegle.rsstestapp.room.RSSDatabase
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import kotlin.concurrent.thread


class RSSRepository(context: Context) {
	
	val database = RSSDatabase.getInstance(context)
	
	fun getChannels(consumer: Consumer<List<Channel>>) {
		thread(start = true) { subscribe(database?.channelsDao()?.getAll(), consumer) }
	}
	
	fun isExist(id: String): Boolean {
		val list = database?.channelsDao()?.get(id)
		return list != null
	}
	
	private fun isExist(item: Item): Boolean {
		val list = database?.itemsDao()?.getByTitleAndChannel(item.title, item.channelId)
		return list != null && list.isNotEmpty()
	}
	
	fun update(channel: Channel) {
		thread(start = true) { database?.channelsDao()?.update(channel) }
	}
	
	fun addChannel(channel: Channel) {
		thread(start = true) { database?.channelsDao()?.insertOrReplace(channel) }
	}
	
	fun deleteChannelById(id: String) {
		val channel = database?.channelsDao()?.get(id)
		if (channel != null)
			database?.channelsDao()?.delete(channel)
	}
	
	fun getMessages(link: String, consumer: Consumer<List<Item>>, errorUpdate: (ErrorType) -> Unit = {}) {
		thread(start = true) {
			subscribe(database?.itemsDao()?.getAllByChannel(link), consumer)
			updateMessages(link, errorUpdate = errorUpdate)
		}
	}
	
	fun updateMessages(link: String, onDataNotChangedListener: () -> Unit = {}, errorUpdate: (ErrorType) -> Unit = {}) {
		RssLoader(link).getFeed(object : OnFeedRequestListener {
			override fun success(feeds: ArrayList<Item>) {
				thread(start = true) {
					
					var isDataChanged = false
					feeds.forEach {
						if (!isExist(it)) {
							isDataChanged = true
							database?.itemsDao()?.insertOrReplace(it)
						}
					}
					
					if (!isDataChanged)
						onDataNotChangedListener.invoke()
				}
			}
			
			override fun error(error: ErrorType) {
				errorUpdate.invoke(error)
				onDataNotChangedListener.invoke()
			}
		})
	}
	
	private fun <T> subscribe(flowable: Flowable<T>?, consumer: Consumer<T>) {
		flowable?.apply {
			observeOn(AndroidSchedulers.mainThread())
					.subscribe(consumer)
		}
	}
	
	fun updateChannel(oldChannel: Channel, newChannel: Channel) {
		if (oldChannel.link != newChannel.link) {
			deleteChannelById(oldChannel.link)
			addChannel(newChannel)
		} else update(newChannel)
	}
}