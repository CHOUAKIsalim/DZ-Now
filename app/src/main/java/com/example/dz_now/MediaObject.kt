package com.example.dz_now

class MediaObject {

    private var uId: Int = 0
    private var title: String? = null
    private var mediaUrl: String? = null
    private var mediaCoverImgUrl: String? = null
    private var userHandle: String? = null

    fun getUserHandle(): String? {
        return userHandle
    }

    fun setUserHandle(mUserHandle: String) {
        this.userHandle = mUserHandle
    }

    fun getId(): Int {
        return uId
    }

    fun setId(uId: Int) {
        this.uId = uId
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(mTitle: String) {
        this.title = mTitle
    }

    fun getUrl(): String? {
        return mediaUrl
    }

    fun setUrl(mUrl: String) {
        this.mediaUrl = mUrl
    }

    fun getCoverUrl(): String? {
        return mediaCoverImgUrl
    }

    fun setCoverUrl(mCoverUrl: String) {
        this.mediaCoverImgUrl = mCoverUrl
    }
}