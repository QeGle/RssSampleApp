package com.qegle.rsstestapp.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.qegle.rsstestapp.model.room.Item
import com.qegle.rsstestapp.util.Constants.HTTP
import com.qegle.rsstestapp.util.Constants.HTTPS
import com.qegle.rsstestapp.util.ErrorType
import com.qegle.rsstestapp.util.RSSRepository
import com.qegle.rsstestapp.view.OnLinkClickListener
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject


class MessagesViewModel(application: Application) : AndroidViewModel(application), OnLinkClickListener {
	lateinit var id: String
	val errorSubject = PublishSubject.create<ErrorType>()
	
	override fun onClick(link: String) {
		goTo(getApplication(), link)
	}
	
	private val TAG = "MessagesViewModel"
	var messagesArray: MutableLiveData<ArrayList<Item>> = MutableLiveData()
	private var repository = RSSRepository(application)
	
	fun init(id: String) {
		this.id = id
		repository.getMessages(id, Consumer { t ->
			if (t != null)
				Log.d(TAG, "init: ")
			messagesArray.value = ArrayList(t)
		}) { errorSubject.onNext(it) }
	}
	
	fun update(dataNotChangedListener: () -> Unit) {
		repository.updateMessages(id, dataNotChangedListener) { errorSubject.onNext(it) }
	}
	
	private fun goTo(context: Context, link: String?) {
		var url = link
		
		if (url != null) {
			if (!url.startsWith(HTTP) && !url.startsWith(HTTPS)) {
				url = HTTP + url
			}
			val browse = Intent(Intent.ACTION_VIEW, Uri.parse(url))
			if (browse.resolveActivity(context.packageManager) != null)
				context.startActivity(browse)
			else
				errorSubject.onNext(ErrorType.ACTIVITY_NOT_FOUND)
		}
	}
}