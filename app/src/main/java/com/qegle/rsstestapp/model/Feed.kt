package com.qegle.rsstestapp.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss",strict = false)
class Feed{
	
//	@Element(name = "channel")
	@get:Element(name = "channel")
	@set:Element(name = "channel")
	var channel: Channel? = null
}