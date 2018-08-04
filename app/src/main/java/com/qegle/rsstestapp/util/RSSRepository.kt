package com.qegle.rsstestapp.util

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.qegle.rsstestapp.model.parser.FeedItem
import com.qegle.rsstestapp.model.parser.RSSChannel
import com.qegle.rsstestapp.network.RssController


class RSSRepository {
	fun getChannels(): LiveData<ArrayList<RSSChannel>> {
		val data = MutableLiveData<ArrayList<RSSChannel>>()
		
		data.value = arrayListOf(RSSChannel("first", "urlF"), RSSChannel("second", "urlS"))
		return data
	}
	
	fun getMessages(id: String): LiveData<ArrayList<FeedItem>> {
		val data = MutableLiveData<ArrayList<FeedItem>>()
		
		val controller = RssController("https://habr.com/rss/feed/posts/9b31673a34bb9a38bc2e1162d330534f/")
		
		controller.rssService.getFeed(data)
		
		data.value = arrayListOf()
		return data
	}
}