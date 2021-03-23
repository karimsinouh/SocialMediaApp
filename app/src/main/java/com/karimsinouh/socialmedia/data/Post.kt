package com.karimsinouh.socialmedia.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Post(
        val userId:String?="",
        val text:String?="",
        val pictures:List<String>?= emptyList(),
        val videoUrl:String?=null,
        val likes:List<String>?= emptyList(),
        val type:Int?= TYPE_TEXT,
        @DocumentId val id:String?="",
        @ServerTimestamp val date:Date?=null,
){
    companion object{
        const val TYPE_TEXT=0
        const val TYPE_IMAGE=1
        const val TYPE_VIDEO=2
    }
}