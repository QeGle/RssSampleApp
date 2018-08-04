package com.qegle.rsstestapp.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.qegle.rsstestapp.util.Constants.CHANNEL_ID
import com.qegle.rsstestapp.R
import com.qegle.rsstestapp.viewmodel.MessagesViewModel
import kotlinx.android.synthetic.main.a_messages.*

class MessagesA : AppCompatActivity() {
	private lateinit var messagesAdapter: MessagesAdapter
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.a_messages)
		val viewModel = ViewModelProviders.of(this).get(MessagesViewModel::class.java)
		viewModel.init(intent.getStringExtra(CHANNEL_ID) ?: "")
		
		messagesAdapter = MessagesAdapter()
		messagesAdapter.onItemClickListener = viewModel
		
		rvMessages.adapter = messagesAdapter
		rvMessages.layoutManager = LinearLayoutManager(this)
		
		observeViewModel(viewModel)
	}
	
	private val TAG = "MessagesA"
	private fun observeViewModel(viewModel: MessagesViewModel) {
		viewModel.messagesArray.observe(this, Observer {
			Log.d(TAG, "observeViewModel: ${it?.size}")
			messagesAdapter.update(it ?: arrayListOf())
		})
	}
}
