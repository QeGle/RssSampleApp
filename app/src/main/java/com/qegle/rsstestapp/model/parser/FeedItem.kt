package com.qegle.rsstestapp.model.parser

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import java.io.Serializable

@Root(name = "item", strict = false)
class FeedItem : Serializable {
	
	@get:Element(name = "pubDate")
	@set:Element(name = "pubDate")
	var pubDate: String = ""
	
	@get:Element(name = "title")
	@set:Element(name = "title")
	var title: String = ""
	
	@get:Element(name = "link")
	@set:Element(name = "link")
	var link: String? = null
	
	@get:Element(name = "description")
	@set:Element(name = "description")
	var description: String = ""
	
	
}