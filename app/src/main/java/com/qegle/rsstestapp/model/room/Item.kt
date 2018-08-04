package com.qegle.rsstestapp.model.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = Channel::class,
		parentColumns = arrayOf("uid"),
		childColumns = arrayOf("ownerId"),
		onDelete = CASCADE)])
class Item(@PrimaryKey(autoGenerate = true)
           var id: Int?,
           var date: String,
           var title: String,
           var link: String,
           var description: String,
           var channelId: String)
