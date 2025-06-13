# DLNA-Casting-Android
Casting movie in android using DLNA.

All creadit and thanks for this project was update from:

```https://github.com/devin1014/DLNA-Cast```

You can check more this project vai the above link: Thanks you!

# Using this library to your project

# Step: 1: Add this jipack to you setting gradle
```
pluginManagement {
    repositories {
        ....

        maven {
            url = uri("https://jitpack.io")
        }
        maven {
            url = uri("http://4thline.org/m2")
            isAllowInsecureProtocol = true
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        ....

        maven {
            url = uri("https://jitpack.io")
        }
        maven {
            url = uri("http://4thline.org/m2")
            isAllowInsecureProtocol = true
        }
    }
}
```
# Step: 2: Add this dependencies to you build gradle in app folder
```
dependencies {
    implementation 'com.github.Panha-Devs:DLNA-Casting-Android:Tag'
}
```

#Usage
1. Add this permission to android manifest

```
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
<uses-permission android:name="android.permission.CHANGE_WIFI_MULTICAST_STATE"/>
<uses-permission android:name="android.permission.WAKE_LOCK"/>
```
2. Register this service in android manifest

```
<service android:name="com.android.cast.dlna.dmc.DLNACastService"/>
<service android:name="com.android.cast.dlna.dmr.DLNARendererService"/>
<service android:name="com.android.cast.dlna.dms.DLNAContentSercice"/>
```

3. Bind and unbind this service on active or fragment or compose screen that using this service
   
- Activity or fragment
```
@Override
protected void onStart() {        
    DLNACastManager.getInstance().bindCastService(this);
}

@Override
protected void onStop() {
    DLNACastManager.getInstance().unbindCastService(this);
}
```
- Compose screen
```
DisposableEffect {
    DLNACastManager.getInstance().bindCastService(this);

    onDispose {
        DLNACastManager.getInstance().unbindCastService(this);
    }
}
```

4. Search device for casting
```
DLNACastManager.getInstance().search();
```

5. Listner for action on found devices, follow step 3 to regiester or unregister
```
DLNACastManager.getInstance().registerDeviceListener(listener);
DLNACastManager.getInstance().unregisterListener(listener);
```
When a new device is found, it needs to be added to the device list for display.
`OnDeviceRegistryListener` This interface callback is always called in the `main thread`

6. Casting to device that found on your local network
```
deviceControl: DeviceControl = DLNACastManager.connectDevice(device, callback)

DeviceControl接口如下：
DeviceControl {
    // Project the current video
    fun setAVTransportURI(uri: String, title: String, callback: ServiceActionCallback<Unit>?) {}

    // Cast the next video (not every player supports this function, the next one will be played automatically after the current video ends)
    fun setNextAVTransportURI(uri: String, title: String, callback: ServiceActionCallback<Unit>?) {}

    // Play the current video
    fun play(speed: String, callback: ServiceActionCallback<Unit>?) {}

    // Pause the current video
    fun pause(callback: ServiceActionCallback<Unit>?) {}

    // Stop the current video
    fun stop(callback: ServiceActionCallback<Unit>?) {}

    // Fast forward/fast rewind
    fun seek(millSeconds: Long, callback: ServiceActionCallback<Unit>?) {}

    // Play the next video
    fun next(callback: ServiceActionCallback<Unit>?) {}

    // Play the previous video
    fun previous(callback: ServiceActionCallback<Unit>?) {}

    // Get the playback information of the currently projected video, current time/total time
    fun getPositionInfo(callback: ServiceActionCallback<PositionInfo>?) {}

    // Get the current video information
    fun getMediaInfo(callback: ServiceActionCallback<MediaInfo>?) {}

    // Get the current playback status, etc.
    fun getTransportInfo(callback: ServiceActionCallback<TransportInfo>?) {}

    // Set the value
    fun setVolume(volume: Int, callback: ServiceActionCallback<Unit>?) {}

    // Get the volume
    fun getVolume(callback: ServiceActionCallback<Int>?) {}

    //Set mute
    fun setMute(mute: Boolean, callback: ServiceActionCallback<Unit>?) {}

    // Get whether the sound is muted
    fun getMute(callback: ServiceActionCallback<Boolean>?) {}

    // Query objectId information (the default value of '0' is all information)
    fun browse(objectId: String, flag: BrowseFlag, filter: String, firstResult: Int, maxResults: Int, callback: ServiceActionCallback<DIDLContent>?) {}

    // Find objectId information
    fun search(containerId: String, searchCriteria: String, filter: String, firstResult: Int, maxResults: Int, callback: ServiceActionCallback<DIDLContent>?) {}
}

Each operation has corresponding parameters and event callback interface to monitor whether the operation is successful.
```
