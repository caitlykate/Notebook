package com.caitlykate.notebook.utils

import android.text.Html
import android.text.Spanned

object HtmlManager {
    fun getFromHtml(htmlText: String): Spanned { //editText понимает Spanned
        return if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.N){
            Html.fromHtml(htmlText)
        } else {
            Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT)
        }
    }

    fun toHtml(text: Spanned): String {
        return if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.N){
            Html.toHtml(text)
        } else {
            Html.toHtml(text, Html.FROM_HTML_MODE_COMPACT)
        }
    }
}