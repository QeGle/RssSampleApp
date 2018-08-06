package com.qegle.rsstestapp.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.content.Intent
import android.util.Log
import com.qegle.rsstestapp.model.room.Channel
import com.qegle.rsstestapp.util.Constants
import com.qegle.rsstestapp.util.RSSRepository
import com.qegle.rsstestapp.view.MessagesA
import com.qegle.rsstestapp.view.OnChannelClickListener


class ChannelsViewModel(application: Application) : AndroidViewModel(application), OnChannelClickListener {
	private val TAG = "ChannelsViewModel"
	override fun onClick(id: String) {
		val intent = Intent(getApplication(), MessagesA::class.java)
		intent.putExtra(Constants.CHANNEL_ID, id)
		
		getApplication<Application>().startActivity(intent)
	}
	
	override fun onLongClick(id: String) {
		Log.d(TAG, "onLongClick: $id")
	}
	
	fun addChannel(channelName: String, channelUrl: String) {
		repository.addChannel(Channel(channelName, channelUrl))
		channelsArray = repository.getChannels()
	}
	
	var channelsArray: LiveData<ArrayList<Channel>>
	var repository = RSSRepository(application)
	
	init {
		channelsArray = repository.getChannels()
	}
}