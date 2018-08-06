package com.qegle.rsstestapp.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qegle.rsstestapp.R
import com.qegle.rsstestapp.model.room.Channel
import kotlinx.android.synthetic.main.v_channel.view.*

class ChannelsAdapter : RecyclerView.Adapter<ChannelsAdapter.ChannelsViewHolder>() {
	private var channelsArrayList: ArrayList<Channel> = arrayListOf()
	var onChannelClickListener: OnChannelClickListener? = null
	override fun onBindViewHolder(holder: ChannelsViewHolder, position: Int) {
		val channel = channelsArrayList[position]
		holder.tvTitle.text = channel.title
		var link = channel.link
		
		if (link.contains("://")) {
			link = link.split("://")[1]
		}
		
		if (link.contains("/")) {
			link = link.split("/")[0]
		}
		
		holder.tvUrl.text = link
		holder.root.setOnLongClickListener {
			onChannelClickListener?.onLongClick(channel.link)
			true
		}
		
		holder.root.setOnClickListener { onChannelClickListener?.onClick(channel.link) }
	}
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelsViewHolder {
		return ChannelsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.v_channel, parent, false))
	}
	
	override fun getItemCount(): Int {
		return channelsArrayList.size
	}
	
	class ChannelsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		val root = view
		val tvTitle = view.tvTitle
		val tvUrl = view.tvUrl
	}
	
	
	fun update(channelsArrayList: ArrayList<Channel>) {
		this.channelsArrayList = channelsArrayList
		notifyDataSetChanged()
	}
}