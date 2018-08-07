package com.qegle.rsstestapp.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.qegle.rsstestapp.R
import com.qegle.rsstestapp.model.room.Channel
import com.qegle.rsstestapp.util.ErrorType
import com.qegle.rsstestapp.viewmodel.ChannelsViewModel
import kotlinx.android.synthetic.main.a_channel.*


class ChannelA : AppCompatActivity() {
	lateinit var viewModel: ChannelsViewModel
	private lateinit var channelsAdapter: ChannelsAdapter
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.a_channel)
		viewModel = ViewModelProviders.of(this).get(ChannelsViewModel::class.java)
		
		channelsAdapter = ChannelsAdapter()
		channelsAdapter.onChannelClickListener = object : OnChannelClickListener {
			override fun onLongClick(channel: Channel) {
				val builder = AlertDialog.Builder(this@ChannelA)
				builder.setTitle(getString(R.string.settings_title) + channel.title)
						.setPositiveButton(getString(R.string.change)) { _, _ -> showChannelAddDialog(channel) }
						.setNeutralButton(getString(R.string.cancel)) { dialogInterface, _ -> dialogInterface.cancel() }
						.setNegativeButton(getString(R.string.delete)) { _, _ -> viewModel.deleteChannel(channel) }
				builder.create().show()
			}
			
			override fun onClick(id: String) {
				viewModel.onClick(id)
			}
		}
		rvChannels.adapter = channelsAdapter
		rvChannels.layoutManager = LinearLayoutManager(this)
		clRoot
		observeViewModel(viewModel)
		btnAddChannel.setOnClickListener {
			showChannelAddDialog()
		}
	}
	
	fun showChannelAddDialog(channel: Channel? = null) {
		val channelAddD = ChannelAddD.newInstance(channel)
		channelAddD.onChannelAddListener = object : OnChannelAddListener {
			override fun add(channel: Channel) {
				viewModel.addChannel(channel)
			}
			
			override fun error(errorType: ErrorType) {
				when (errorType) {
					ErrorType.LINK_NOT_VALID -> showSnack(getString(R.string.check_internet))
					ErrorType.LINK_EMPTY -> showSnack(getString(R.string.link_is_empty))
					ErrorType.TITLE_EMPTY -> showSnack(getString(R.string.title_is_empty))
					ErrorType.ALREADY_EXIST -> showSnack(getString(R.string.channel_already_exist))
				}
			}
			
			override fun isExist(id: String) = viewModel.isChannelExist(id)
			
			override fun update(oldChannel: Channel, newChannel: Channel) {
				viewModel.update(oldChannel, newChannel)
			}
			
		}
		channelAddD.show(supportFragmentManager, "MessageD")
	}
	
	private fun showSnack(text: String) {
		Snackbar.make(clRoot, text, Snackbar.LENGTH_SHORT).show()
	}
	
	private fun observeViewModel(viewModel: ChannelsViewModel) {
		viewModel.channelsArray.observe(this, Observer {
			tvPlaceholder.visibility = if (it?.isNotEmpty() == true) View.GONE else View.VISIBLE
			channelsAdapter.update(it ?: arrayListOf())
		})
	}
}
