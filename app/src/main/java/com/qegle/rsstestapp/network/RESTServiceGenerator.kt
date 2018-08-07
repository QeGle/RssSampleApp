package com.qegle.rsstestapp.network


import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

object RESTServiceGenerator {
	
	private val httpClient = OkHttpClient.Builder()
			.connectTimeout(10, TimeUnit.SECONDS)
	
	fun <S> createService(serviceClass: Class<S>, baseUrl: String): S {
		var url = baseUrl
		
		Log.d("createService", "createService: baseUrl $url")
		if (!url.endsWith("/"))
			url += "/"
		httpClient.addInterceptor { chain ->
			val original = chain.request()
			val requestBuilder = original.newBuilder()
					.header("Content-Type", "application/json")
					.method(original.method(), original.body())
			val request = requestBuilder.build()
			chain.proceed(request)
		}
		
		val client = httpClient.build()
		val builder = Retrofit.Builder()
				.baseUrl(url)
				.addConverterFactory(SimpleXmlConverterFactory.create())
		val retrofit = builder.client(client).build()
		return retrofit.create(serviceClass)
	}
	
}
