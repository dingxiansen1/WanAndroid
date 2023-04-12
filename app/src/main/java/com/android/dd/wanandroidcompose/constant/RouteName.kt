package com.android.dd.wanandroidcompose.constant


object RouteName {
    const val Main = "Main"
    const val SeriesDesc = "SeriesDesc"
    const val TutorialDesc = "TutorialDesc"
    const val SearchResult = "SearchResult"
    const val Search = "Search"
    const val Web = "Web"
    const val Login = "Login"
    const val Collection = "Collection"
    const val History = "History"
    const val Tool = "Tool"
    const val Message = "Message"
    const val Setting = "Setting"

    object Arguments {
        const val key = "key"
        const val cid = "cid"
    }

    object MainRoute {
        const val Home = "Home"
        const val Project = "Project"
        const val Author = "Author"
        const val Navigator = "Navigator"
        const val Mine = "Mine"
    }

    fun searchResultArguments(
        key: String,
    ): String {
        return SearchResult + "?key=${key}"
    }

    fun seriesDescArguments(
        cid: Int,
    ): String {
        return SeriesDesc + "?cid=${cid}"
    }

    fun tutorialDescArguments(
        cid: Int,
    ): String {
        return TutorialDesc + "?cid=${cid}"
    }

    fun webArguments(
        link: String,
        title: String,
    ): String {
        return Web + "?link=${link}&title=${title}"
    }
}