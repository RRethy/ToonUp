package com.bonnetrouge.toonup.Commons.Ext

fun <T> T?.notNullElse(notNullAction: () -> Unit, nullAction: () -> Unit) {
    if (this != null) {
        notNullAction()
    } else {
        nullAction()
    }
}

fun <T> withNotNull(variable: T?, action: T.() -> Unit) {
    variable?.let { variable.action() }
}

fun <T> T.with(action: T.() -> Unit) {
    this.action()
}

fun <T> T?.ifNull(action: () -> Unit) {
    if (this == null) action()
}

fun <T> T?.ifNullElse(nullAction: () -> Unit, notNullAction: () -> Unit) {
    if (this == null) nullAction()
    else {
        notNullAction()
    }
}

fun <T> T?.safeBool(defaultValue: Boolean = false, predicate: T.() -> Boolean): Boolean {
    return if (this != null) this.predicate()
    else defaultValue
}
