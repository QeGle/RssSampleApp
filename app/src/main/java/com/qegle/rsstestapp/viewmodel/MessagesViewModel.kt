package com.qegle.rsstestapp.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.qegle.rsstestapp.model.room.Item
import com.qegle.rsstestapp.util.RSSRepository
import com.qegle.rsstestapp.view.OnItemClickListener


class MessagesViewModel(application: Application) : AndroidViewModel(application), OnItemClickListener {
	lateinit var id: String
	override fun onClick(link: String) {
		goTo(getApplication(), link)
	}
	
	lateinit var messagesArray: LiveData<ArrayList<Item>>
	var repository = RSSRepository(application)
	
	fun init(id: String) {
		messagesArray = repository.getMessages(id)
	}
	
	fun goTo(context: Context, link: String?) {
		var url = link
		val HTTP = "http://"
		val HTTPS = "https://"
		if (url != null) {
			if (!url.startsWith(HTTP) && !url.startsWith(HTTPS)) {
				url = HTTP + url
			}
			val browse = Intent(Intent.ACTION_VIEW, Uri.parse(url))
			if (browse.resolveActivity(context.packageManager) != null)
				context.startActivity(browse)
			else
				Toast
						.makeText(context, "You don\'t have any browser to open web page", Toast.LENGTH_LONG)
						.show()
		}
	}
}