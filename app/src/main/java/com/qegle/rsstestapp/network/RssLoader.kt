package com.qegle.rsstestapp.network

import com.qegle.rsstestapp.model.room.Item
import com.qegle.rsstestapp.util.Constants
import com.qegle.rsstestapp.util.ErrorType
import com.qegle.rsstestapp.util.OnFeedRequestListener
import okhttp3.OkHttpClient
import okhttp3.Request
import org.w3c.dom.Element
import org.xml.sax.SAXException
import java.io.ByteArrayInputStream
import java.io.IOException
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException


class RssLoader(var url: String) {
	
	fun getFeed(onFeedRequestListener: OnFeedRequestListener) {
		val client = OkHttpClient()
		
		if (!url.startsWith(Constants.HTTP) && !url.startsWith(Constants.HTTPS)) {
			url = Constants.HTTP + url
		}
		
		val request = Request.Builder()
				.url(url)
				.build()
		
		client.newCall(request).enqueue(object : okhttp3.Callback {
			override fun onFailure(call: okhttp3.Call?, e: IOException?) {
				onFeedRequestListener.error(ErrorType.LINK_NOT_VALID)
			}
			
			override fun onResponse(call: okhttp3.Call?, response: okhttp3.Response?) {
				val respBodyString = response?.body()?.string() ?: return
				
				try {
					val dbf = DocumentBuilderFactory.newInstance()
					val db = dbf.newDocumentBuilder()
					val doc = db.parse(ByteArrayInputStream(respBodyString.toByteArray()))
					doc.documentElement.normalize()
					
					val nodeItems = doc.getElementsByTagName("item")
					
					val items = arrayListOf<Item>()
					
					for (i in 0..nodeItems.length) {
						val node = nodeItems.item(i) ?: continue
						val element = node as Element
						
						items.add(parseElement(url, element))
						
					}
					onFeedRequestListener.success(items)
					
				} catch (e: ParserConfigurationException) {
					onFeedRequestListener.error(ErrorType.PARSING)
				} catch (e: SAXException) {
					onFeedRequestListener.error(ErrorType.PARSING)
				}
			}
		})
	}
	
	fun parseElement(channelId: String, element: Element): Item {
		val title = element.getElementsByTagName("title").item(0).textContent
		val desc = element.getElementsByTagName("description").item(0).textContent
		val date = element.getElementsByTagName("pubDate").item(0).textContent
		val link = element.getElementsByTagName("link").item(0).textContent
		return Item(date, title, link, desc, channelId)
	}
}