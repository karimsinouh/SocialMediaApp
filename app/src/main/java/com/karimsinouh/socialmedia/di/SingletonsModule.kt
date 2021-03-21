package com.karimsinouh.socialmedia.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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


}