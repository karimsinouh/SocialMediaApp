package com.karimsinouh.socialmedia.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.karimsinouh.socialmedia.R
import com.karimsinouh.socialmedia.databinding.FragmentHomeBinding
import com.karimsinouh.socialmedia.ui.main.MainViewModel
import com.karimsinouh.socialmedia.utils.hide
import com.karimsinouh.socialmedia.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment:Fragment(R.layout.fragment_home) {

    private lateinit var binding:FragmentHomeBinding

    private val vm by activityViewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding= FragmentHomeBinding.bind(view)

        vm.posts.observe(viewLifecycleOwner){
            if (it.isEmpty())
                binding.message.show()
            else{

            }
            binding.bar.hide()
        }

    }


}