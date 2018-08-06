package com.qegle.rsstestapp.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.qegle.rsstestapp.R
import com.qegle.rsstestapp.viewmodel.ChannelsViewModel
import kotlinx.android.synthetic.main.a_channel.*


class ChannelA : AppCompatActivity() {
	
	lateinit var channelsAdapter: ChannelsAdapter
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.a_channel)
		val viewModel = ViewModelProviders.of(this).get(ChannelsViewModel::class.java)
		
		channelsAdapter = ChannelsAdapter()
		channelsAdapter.onChannelClickListener = viewModel
		rvChannels.adapter = channelsAdapter
		rvChannels.layoutManager = LinearLayoutManager(this)
		
		observeViewModel(viewModel)
		btnAddChannel.setOnClickListener {
			val channelAddD = ChannelAddD.newInstance()
			channelAddD.onChannelAddListener = object : OnChannelAddListener {
				override fun add(channelName: String, channelLink: String) {
					viewModel.addChannel(channelName, channelLink)
				}
			}
			channelAddD.show(supportFragmentManager, "MessageD")
		}
	}
	
	
	private fun observeViewModel(viewModel: ChannelsViewModel) {
		viewModel.channelsArray.observe(this, Observer {
			channelsAdapter.update(it ?: arrayListOf())
		})
	}
}
