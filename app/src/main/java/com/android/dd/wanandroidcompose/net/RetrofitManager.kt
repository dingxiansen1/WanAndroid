package com.android.dd.wanandroidcompose.net

import android.annotation.SuppressLint
import com.android.dd.wanandroidcompose.net.interceptor.CacheCookieInterceptor
import com.android.dd.wanandroidcompose.net.interceptor.LogInterceptor
import com.android.dd.wanandroidcompose.net.interceptor.SetCookieInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.*


object RetrofitManager {


    private var baseUrl = ""

    fun setBaseUrl(string: String) {
        baseUrl = string
    }

    /**
     * 请求超时时间
     */
    private var DEFAULT_TIMEOUT = 20000

    //    实例化Retrofit
    val retrofit: HttpService by lazy {
        Retrofit.Builder()
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(HttpService::class.java)
    }

    //手动创建一个OkHttpClient并设置超时时间
    val okHttp: OkHttpClient
        get() {
            return OkHttpClient.Builder().run {
                connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
                readTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
                writeTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
                addInterceptor(SetCookieInterceptor())
                addInterceptor(CacheCookieInterceptor())
                addInterceptor(LogInterceptor())
                hostnameVerifier(TrustAllNameVerifier())
                build()
            }
        }

    class TrustAllNameVerifier : HostnameVerifier {
        @SuppressLint("BadHostnameVerifier")
        override fun verify(hostname: String?, session: SSLSession?): Boolean = true
    }
}