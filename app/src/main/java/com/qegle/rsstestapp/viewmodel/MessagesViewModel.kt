package com.qegle.rsstestapp.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.util.Log
import com.qegle.rsstestapp.util.RSSRepository
import com.qegle.rsstestapp.view.OnItemClickListener
import com.qegle.rsstestapp.model.FeedItem


class MessagesViewModel(application: Application) : AndroidViewModel(application), OnItemClickListener {
	lateinit var id: String
	private val TAG = "MessagesViewModel"
	override fun onClick(id: String) {
		Log.d(TAG, "onClick: $id")
	}
	
	lateinit var messagesArray: LiveData<ArrayList<FeedItem>>
	var repository = RSSRepository()
	
	fun init(id: String) {
		messagesArray = repository.getMessages(id)
	}
}