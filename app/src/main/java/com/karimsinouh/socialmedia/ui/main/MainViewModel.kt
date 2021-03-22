package com.karimsinouh.socialmedia.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karimsinouh.socialmedia.data.Post
import com.karimsinouh.socialmedia.data.User
import com.karimsinouh.socialmedia.repositories.PostRepo
import com.karimsinouh.socialmedia.repositories.UserRepo
import com.karimsinouh.socialmedia.utils.USER_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class MainViewModel @Inject constructor(
        private val userRepo: UserRepo,
        private val postsRepo:PostRepo
):ViewModel() {

    private var postsLimit=10L
    private var canLoadMore=true

    //mutable values
    private val _user=MutableLiveData<User>()
    private val _error=MutableLiveData<String>()
    private val _posts=MutableLiveData<List<Post>>()


    //observable values
    val user:LiveData<User> =_user
    val posts:LiveData<List<Post>> =_posts

    fun loadCurrentUser(id:String){
        userRepo.getUser(id,true){
            if(it.isSuccessful)
                _user.postValue(it.data)
            else
                _error.postValue(it.message)
        }
    }

    fun loadPosts(){
        if (canLoadMore)
            postsRepo.getPosts(postsLimit) {
                if (it.isSuccessful) {
                    _posts.postValue(it.data)
                    if (postsLimit+it.data?.size!! <postsLimit+10)
                        canLoadMore=false
                    postsLimit += 10
                } else
                    _error.postValue(it.message)

            }
    }



}