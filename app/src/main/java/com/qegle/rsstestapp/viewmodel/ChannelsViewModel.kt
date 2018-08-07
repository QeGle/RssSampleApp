package com.qegle.rsstestapp.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Intent
import com.qegle.rsstestapp.model.room.Channel
import com.qegle.rsstestapp.util.Constants
import com.qegle.rsstestapp.util.RSSRepository
import com.qegle.rsstestapp.view.MessagesA
import com.qegle.rsstestapp.view.OnChannelClickListener
import io.reactivex.functions.Consumer
import kotlin.concurrent.thread


class ChannelsViewModel(application: Application) : AndroidViewModel(application), OnChannelClickListener {
	
	
	override fun onClick(id: String) {
		val intent = Intent(getApplication(), MessagesA::class.java)
		intent.putExtra(Constants.CHANNEL_ID, id)
		getApplication<Application>().startActivity(intent)
	}
	
	override fun onLongClick(channel: Channel) {
	
		
	}
	
	fun deleteChannel(channel: Channel) {
		thread(start = true) {  repository.deleteChannelById(channel.link)}
	}
	
	fun addChannel(channel: Channel) {
		repository.addChannel(channel)
	}
	
	fun isChannelExist(id: String) = repository.isExist(id)
	
	fun update(oldChannel: Channel, newChannel: Channel) {
		repository.updateChannel(oldChannel,newChannel)
	}
	
	var channelsArray: MutableLiveData<ArrayList<Channel>> = MutableLiveData()
	private var repository = RSSRepository(application)
	
	init {
		repository.getChannels(Consumer { t ->
			if (t != null)
				channelsArray.value = ArrayList(t)
		})
	}
}