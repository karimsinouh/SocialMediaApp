package com.karimsinouh.socialmedia.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.karimsinouh.socialmedia.data.Result
import com.karimsinouh.socialmedia.data.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepo @Inject constructor(
        private val db: FirebaseFirestore
) {

    fun getUser(id:String,realtimeChanges:Boolean?=false,listener:(Result<User>)->Unit){
        if (realtimeChanges!!)
            db.collection("users").document(id).addSnapshotListener { value, error ->
                if (error!=null){
                    listener(Result(false,null,error.message))
                    return@addSnapshotListener
                }

                if(value!=null)
                    listener(Result(true,value.toObject(User::class.java)))
            }
        else
            db.collection("users").document(id).get().addOnCompleteListener {
                listener(Result(it.isSuccessful,it.result?.toObject(User::class.java),it.exception?.message))
            }
    }

}