package com.example.lightdictionary.utils

fun String?.toUrl(): String {
    this?.let {
        return "https:${this}"
    } ?: return ""
}

fun String.concatWithRoundBrackets(arg: String?): String {
    return if (!arg.isNullOrEmpty()) {
        "$this ($arg)"
    } else
        this
}