package com.karimsinouh.socialmedia.ui.writePost

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.karimsinouh.socialmedia.utils.addItem
import com.karimsinouh.socialmedia.utils.removeAt

@HiltViewModel
class WritePostViewModel @Inject constructor() :ViewModel() {

    //mutable values
    private val _images=MutableLiveData<MutableList<Uri>>()
    private val _hashTags=MutableLiveData<List<String>>()


    //observable values
    val images:LiveData<MutableList<Uri>> =_images
    val hashTags:LiveData<List<String>> =_hashTags



    //hashTags
    fun setHashTags(tags:String){
        val hashList= mutableListOf<String>()
        for (tag in tags.split(",")){
            if (tag!="" && tag!=" "){
                hashList.add(tag)
            }
        }
        _hashTags.value=hashList

    }

    //images
    fun addImage(uri:Uri){ _images.addItem(uri) }
    fun removeImage(index:Int){ _images.removeAt(index) }

}