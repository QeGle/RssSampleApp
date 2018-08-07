package com.qegle.rsstestapp.util

import com.qegle.rsstestapp.model.room.Item

interface OnFeedRequestListener {
	fun success(feeds: ArrayList<Item>)
	
	fun error(error: ErrorType)
}