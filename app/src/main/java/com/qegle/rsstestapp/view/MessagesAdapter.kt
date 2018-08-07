package com.qegle.rsstestapp.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qegle.rsstestapp.R
import com.qegle.rsstestapp.model.room.Item
import kotlinx.android.synthetic.main.v_message.view.*

class MessagesAdapter : RecyclerView.Adapter<MessagesAdapter.ChannelsViewHolder>() {
	private var messagesArrayList: ArrayList<Item> = arrayListOf()
	var onItemClickListener: OnItemClickListener? = null
	override fun onBindViewHolder(holder: ChannelsViewHolder, position: Int) {
		val item = messagesArrayList[position]
		holder.tvTitle.text = item.title
		holder.tvDate.text = item.date
		holder.tvDesc.text = item.description
		holder.ivLink.visibility = if (item.link != null) View.VISIBLE else View.GONE
		
		holder.root.setOnClickListener {
			val link = item.link
			if (link != null)
				onItemClickListener?.onClick(link)
		}
	}
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelsViewHolder {
		return ChannelsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.v_message, parent, false))
	}
	
	override fun getItemCount(): Int {
		return messagesArrayList.size
	}
	
	class ChannelsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		val root = view
		val tvTitle = view.tvTitle
		val tvDate = view.tvDate
		val tvDesc = view.tvDesc
		val ivLink = view.ivLink
	}
	
	
	fun update(messagesArrayList: ArrayList<Item>) {
		this.messagesArrayList = messagesArrayList
		notifyDataSetChanged()
	}
}