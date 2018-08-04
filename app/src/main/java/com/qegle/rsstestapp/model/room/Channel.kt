package com.qegle.rsstestapp.model.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Channel(var title: String, @PrimaryKey var link: String)