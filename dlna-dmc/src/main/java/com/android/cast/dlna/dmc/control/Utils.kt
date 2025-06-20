/*
 * Copyright (C) 2014 Kevin Shen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.cast.dlna.dmc.control

import android.os.Handler
import android.os.Looper
import com.android.cast.dlna.core.Logger
import kotlinx.coroutines.suspendCancellableCoroutine
import org.fourthline.cling.support.model.item.VideoItem
import kotlin.coroutines.resume

internal object MetadataUtils {
    private const val DIDL_LITE_XML =
        """<?xml version="1.0"?><DIDL-Lite xmlns="urn:schemas-upnp-org:metadata-1-0/DIDL-Lite/" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:upnp="urn:schemas-upnp-org:metadata-1-0/upnp/">%s</DIDL-Lite>"""

    fun create(url: String, title: String) = DIDL_LITE_XML.format(buildItemXml(url, title))

    fun createBaseOnType(url: String, title: String) =
        DIDL_LITE_XML.format(buildItemXmlBaseOnType(url, title))

    private fun buildItemXml(url: String, title: String): String {
        val item = VideoItem(title, "-1", title, null)
        val builder = StringBuilder()
        builder.append("<item id=\"$title\" parentID=\"-1\" restricted=\"1\">")
        builder.append("<dc:title>$title</dc:title>")
        builder.append("<upnp:class>${item.clazz.value}</upnp:class>")
        builder.append("<res protocolInfo=\"http-get:*:video/mp4:*;DLNA.ORG_OP=01;\">$url</res>")
        builder.append("</item>")
        return builder.toString()
    }

    private fun buildItemXmlBaseOnType(url: String, title: String): String {
        val mimeType = if (url.endsWith(".m3u8")) {
            "application/vnd.apple.mpegurl"
        } else {
            "video/mp4"
        }

        val item = VideoItem(title, "-1", title, null)
        return """
        <item id="$title" parentID="-1" restricted="1">
            <dc:title>$title</dc:title>
            <upnp:class>${item.clazz.value}</upnp:class>
            <res protocolInfo="http-get:*:$mimeType:*;DLNA.ORG_OP=01;">$url</res>
        </item>
    """.trimIndent()
    }

}

private val mainHandler = Handler(Looper.getMainLooper())

fun executeInMainThread(runnable: Runnable) {
    if (Thread.currentThread() == Looper.getMainLooper().thread) {
        runnable.run()
    } else {
        mainHandler.post(runnable)
    }
}

private val logger = Logger.create("DeviceControlExtension")

suspend fun <T> safeCoroutineLauncher(action: (callback: ServiceActionCallback<T>) -> Unit): Result<T> {
    return suspendCancellableCoroutine { continuation ->
        val callback = object : ServiceActionCallback<T> {
            override fun onSuccess(result: T) {
                if (continuation.isActive) {
                    continuation.resume(Result.success(result))
                }
            }

            override fun onFailure(msg: String) {
                if (continuation.isActive) {
                    continuation.resume(Result.failure(Exception(msg)))
                }
            }
        }
        action.invoke(callback)

        continuation.invokeOnCancellation {
            logger.d("invokeOnCancellation with extension function")
        }
    }
}