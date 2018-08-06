package com.qegle.rsstestapp.util

import com.qegle.rsstestapp.model.parser.FeedItem

interface OnFeedRequestSuccessListener {
	fun success(feeds: ArrayList<FeedItem>)
}