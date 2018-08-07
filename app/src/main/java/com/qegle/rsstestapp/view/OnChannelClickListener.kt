package com.qegle.rsstestapp.view

import com.qegle.rsstestapp.model.room.Channel

interface OnChannelClickListener:OnLinkClickListener {
	fun onLongClick(channel: Channel)
}