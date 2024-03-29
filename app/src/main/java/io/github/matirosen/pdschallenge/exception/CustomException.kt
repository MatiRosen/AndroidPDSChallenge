package io.github.matirosen.pdschallenge.exception

import io.github.matirosen.pdschallenge.ui.UiText

open class CustomException(private val uiText: UiText) : Exception() {

    constructor(message: String) : this(UiText.DynamicString(message))

    fun getUiText(): UiText {
        return uiText
    }
}