package com.qegle.rsstestapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qegle.rsstestapp.R
import kotlinx.android.synthetic.main.d_channel_add.*

/**
 * Created by Sergey Makhaev on 04.10.2017.
 */

class ChannelAddD : android.support.v4.app.DialogFragment() {
	var onChannelAddListener: OnChannelAddListener? = null
	
	companion object {
		
		fun newInstance(): ChannelAddD {
			return ChannelAddD()
		}
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		btnAdd.setOnClickListener {
			val name = etTitle.text?.toString()
			val link = etLink.text?.toString()
			
			if (link?.isNotEmpty() == true && name?.isNotEmpty() == true) {
				onChannelAddListener?.add(name, link)
				dismiss()
			} else {
				// TODO: 06.08.2018 make toast
			}
		}
	}
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.d_channel_add, container, false)
	}
}