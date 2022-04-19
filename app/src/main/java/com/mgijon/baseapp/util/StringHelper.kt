package com.mgijon.baseapp.util

import android.content.Context
import javax.inject.Inject

class StringHelper @Inject constructor(private val context: Context) {

    fun getString(resource: Int): String = context.getString(resource)
}