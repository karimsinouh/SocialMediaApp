package com.karimsinouh.socialmedia.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.karimsinouh.socialmedia.data.Post
import com.karimsinouh.socialmedia.data.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepo @Inject constructor(
        private val db:FirebaseFirestore
) {

    fun getPosts(limit:Long,listener:(Result<List<Post>>)->Unit){
        val query=db.collection("posts").orderBy("date",Query.Direction.DESCENDING).limit(limit)
        query.addSnapshotListener { value, error ->
            if(error!=null){
                listener(Result(false,null,error.message))
                return@addSnapshotListener
            }

            if (value!=null)
                listener(Result(true,value.toObjects(Post::class.java)))
        }
    }

    fun getPostsOf(userId:String,realtimeChanges:Boolean?=false,listener:(Result<List<Post>>)->Unit){
        val query=db.collection("posts").whereEqualTo("userId",userId).orderBy("date",Query.Direction.DESCENDING).limit(20)

        if (realtimeChanges!!)
            query.addSnapshotListener { value, error ->
                if(error!=null){
                    listener(Result(false,null,error.message))
                    return@addSnapshotListener
                }

                if (value!=null)
                    listener(Result(true,value.toObjects(Post::class.java)))
            }
        else
            query.get().addOnCompleteListener {
                listener(Result(it.isSuccessful,it.result?.toObjects(Post::class.java),it.exception?.message))
            }


    }

}