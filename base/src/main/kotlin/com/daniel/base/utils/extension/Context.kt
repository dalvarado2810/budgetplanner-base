package com.daniel.base.utils.extension

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

fun Context.openBrowser(url: String) {
    runCatching {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())

        startActivity(intent)
    }
}