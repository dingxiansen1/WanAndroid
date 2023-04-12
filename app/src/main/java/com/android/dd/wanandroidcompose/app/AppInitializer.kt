package com.android.dd.wanandroidcompose.app

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.android.dd.wanandroidcompose.net.RetrofitManager
import com.dd.common.manager.CrashManager

class AppInitializer : Initializer<Unit> {

    override fun create(context: Context) {

        RetrofitManager.setBaseUrl("https://www.wanandroid.com")

        CrashManager.init(object : CrashManager.ICrashCallback {

            override fun crashCallback(filePath: String?) {
                Log.e(" filePath：", "$filePath")
                // 上传文件
            }
        })

    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}