package com.qegle.rsstestapp.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qegle.rsstestapp.R
import com.qegle.rsstestapp.model.parser.RSSChannel
import kotlinx.android.synthetic.main.v_channel.view.*

class ChannelsAdapter : RecyclerView.Adapter<ChannelsAdapter.ChannelsViewHolder>() {
	var channelsArrayList: ArrayList<RSSChannel> = arrayListOf()
	var onChannelClickListener: OnChannelClickListener? = null
	override fun onBindViewHolder(holder: ChannelsViewHolder, position: Int) {
		val rssChanel = channelsArrayList[position]
		holder.tvTitle.text = rssChanel.name
		holder.tvUrl.text = rssChanel.link
		holder.root.setOnLongClickListener {
			onChannelClickListener?.onLongClick(rssChanel.name)
			true
		}
		
		holder.root.setOnClickListener { onChannelClickListener?.onClick(rssChanel.name) }
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
	
	
	fun update(channelsArrayList: ArrayList<RSSChannel>) {
		this.channelsArrayList = channelsArrayList
		notifyDataSetChanged()
	}
}