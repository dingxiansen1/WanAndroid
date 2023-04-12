package com.android.dd.wanandroidcompose.net

import com.android.dd.wanandroidcompose.data.entity.*
import retrofit2.http.*

interface HttpService {


    //首页
    @GET("/article/list/{page}/json")
    suspend fun getIndexList(@Path("page") page: Int): BasicBean<ListWrapper<Article>>

    //广场
    @GET("/user_article/list/{page}/json")
    suspend fun getSquareData(@Path("page") page: Int): BasicBean<ListWrapper<Article>>

    //问答
    @GET("/wenda/list/{page}/json")
    suspend fun getWendaData(@Path("page") page: Int): BasicBean<ListWrapper<Article>>

    //banner
    @GET("/banner/json")
    suspend fun getBanners(): BasicBean<List<HomeBanner>>

    /**
     * 项目分类数据
     */
    @GET("project/tree/json")
    suspend fun getProjectTitleList(): BasicBean<List<Category>>


    /**
     * 项目文章列表
     */
    @GET("project/list/{pageNo}/json")
    suspend fun getProjectPageList(
        @Path("pageNo") pageNo: Int,
        @Query("cid") categoryId: Int
    ): BasicBean<ListWrapper<Article>>


    /**
     * 公众号作者列表
     */
    @GET("wxarticle/chapters/json")
    suspend fun getAuthorTitleList(): BasicBean<List<Category>>

    /**
     * 对于id作者的文章
     */
    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun getAuthorArticles(
        @Path("page") page: Int,
        @Path("id") id: Int,
    ): BasicBean<ListWrapper<Article>>


    /**
     * 导航数据
     */
    @GET("navi/json")
    suspend fun getNavigationList(): BasicBean<List<Navigation>>


    /**
     * 体系数据
     */
    @GET("tree/json")
    suspend fun getTreeList(): BasicBean<List<Series>>

    /**
     * 教程列表
     */
    @GET("chapter/547/sublist/json")
    suspend fun getTutorialList(): BasicBean<List<Classify>>

    /**
     * 对应教程的章节列表
     */
    @GET("article/list/0/json")
    suspend fun getTutorialChapterList(
        @Query("cid") id: Int,
        @Query("order_type") orderType: Int = 1
    ): BasicBean<ListWrapper<Article>>

    /**
     * 系列对应Tag的文章列表
     */
    @GET("article/list/{page}/json")
    suspend fun getSeriesDetailList(
        @Path("page") page: Int,
        @Query("cid") id: Int,
    ): BasicBean<ListWrapper<Article>>


    /**
     * 热搜词
     */
    @GET("hotkey/json")
    suspend fun getSearchHotKey(): BasicBean<List<HotKey>>

    /**
     * 搜索
     */
    @POST("article/query/{page}/json")
    @FormUrlEncoded
    suspend fun queryBySearchKey(
        @Path("page") page: Int,
        @Field("k") key: String
    ): BasicBean<ListWrapper<Article>>

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): BasicBean<User>

    @GET("user/logout/json")
    suspend fun logout(): BasicBean<Any>

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") confirmPassword: String
    ): BasicBean<User>

    /**
     * 获取用户信息
     */
    @GET("user/lg/userinfo/json")
    suspend fun getUserInfo(): BasicBean<UserBaseInfo>


    /**
     * 收藏列表
     */
    @GET("lg/collect/list/{page}/json")
    suspend fun getCollectList(@Path("page") page: Int): BasicBean<ListWrapper<CollectBean>>

    /**
     * 已读消息列表
     */
    @GET("message/lg/readed_list/{page}/json")
    suspend fun getReadiedMessageList(@Path("page") page: Int): BasicBean<ListWrapper<MsgBean>>

    /**
     * 未读消息列表
     */
    @GET("message/lg/unread_list//{page}/json")
    suspend fun getUnReadMessageList(@Path("page") page: Int): BasicBean<ListWrapper<MsgBean>>

    /**
     * 收藏站内文章
     */
    @POST("lg/collect/{id}/json")
    suspend fun collectArticle(@Path("id") id: Int): BasicBean<Any>

    /**
     * 取消收藏站内文章
     */
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun unCollectArticle(@Path("id") id: Int): BasicBean<Any>

    /**
     * 工具列表
     */
    @GET("tools/list/json")
    suspend fun getToolList(): BasicBean<List<ToolBean>>
}