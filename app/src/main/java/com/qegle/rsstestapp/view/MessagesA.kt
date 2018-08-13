package com.qegle.rsstestapp.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.qegle.rsstestapp.R
import com.qegle.rsstestapp.util.Constants.CHANNEL_ID
import com.qegle.rsstestapp.util.ErrorType
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
		messagesAdapter.onLinkClickListener = viewModel
		
		rvMessages.adapter = messagesAdapter
		rvMessages.layoutManager = LinearLayoutManager(this)
		srLayout.setOnRefreshListener {
			viewModel.update { runOnUiThread { srLayout?.isRefreshing = false } }
		}
		
		viewModel.errorSubject.subscribe {
			val message = when (it) {
				ErrorType.LINK_NOT_VALID, ErrorType.PARSING -> getString(R.string.check_internet)
				ErrorType.ACTIVITY_NOT_FOUND -> getString(R.string.dont_have_browser)
				else -> getString(R.string.something_went_wrong)
			}
			
			showSnack(message)
		}
		observeViewModel(viewModel)
	}
	
	private fun showSnack(text: String) {
		Snackbar.make(clRoot, text, Snackbar.LENGTH_SHORT).show()
	}
	
	private fun observeViewModel(viewModel: MessagesViewModel) {
		viewModel.messagesArray.observe(this, Observer {
			srLayout?.isRefreshing = false
			tvPlaceholder.visibility = if (it?.isNotEmpty() == true) View.GONE else View.VISIBLE
			
			messagesAdapter.update(it ?: arrayListOf())
		})
	}
}
