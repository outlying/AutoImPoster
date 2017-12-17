package com.antyzero.autoinposter.domain.dsl


@Suppress("PropertyName")
inline val Any.TAG: String
    get() = this.javaClass.simpleName