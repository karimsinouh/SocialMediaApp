package com.karimsinouh.socialmedia.ui.writePost

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karimsinouh.socialmedia.data.Post
import com.karimsinouh.socialmedia.utils.USER_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.karimsinouh.socialmedia.utils.addItem
import com.karimsinouh.socialmedia.utils.removeAt
import javax.inject.Named

@HiltViewModel
class WritePostViewModel @Inject constructor(
    @Named(USER_ID) private val uid:String
) :ViewModel() {

    //mutable values
    private val _images=MutableLiveData(mutableListOf<String>())
    private val _hashTags=MutableLiveData<List<String>>()
    private var _error=MutableLiveData<String>()
    private val _videoUri=MutableLiveData<String?>()

    //observable values
    val images:LiveData<MutableList<String>> =_images
    val hashTags:LiveData<List<String>> =_hashTags
    val error:LiveData<String> =_error
    val videoUri:LiveData<String?> =_videoUri


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
    fun addImage(uri:String){ _images.addItem(uri) }
    fun removeImage(index:Int){ _images.removeAt(index) }

}