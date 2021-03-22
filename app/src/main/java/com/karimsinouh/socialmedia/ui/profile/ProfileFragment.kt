package com.karimsinouh.socialmedia.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.RequestManager
import com.karimsinouh.socialmedia.R
import com.karimsinouh.socialmedia.data.User
import com.karimsinouh.socialmedia.databinding.FragmentProfileBinding
import com.karimsinouh.socialmedia.ui.main.MainViewModel
import com.karimsinouh.socialmedia.utils.USER_ID
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class ProfileFragment:Fragment(R.layout.fragment_profile) {


    @Inject @Named(USER_ID) lateinit var currentUserId:String
    @Inject lateinit var glide:RequestManager

    private val vm by activityViewModels<MainViewModel>()

    private lateinit var binding:FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentProfileBinding.bind(view)

        subscribeToObservers()

    }

    private fun subscribeToObservers(){
        vm.user.observe(viewLifecycleOwner){
            bindUser(it)
        }
    }

    private fun bindUser(user:User){
        binding.userName.text=user.name
        binding.bio.text=user.bio
        glide.load(user.picture).into(binding.userPicture)

        binding.followers.text=user.followers?.size.toString()
        binding.following.text=user.following?.size.toString()
    }

}