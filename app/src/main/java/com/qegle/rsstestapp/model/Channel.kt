package com.qegle.rsstestapp.model

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.io.Serializable


@Root(name = "channel", strict = false)
class Channel : Serializable {
	
	@get:ElementList(inline = true, name = "item")
	@set:ElementList(inline = true, name = "item")
	var feedItems: ArrayList<FeedItem> = arrayListOf()
	
}