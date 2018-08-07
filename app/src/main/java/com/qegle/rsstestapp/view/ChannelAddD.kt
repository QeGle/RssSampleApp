package com.qegle.rsstestapp.view

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qegle.rsstestapp.R
import com.qegle.rsstestapp.model.room.Channel
import com.qegle.rsstestapp.util.ErrorType
import kotlinx.android.synthetic.main.d_channel_add.*
import kotlin.concurrent.thread


/**
 * Created by Sergey Makhaev on 04.10.2017.
 */

class ChannelAddD : android.support.v4.app.DialogFragment() {
	var onChannelAddListener: OnChannelAddListener? = null
	
	companion object {
		var channel: Channel? = null
		
		fun newInstance(channel: Channel?): ChannelAddD {
			this.channel = channel
			return ChannelAddD()
		}
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		etLink.setText(channel?.link ?: "")
		etTitle.setText(channel?.title ?: "")
		
		btnAdd.setOnClickListener { addChannel(channel != null) }
		btnCancel.setOnClickListener { dismiss() }
	}
	
	private fun addChannel(isUpdate: Boolean) {
		val name = etTitle.text?.toString()
		val link = etLink.text?.toString()
		
		thread(start = true) {
			when {
				name?.isEmpty() == true -> onChannelAddListener?.error(ErrorType.TITLE_EMPTY)
				link?.isEmpty() == true -> onChannelAddListener?.error(ErrorType.LINK_EMPTY)
				isUpdate -> {
					onChannelAddListener?.update(channel!!, Channel(name!!, link!!))
					dismiss()
				}
				link?.let { onChannelAddListener?.isExist(it) } == true -> {
					onChannelAddListener?.error(ErrorType.ALREADY_EXIST)
					
				}
				!Patterns.WEB_URL.matcher(link).matches() -> onChannelAddListener?.error(ErrorType.LINK_NOT_VALID)
				else -> {
					onChannelAddListener?.add(Channel(name!!, link!!))
					dismiss()
				}
			}
		}
	}
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.d_channel_add, container, false)
	}
}