package com.karimsinouh.socialmedia.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<MutableList<T>>.addItem(item:T){
    val value=this.value ?: mutableListOf()
    value.add(item)
    this.value=value
}

fun <T> MutableLiveData<MutableList<T>>.removeAt(index:Int){
    val value=this.value ?: mutableListOf()
    value.removeAt(index)
    Log.d("wtf","index $index removed")
    this.value=value
}
