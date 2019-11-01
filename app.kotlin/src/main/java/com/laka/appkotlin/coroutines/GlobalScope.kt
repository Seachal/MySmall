package com.laka.appkotlin.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class GlobalScope : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = EmptyCoroutineContext
}