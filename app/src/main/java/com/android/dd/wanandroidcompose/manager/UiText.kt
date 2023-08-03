package com.android.dd.wanandroidcompose.manager

import android.content.Context
import androidx.annotation.StringRes

/**
 * UiText 是一个密封接口，用來封裝不同形式的文字內容。
 */
sealed class UiText {

    /**
     * DynamicString 是 UiText 的一種形式，表示可以动态变化的文字。
     * @param value 文字的內容。
     */
    data class DynamicString(val value: String) : UiText()

    /**
     * StringResource 是 UiText 的一种形式，表示來自 Android 资源的文字。
     * @param resId 文字资源的 ID。
     * @param args 文字资源的参数列表。
     */
    data class StringResource(
        @StringRes val resId: Int,
        val args: List<Args> = emptyList()
    ) : UiText() {

        /**
         * Args 是 StringResource 參數的密封接口，可以是动态字串或 UiText 類型。
         */
        sealed class Args {

            /**
             * DynamicString 是 Args 的一種形式，表示可以动态变化的文字。
             * @param value 文字的內容。
             */
            data class DynamicString(val value: String) : Args()

            /**
             * UiTextArg 是 Args 的一种形式，表示其他的 UiText 类型。
             * @param uiText 被封裝的 UiText 物件。
             */
            data class UiTextArg(val uiText: UiText): Args()

            /**
             * toString 方法將 Args 物件转换为字串。
             * @param context Android 上下文物件。
             */
            fun toString(context: Context) =
                when (this) {
                    is DynamicString -> value
                    is UiTextArg -> uiText.asString(context)
                }
        }
    }

    /**
     * asString 方法將 UiText 物件轉換為字串。
     * @param context Android 上下文物件。
     */
    fun asString(context: Context): String =
        when (this) {
            is DynamicString -> value
            is StringResource ->
                context.getString(
                    resId, *(args.map { it.toString(context) }.toTypedArray())
                )
        }
}