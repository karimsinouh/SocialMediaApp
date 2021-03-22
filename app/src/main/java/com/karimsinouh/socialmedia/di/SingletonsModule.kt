package com.karimsinouh.socialmedia.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.karimsinouh.socialmedia.utils.USER_ID
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.ocpsoft.prettytime.PrettyTime
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SingletonsModule {

    @Provides
    @Singleton
    fun glide(@ApplicationContext c:Context)=Glide.with(c).applyDefaultRequestOptions(RequestOptions())

    @Provides
    @Singleton
    fun auth()=Firebase.auth

    @Provides
    @Singleton
    fun firestore()=Firebase.firestore

    @Provides
    @Singleton
    @Named(USER_ID)
    fun userId(auth:FirebaseAuth)=auth.currentUser?.uid!!

    @Provides
    @Singleton
    fun prettyTime()=PrettyTime()


}