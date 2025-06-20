package com.android.cast.dlna.dmc.extension

import com.android.cast.dlna.dmc.control.DeviceControl
import com.android.cast.dlna.dmc.control.safeCoroutineLauncher
import org.fourthline.cling.support.model.BrowseFlag

suspend fun DeviceControl.setAVTransportURI(uri: String, title: String) =
    safeCoroutineLauncher { callback ->
        setAVTransportURI(uri = uri, title = title, callback = callback)
    }

suspend fun DeviceControl.setNextAVTransportURI(uri: String, title: String) =
    safeCoroutineLauncher { callback ->
        setNextAVTransportURI(uri = uri, title = title, callback = callback)
    }


suspend fun DeviceControl.play(speed: String = "1") = safeCoroutineLauncher { callback ->
    play(speed = speed, callback = callback)
}

suspend fun DeviceControl.stop() = safeCoroutineLauncher { callback ->
    stop(callback = callback)
}

suspend fun DeviceControl.pause() = safeCoroutineLauncher { callback ->
    pause(callback = callback)
}

suspend fun DeviceControl.next() = safeCoroutineLauncher { callback ->
    next(callback = callback)
}

suspend fun DeviceControl.previous() = safeCoroutineLauncher { callback ->
    previous(callback = callback)
}

suspend fun DeviceControl.seek(millSeconds: Long) = safeCoroutineLauncher { callback ->
    seek(millSeconds = millSeconds, callback = callback)
}

suspend fun DeviceControl.getPositionInfo() = safeCoroutineLauncher { callback ->
    getPositionInfo(callback = callback)
}

suspend fun DeviceControl.getMediaInfo() = safeCoroutineLauncher { callback ->
    getMediaInfo(callback = callback)
}

suspend fun DeviceControl.getTransportInfo() = safeCoroutineLauncher { callback ->
    getTransportInfo(callback = callback)
}

suspend fun DeviceControl.setVolume(volume: Int) = safeCoroutineLauncher { callback ->
    setVolume(volume = volume, callback = callback)
}

suspend fun DeviceControl.getVolume() = safeCoroutineLauncher { callback ->
    getVolume(callback = callback)
}

suspend fun DeviceControl.setMute(mute: Boolean) = safeCoroutineLauncher { callback ->
    setMute(mute = mute, callback = callback)
}

suspend fun DeviceControl.getMute() = safeCoroutineLauncher { callback ->
    getMute(callback = callback)
}

suspend fun DeviceControl.browse(
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

suspend fun DeviceControl.search(
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