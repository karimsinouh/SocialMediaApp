package com.karimsinouh.socialmedia.ui.writePost

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WritePostViewModel @Inject constructor() :ViewModel() {

    //mutable values
    private val _images=MutableLiveData<MutableList<String>>()


    //observable values
    val images:LiveData<MutableList<String>> =_images


    fun addImage(uri:String){
        _images.addItem(uri)
    }

    fun removeImage(index:Int){
        _images.removeAt(index)
    }

    private fun <T> MutableLiveData<MutableList<T>>.addItem(image:T){
        val value=this.value ?: mutableListOf()
        value.add(image)
        this.postValue(value)
    }

    private fun <T> MutableLiveData<MutableList<T>>.removeAt(index:Int){
        val value=this.value ?: mutableListOf()
        value.removeAt(index)
    }

}