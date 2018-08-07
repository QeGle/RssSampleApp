package com.qegle.rsstestapp.view

import com.qegle.rsstestapp.model.room.Channel
import com.qegle.rsstestapp.util.ErrorType

interface OnChannelAddListener {
	fun add(channel: Channel)
	fun update(oldChannel: Channel, newChannel: Channel)
	fun error(errorType: ErrorType)
	fun isExist(id: String): Boolean
}

