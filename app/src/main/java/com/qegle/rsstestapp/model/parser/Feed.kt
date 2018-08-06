package com.qegle.rsstestapp.model.parser

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss",strict = false)
class Feed{
	
	@get:Element(name = "channel")
	@set:Element(name = "channel")
	var items: Items? = null
}