package com.qegle.rsstestapp.model.parser

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.io.Serializable


@Root(name = "Channel", strict = false)
class Items : Serializable {
	
	@get:ElementList(inline = true, name = "item")
	@set:ElementList(inline = true, name = "item")
	var feedItems: ArrayList<FeedItem> = arrayListOf()
	
}