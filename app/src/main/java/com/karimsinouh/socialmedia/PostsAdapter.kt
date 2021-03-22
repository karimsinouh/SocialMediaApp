package com.karimsinouh.socialmedia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.karimsinouh.socialmedia.data.Post
import com.karimsinouh.socialmedia.data.User
import com.karimsinouh.socialmedia.databinding.ItemPostBinding
import com.karimsinouh.socialmedia.repositories.UserRepo
import com.karimsinouh.socialmedia.utils.hide
import com.karimsinouh.socialmedia.utils.show
import javax.inject.Inject

class PostsAdapter @Inject constructor(
        private val glide:RequestManager,
        private val userRepo:UserRepo
):RecyclerView.Adapter<PostsAdapter.PostHolder>() {

    inner class PostHolder(private val binding:ItemPostBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(post:Post){
            binding.text.text=post.text

            if (post.pictures!!.isNotEmpty())
                binding.picture.also {
                    it.show()
                    glide.load(post.pictures[0]).into(it)
                }

            userRepo.getUser(post.userId!!){
                if (it.isSuccessful)
                    bindUser(it.data!!)
            }

        }

        private fun bindUser(user:User)=binding.apply {
            userSection.userName.text=user.name
            userSection.subText.text="@Developer"
            glide.load(user.picture).into(userSection.userPicture)

            userSection.placeholder.hide()
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
            PostHolder(ItemPostBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
       val item=differ.currentList[position]
        holder.bind(item)
    }

    override fun getItemCount()=differ.currentList.size


    //diff utils
    private val diffCallback=object:DiffUtil.ItemCallback<Post>(){
        override fun areItemsTheSame(oldItem: Post, newItem: Post)=oldItem.id==newItem.id
        override fun areContentsTheSame(oldItem: Post, newItem: Post)=oldItem.hashCode()==newItem.hashCode()
    }

    private val differ=AsyncListDiffer(this,diffCallback)

    fun submitList(it:List<Post>)=differ.submitList(it)

}