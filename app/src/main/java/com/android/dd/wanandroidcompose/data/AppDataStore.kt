package com.android.dd.wanandroidcompose.data

import com.dd.common.utils.datastore.DataStoreOwner

object AppDataStore : DataStoreOwner("WanAndroidCompose") {
    val account by stringPreference("")
    val password by stringPreference("")
    val cookie by stringPreference("")
}