package io.github.matirosen.pdschallenge.exception

import io.github.matirosen.pdschallenge.ui.UiText

class InvalidFactorialInputException(uiText: UiText) : CustomException(uiText) {

    constructor(message: String) : this(UiText.DynamicString(message))
}