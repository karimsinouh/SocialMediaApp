package com.karimsinouh.socialmedia.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.karimsinouh.socialmedia.PostsAdapter
import com.karimsinouh.socialmedia.R
import com.karimsinouh.socialmedia.databinding.FragmentHomeBinding
import com.karimsinouh.socialmedia.ui.main.MainViewModel
import com.karimsinouh.socialmedia.utils.hide
import com.karimsinouh.socialmedia.utils.show
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment:Fragment(R.layout.fragment_home) {

    private lateinit var binding:FragmentHomeBinding
    private lateinit var nav:NavController

    private val vm by activityViewModels<MainViewModel>()
    @Inject lateinit var adapter:PostsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav=findNavController()

        binding= FragmentHomeBinding.bind(view)

        setupRcv()

        subscribeToObservers()

        adapter.setOnClickListener { _, user ->
            Toast.makeText(requireContext(),user.name,Toast.LENGTH_SHORT).show()
        }

        binding.writePost.setOnClickListener {
            nav.navigate(R.id.home_to_writePost)
        }

    }

    private fun subscribeToObservers(){
        vm.posts.observe(viewLifecycleOwner){
            if (it.isEmpty())
                binding.message.show()
            else
                adapter.submitList(it)

            binding.bar.hide()
        }
    }

    private fun setupRcv()=binding.rcv.apply {
        layoutManager = LinearLayoutManager(requireContext())
        setHasFixedSize(true)
        adapter = this@HomeFragment.adapter
    }

}