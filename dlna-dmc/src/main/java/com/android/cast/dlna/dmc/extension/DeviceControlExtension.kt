package com.android.cast.dlna.dmc.extension

import com.android.cast.dlna.dmc.DLNACastManager
import com.android.cast.dlna.dmc.control.DeviceControl
import com.android.cast.dlna.dmc.control.EmptyDeviceControl
import com.android.cast.dlna.dmc.control.OnDeviceControlListener
import com.android.cast.dlna.dmc.control.safeCoroutineLauncher
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import org.fourthline.cling.model.meta.Device
import org.fourthline.cling.support.lastchange.EventedValue
import org.fourthline.cling.support.model.BrowseFlag

suspend fun DeviceControl.setAVTransportURIAsync(uri: String, title: String) =
    safeCoroutineLauncher { callback ->
        setAVTransportURI(uri = uri, title = title, callback = callback)
    }

suspend fun DeviceControl.setNextAVTransportURIAsync(uri: String, title: String) =
    safeCoroutineLauncher { callback ->
        setNextAVTransportURI(uri = uri, title = title, callback = callback)
    }


suspend fun DeviceControl.playAsync(speed: String = "1") = safeCoroutineLauncher { callback ->
    play(speed = speed, callback = callback)
}

suspend fun DeviceControl.stopAsync() = safeCoroutineLauncher { callback ->
    stop(callback = callback)
}

suspend fun DeviceControl.pauseAsync() = safeCoroutineLauncher { callback ->
    pause(callback = callback)
}

suspend fun DeviceControl.nextAsync() = safeCoroutineLauncher { callback ->
    next(callback = callback)
}

suspend fun DeviceControl.previousAsync() = safeCoroutineLauncher { callback ->
    previous(callback = callback)
}

suspend fun DeviceControl.seekAsync(millSeconds: Long) = safeCoroutineLauncher { callback ->
    seek(millSeconds = millSeconds, callback = callback)
}

suspend fun DeviceControl.getPositionInfoAsync() = safeCoroutineLauncher { callback ->
    getPositionInfo(callback = callback)
}

suspend fun DeviceControl.getMediaInfoAsync() = safeCoroutineLauncher { callback ->
    getMediaInfo(callback = callback)
}

suspend fun DeviceControl.getTransportInfoAsync() = safeCoroutineLauncher { callback ->
    getTransportInfo(callback = callback)
}

suspend fun DeviceControl.setVolumeAsync(volume: Int) = safeCoroutineLauncher { callback ->
    setVolume(volume = volume, callback = callback)
}

suspend fun DeviceControl.getVolumeAsync() = safeCoroutineLauncher { callback ->
    getVolume(callback = callback)
}

suspend fun DeviceControl.setMuteAsync(mute: Boolean) = safeCoroutineLauncher { callback ->
    setMute(mute = mute, callback = callback)
}

suspend fun DeviceControl.getMuteAsync() = safeCoroutineLauncher { callback ->
    getMute(callback = callback)
}

suspend fun DeviceControl.browseAsync(
    objectId: String,
    flag: BrowseFlag,
    filter: String,
    firstResult: Int,
    maxResults: Int,
) = safeCoroutineLauncher { callback ->
    browse(
        objectId = objectId,
        flag = flag,
        filter = filter,
        firstResult = firstResult,
        maxResults = maxResults,
        callback = callback
    )
}

suspend fun DeviceControl.searchAsync(
    containerId: String,
    searchCriteria: String,
    filter: String,
    firstResult: Int,
    maxResults: Int,
) = safeCoroutineLauncher { callback ->
    search(
        containerId = containerId,
        searchCriteria = searchCriteria,
        filter = filter,
        firstResult = firstResult,
        maxResults = maxResults,
        callback = callback
    )
}

sealed interface DeviceConnectionEvent {
    data class Connected(val device: Device<*, *, *>) : DeviceConnectionEvent
    data class Disconnected(val device: Device<*, *, *>) : DeviceConnectionEvent
    data class EventChanged(val event: EventedValue<*>) : DeviceConnectionEvent
}

suspend fun DLNACastManager.connectDeviceFlow(
    device: Device<*, *, *>
): Pair<DeviceControl, Flow<DeviceConnectionEvent>> {
    val deviceControlReady = CompletableDeferred<DeviceControl>()

    val flow = callbackFlow {
        val listener = object : OnDeviceControlListener {
            override fun onConnected(device: Device<*, *, *>) {
                trySend(DeviceConnectionEvent.Connected(device))
            }

            override fun onDisconnected(device: Device<*, *, *>) {
                trySend(DeviceConnectionEvent.Disconnected(device))
            }

            override fun onEventChanged(event: EventedValue<*>) {
                trySend(DeviceConnectionEvent.EventChanged(event))
            }
        }

        val control = withContext(Dispatchers.Default) {
            runCatching {
                connectDevice(device = device, listener = listener)
            }.getOrElse { exception ->
                close(exception)
                deviceControlReady.completeExceptionally(exception)
                return@withContext EmptyDeviceControl
            }
        }

        if (!deviceControlReady.isCompleted) {
            deviceControlReady.complete(control)
        }

        awaitClose {
            disconnectDevice(device = device)
        }
    }

    val deviceControl = deviceControlReady.await()
    return deviceControl to flow
}