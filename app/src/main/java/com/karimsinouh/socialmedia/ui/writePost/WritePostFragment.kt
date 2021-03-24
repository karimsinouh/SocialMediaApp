package com.karimsinouh.socialmedia.ui.writePost

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.karimsinouh.socialmedia.R
import com.karimsinouh.socialmedia.adapters.ImagesAdapter
import com.karimsinouh.socialmedia.data.Post
import com.karimsinouh.socialmedia.databinding.FragmentWritePostBinding
import com.karimsinouh.socialmedia.service.UploadPostService
import com.karimsinouh.socialmedia.utils.USER_ID
import com.karimsinouh.socialmedia.utils.hide
import com.karimsinouh.socialmedia.utils.show
import com.theartofdev.edmodo.cropper.CropImage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class WritePostFragment: Fragment(R.layout.fragment_write_post) {

    private lateinit var binding:FragmentWritePostBinding
    private lateinit var nav:NavController

    private val vm by viewModels<WritePostViewModel>()
    @Inject lateinit var imagesAdapter:ImagesAdapter
    @Inject @Named(USER_ID) lateinit var userId:String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding= FragmentWritePostBinding.bind(view)
        nav=findNavController()

        onClickListeners()
        setupRcv()
        subscribeToObservers()

        binding.hashTagsInput.doOnTextChanged { text, _, _, _ ->
            vm.setHashTags(text.toString())
        }

    }

    private fun subscribeToObservers() {
        vm.images.observe(viewLifecycleOwner){
            imagesAdapter.submitList(it)

            Log.d("wtf","Observed, size: ${it.size}")

            if (it.isNotEmpty())
                binding.imagesRcv.show()
            else
                binding.imagesRcv.hide()

        }


        vm.hashTags.observe(viewLifecycleOwner){
            binding.chipGroup.removeAllViews()
            for (tag in it){
                val chip=Chip(requireContext())
                chip.text=tag
                binding.chipGroup.addView(chip)
            }
        }
    }

    private fun onClickListeners() {

        //on back button clicked
        binding.toolbar.setNavigationOnClickListener {
            nav.popBackStack()
        }


        //on add pictures clicked
        binding.addPicture.setOnClickListener {
            CropImage.activity().start(requireActivity(),this)
        }


        //when hasTag button clicked
        binding.addHashTags.setOnClickListener {
            binding.hashTagsLayout.apply {
                if (isShown)
                    hide()
                else
                    show()
            }

        }

        //om save clicked
        binding.toolbar.setOnMenuItemClickListener {
            //since it only has one item we won't check if id..
            save()
            true
        }
    }


    private fun save(){
        if (binding.editText.text.isEmpty() && vm.images.value.isNullOrEmpty()){
            Snackbar.make(binding.root,"You cant't save an empty post",Snackbar.LENGTH_SHORT).show()
            return
        }else{

            val post=Post(userId,binding.editText.text.toString())
            post.hashTags=vm.hashTags.value ?: emptyList()

            val intent=Intent(requireContext(),UploadPostService::class.java)


            when{

                !vm.images.value.isNullOrEmpty()->{
                    intent.action=UploadPostService.ACTION_IMAGE
                    intent.putStringArrayListExtra(UploadPostService.KEY_IMAGES,ArrayList(vm.images.value))
                    post.type=Post.TYPE_IMAGE
                }

                vm.videoUri.value!=null->{
                    post.type=Post.TYPE_VIDEO
                    intent.action=UploadPostService.ACTION_VIDEO
                    intent.putExtra(UploadPostService.KEY_VIDEO,vm.videoUri.value)
                }

                else-> {
                    intent.action = UploadPostService.ACTION_TEXT
                    post.type=Post.TYPE_TEXT
                }

            }

            intent.putExtra(UploadPostService.KEY_POST,post)
            requireActivity().startService(intent)
            Toast.makeText(requireContext(),"Your post is being uploaded",Toast.LENGTH_SHORT).show()
            nav.popBackStack()

        }
    }

    private fun setupRcv()=binding.imagesRcv.apply{
        layoutManager=LinearLayoutManager(requireContext()).apply {
            orientation=RecyclerView.HORIZONTAL
        }
        setHasFixedSize(true)
        adapter=imagesAdapter

        imagesAdapter.onRemoveListener {
            vm.removeImage(it)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==Activity.RESULT_OK){
            if (data!=null){
                val uri=CropImage.getActivityResult(data).uri
                vm.addImage(uri.toString())
            }else{
                Toast.makeText(requireContext(),"Something went wrong",Toast.LENGTH_SHORT).show()
            }
        }

    }


}