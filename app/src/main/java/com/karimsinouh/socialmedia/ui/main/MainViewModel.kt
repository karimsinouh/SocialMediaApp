package com.karimsinouh.socialmedia.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karimsinouh.socialmedia.data.User
import com.karimsinouh.socialmedia.repositories.UserRepo
import com.karimsinouh.socialmedia.utils.USER_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class MainViewModel @Inject constructor(
        private val userRepo: UserRepo
):ViewModel() {

    private val _user=MutableLiveData<User>()
    private val _error=MutableLiveData<String>()


    val user:LiveData<User> =_user

    fun loadCurrentUser(id:String){
        userRepo.getUser(id,true){
            if(it.isSuccessful)
                _user.postValue(it.data)
            else
                _error.postValue(it.message)
        }
    }



}