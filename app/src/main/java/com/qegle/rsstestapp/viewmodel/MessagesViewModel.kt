package com.qegle.rsstestapp.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.util.Log
import com.qegle.rsstestapp.model.room.Item
import com.qegle.rsstestapp.util.RSSRepository
import com.qegle.rsstestapp.view.OnItemClickListener


class MessagesViewModel(application: Application) : AndroidViewModel(application), OnItemClickListener {
	lateinit var id: String
	private val TAG = "MessagesViewModel"
	override fun onClick(link: String) {
		Log.d(TAG, "onClick: $link")
	}
	
	lateinit var messagesArray: LiveData<ArrayList<Item>>
	var repository = RSSRepository(application)
	
	fun init(id: String) {
		messagesArray = repository.getMessages(id)
	}
}