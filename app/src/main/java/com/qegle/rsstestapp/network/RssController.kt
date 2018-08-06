package com.qegle.rsstestapp.network

import android.util.Log
import com.qegle.rsstestapp.model.parser.Feed
import com.qegle.rsstestapp.util.OnFeedRequestSuccessListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

class RssController(url: String) {
	var rssService: RssService
		private set
	
	init {
		val restService = RESTServiceGenerator.createService(RssAdapter::class.java, url)
		rssService = RssService(restService)
	}
}

class RssService(val rssAdapter: RssAdapter) {
	private val TAG = "RssService"
	fun getFeed(onFeedRequestSuccessListener: OnFeedRequestSuccessListener) {
		
		rssAdapter.getItems().enqueue(object : Callback<Feed?> {
			override fun onFailure(call: Call<Feed?>?, t: Throwable?) {
				Log.d(TAG, "onFailure: " + t?.message)
			}
			
			override fun onResponse(call: Call<Feed?>?, response: Response<Feed?>?) {
				val channels = response?.body()?.items?.feedItems ?: arrayListOf()
				
				onFeedRequestSuccessListener.success(channels)
			}
		})
	}
}

interface RssAdapter {
	@GET(".")
	fun getItems(): Call<Feed>
}