package com.karimsinouh.socialmedia.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.io.Serializable
import java.util.*

data class Post(
        var userId:String?="",
        val text:String?="",
        var pictures:List<String>?= emptyList(),
        val videoUrl:String?=null,
        val likes:List<String>?= emptyList(),
        var hashTags:List<String>?= emptyList(),
        var type:Int?= TYPE_TEXT,
        @DocumentId val id:String?="",
        @ServerTimestamp val date:Date?=null,
):Serializable{
    companion object{
        const val TYPE_TEXT=0
        const val TYPE_IMAGE=1
        const val TYPE_VIDEO=2
    }
}