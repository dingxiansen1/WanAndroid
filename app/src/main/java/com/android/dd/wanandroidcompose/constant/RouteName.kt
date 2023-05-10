package com.android.dd.wanandroidcompose.constant


object RouteName {
    const val SeriesDesc = "SeriesDesc"
    const val TutorialDesc = "TutorialDesc"
    const val Collection = "Collection"
    const val History = "History"
    const val Tool = "Tool"
    const val Message = "Message"
    const val Setting = "Setting"

    object Arguments {
        const val key = "key"
        const val cid = "cid"
    }

    object MainNavItem {
        const val Home = "Home"
        const val Project = "Project"
        const val Author = "Author"
        const val Navigator = "Navigator"
        const val Mine = "Mine"
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

}