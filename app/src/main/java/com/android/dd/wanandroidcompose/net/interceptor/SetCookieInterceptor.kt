package com.android.dd.wanandroidcompose.net.interceptor

import com.android.dd.wanandroidcompose.data.AppDataStore
import okhttp3.Interceptor
import okhttp3.Response

class SetCookieInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        val domain = request.url.host
        //获取domain内的cookie
        if (domain.isNotEmpty()) {
            val cookie: String = AppDataStore.cookie.getSync() ?: ""
            if (cookie.isNotEmpty()) {
                builder.addHeader("Cookie", cookie)
            }
        }
        return chain.proceed(builder.build())
    }
}