package com.karimsinouh.socialmedia.data

data class User(
    val id:String?="",
    val name:String?="",
    val picture:String?="",
    val bio:String?="",
    val followers:List<String>?= emptyList(),
    val following:List<String>?= emptyList()
)
