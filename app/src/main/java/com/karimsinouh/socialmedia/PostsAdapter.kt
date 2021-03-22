package com.karimsinouh.socialmedia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.karimsinouh.socialmedia.data.Post
import com.karimsinouh.socialmedia.data.User
import com.karimsinouh.socialmedia.databinding.ItemPostBinding
import com.karimsinouh.socialmedia.repositories.UserRepo
import com.karimsinouh.socialmedia.utils.USER_ID
import com.karimsinouh.socialmedia.utils.hide
import com.karimsinouh.socialmedia.utils.show
import org.ocpsoft.prettytime.PrettyTime
import javax.inject.Inject
import javax.inject.Named

class PostsAdapter @Inject constructor(
        private val glide:RequestManager,
        private val userRepo:UserRepo,
        @Named(USER_ID) private val currentUserId:String,
        private val prettyTime: PrettyTime
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
                if (it.isSuccessful) {
                    bindUser(post,it.data!!)
                }

            }

        }

        private fun bindUser(post:Post,user:User)=binding.apply {
            userSection.userName.text=user.name
            userSection.subText.text=prettyTime.format(post.date)
            glide.load(user.picture).into(userSection.userPicture)

            userSection.placeholder.hide()

            root.setOnClickListener {
                onClick?.let{
                    it(post,user)
                }
            }

            root.setOnLongClickListener { view->
                onLongClick?.let {
                    it(view,post)
                }
                true
            }

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

    //callbacks
    private var onClick:( (Post,User)->Unit )?=null
    private var onLongClick:( (View, Post)->Unit )?=null

    fun setOnClickListener(listener:(Post,User)->Unit){
        onClick=listener
    }

    fun setOnLongClickListener(listener:(View,Post)->Unit){
        onLongClick=listener
    }

}