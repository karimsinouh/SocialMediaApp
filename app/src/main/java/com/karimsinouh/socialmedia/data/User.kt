package com.karimsinouh.socialmedia.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class User(
        val name:String?="",
        val picture:String?="",
        val bio:String?="",
        val followers:List<String>?= emptyList(),
        val following:List<String>?= emptyList(),
        @ServerTimestamp val joinedAt:Date?=null,
        @DocumentId val id:String?="",
    )
