# Android Kotlin SDK API.VIDEO


The api.video service helps you put video online without the hassle. This documentation helps you use the corresponding Android client. This is an early version, feel free to report any issue.

## Installation
### As a local aar

1. Download the [latest release](https://github.com/apivideo/android-kotlin-sdk/releases) of the aar file.
2. Go to “File” > “Project Structure...”
3. On "Modules" select the "+" button and select "Import .JAR/.AAR Package" then click "next"
4. Select the AAR file you have just downloaded, and click "Finish"
5. Then go to "Dependencies" select the the app module and add a new dependencies by clicking on the "+" button, then select "Module Dependency"
(if there is no "Module Dependency", close the window and re-open it, it should be fine)
6. select Api.Video module, click "ok"
7. click on "Apply" and then "ok"

### Permissions:

<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.RECORD_AUDIO" />


### Quick Start

#### 1. In the App instantiate a new Client
* If you want to use the production environment, use createProduction method
* If you want to use the sandbox environment , use createSandbox method
```kotlin
private val apiVideoClient = Client.createSandbox(« YOUR_SANDBOX_API_KEY »)
```

#### 2. In your MainActivity.kt file import the sdk
#### 3. Get the Client variable

```kotlin
var client = (application as? App)?.getApiVideoClient()
```
#### 4. Create and upload a video file

```kotlin

client.videos.upload(file, video, object : CallBack<Video>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }

    override fun onResponse(response: Video) {
        // response is Video Object
    }
})
```

## FULL API

### Client
#### Create Production Environment 
```kotlin
// YOUR_PRODUCTION_API_KEY: String
val apiVideoClient = Client.createProduction(YOUR_PRODUCTION_API_KEY)
```

#### Create Sandbox Environment 
```kotlin
// YOUR_SANDBOX_API_KEY: String
val apiVideoClient = Client.createSandbox(YOUR_SANDBOX_API_KEY)
```
### VIDEO

#### Upload Video
##### Getting started
```kotlin
// file: File
val file = File("test.mp4")
client.videos.upload(file, object : CallBack<Video>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }

    override fun onResponse(response: Video) {
        // response is Video Object
    }
})
```
##### Option 2
```kotlin
// file: File
// video: Video
val file = File("test.mp4")
val video = Video("test title")
client.videos.upload(file, video, object : CallBack<Video>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }

    override fun onResponse(response: Video) {
        // response is Video Object
    }
})
```
##### Option 3
```kotlin
// file: File
// title: String
val file = File("test.mp4")
val title = "test title"
client.videos.upload(file, title, object : CallBack<Video>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }

    override fun onResponse(response: Video) {
        // response is Video Object
    }
})
```

#### Get List of Video
##### Option 1
```kotlin
// pageNumber: Int,
// pageSize: Int,
// params: QueryParams,
client.videos.list(pageNumber, pageSize, params, object : CallBack<List<Video>>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }

    override fun onResponse(response: List<Video>) {
        // response is List<Video>
    }
})
```
##### Option 2
```kotlin
// pageNumber: Int,
// params: QueryParams
client.videos.list(pageNumber, params, object : CallBack<List<Video>>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }

    override fun onResponse(response: List<Video>) {
        // response is List<Video>
    }
})
```
##### Option 3
```kotlin
// pageNumber: Int,
// pageSize: Int

client.videos.list(pageNumber, pageSize, object : CallBack<List<Video>>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }

    override fun onResponse(response: List<Video>) {
        // response is List<Video>
    }
})
```

##### Option 4
```kotlin
// pageNumber: Int,
client.videos.list(pageNumber, object : CallBack<List<Video>>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }

    override fun onResponse(response: List<Video>) {
        // response is List<Video>
    }
})
```

#### Get Video By Id
```kotlin
// videoId: String
client.videos.get(videoId, object : CallBack<Video>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
    
    override fun onResponse(response: Video) {
        // response is List<Video>
    }
})
```

#### Get Video Status
```kotlin
// videoId: String
client.videos.getStatus(videoId, object : CallBack<Status>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
    
    override fun onResponse(response: Status) {
        // response is Status
    }
})
```

#### Update Video
```kotlin
// video: Video
client.videos.update(video, object : CallBack<Video>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
    
    override fun onResponse(response: Video) {
        // response is Video
    }
})
```

#### Upload Video Thumbnail
```kotlin
// videoId: String,
// file: File
client.videos.uploadThumbnail(videoId, file, object : CallBack<Video>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
    
    override fun onResponse(response: Video) {
        // response is Video
    }
})
```

#### Delte Thumbnail
```kotlin
// videoId: String,
client.videos.deleteThumbnail(videoId, object : CallBack<Video>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
    
    override fun onResponse(response: Video) {
        // response is Video
    }
})
```

#### Delete Video
```kotlin
// videoId: String
client.videos.delete(videoId, object : CallBack<Boolean>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
    
    override fun onResponse(response: Boolean) {
        // response is Boolean
    }
})
```

### PLAYER

#### Create Player
```kotlin

// player: Player
client.players.create(player, object : CallBack<Player>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }

    override fun onResponse(response: Player) {
        // response is Player
    }
})
```
#### Update Player
```kotlin
// player: Player
client.players.update(player, object : CallBack<Player>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
                              
    override fun onResponse(response: Player) {
        // response is Player
    }
})
```

#### Get All Players
##### Option 1
```kotlin
// pageNumber: Int,
// pageSize: Int,
// params: QueryParams,
client.players.list(pageNumber, pageSize, params, object : CallBack<List<Player>>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
                                                             
    override fun onResponse(response: List<Player>) {
        // response is List<Player>
    }
})
```
##### Option 2
```kotlin
// pageNumber: Int,
// params: QueryParams,
client.players.list(pageNumber, params, object : CallBack<List<Player>>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
                                                             
    override fun onResponse(response: List<Player>) {
        // response is List<Player>
    }
})
```
##### Option 3
```kotlin
// pageNumber: Int,
// pageSize: Int,
client.players.list(pageNumber, pageSize, object : CallBack<List<Player>>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
                                                             
    override fun onResponse(response: List<Player>) {
        // response is List<Player>
    }
})
```
##### Option 4
```kotlin
// pageNumber: Int,
client.players.list(pageNumber, object : CallBack<List<Player>>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
                                                             
    override fun onResponse(response: List<Player>) {
        // response is List<Player>
    }
})
```

#### Get Player Id
```kotlin
// playerId: String
client.players.get(playerId, object : CallBack<Player>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
                                                                                                   
    override fun onResponse(response: Player) {
        // response is Player
    }
})
```

#### Delete Player
```kotlin
// playerId: String
client.players.delete(playerId, object : CallBack<Boolean>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
                                                                                                                                           
    override fun onResponse(response: Boolean) {
        // response is Boolean
    }
})
```

#### Upload Logo Player
```kotlin
// playerId: String,
// file: File,
// link: HttpUrl,
client.players.uploadLogo(playerId, file, link, object : CallBack<Boolean>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
                                                                                                                                           
    override fun onResponse(response: Boolean) {
        // response is Boolean
    }
})
```

### LIVESTREAM

#### Create Live Stream
##### Option 1
```kotlin
// liveStream: LiveStream
client.liveStreams.create(liveStream, object : CallBack<LiveStream>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
                                                                                                                                                                                                            
    override fun onResponse(response: LiveStream) {
        // response is LiveStream
    }
})
```
##### Option 2
```kotlin
// name: String
client.liveStreams.create(name, object : CallBack<LiveStream>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
                                                                                                                                                                                                            
    override fun onResponse(response: LiveStream) {
        // response is LiveStream
    }
})
```

#### Get LiveStream By Id
```kotlin
// liveStreamId: String
client.liveStreams.get(liveStreamId, object : CallBack<LiveStream>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
                                                                                                                                                                                                                                                               
    override fun onResponse(response: LiveStream) {
        response as LiveStream
    }
})
```

#### Get All LiveStream
##### Option 1
```kotlin
// pageNumber: Int,
// pageSize: Int,
// params: QueryParams,
client.liveStreams.list(pageNumber, pageSize, params, object : CallBack<List<LiveStream>>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
                                                                                                                                                                                                                                                                                                  
    override fun onResponse(response: List<LiveStream>) {
        // response is List<LiveStream>
    }
})
```
##### Option 2
```kotlin
// pageNumber: Int,
// params: QueryParams,
client.liveStreams.list(pageNumber, params, object : CallBack<List<LiveStream>>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
                                                                                                                                                                                                                                                                                                  
    override fun onResponse(response: List<LiveStream>) {
        // response is List<LiveStream>
    }
})
```
##### Option 3
```kotlin
// pageNumber: Int,
// pageSize: Int,
client.liveStreams.list(pageNumber, pageSize, object : CallBack<List<LiveStream>>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
                                                                                                                                                                                                                                                                                                  
    override fun onResponse(response: List<LiveStream>) {
        // response is List<LiveStream>
    }
})
```
##### Option 4
```kotlin
// pageNumber: Int,
client.liveStreams.list(pageNumber, object : CallBack<List<LiveStream>>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
                                                                                                                                                                                                                                                                                                  
    override fun onResponse(response: List<LiveStream>) {
        // response is List<LiveStream>
    }
})
```

#### Delete Live Stream
```kotlin
// liveStreamId: String
client.liveStreams.delete(liveStreamId, object : CallBack<Boolean>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
                                                                                                                                                                                                                                       
    override fun onResponse(response: Boolean) {
        // response is Boolean
    }
})
```

#### Upload Thumbnail Live Stream
```kotlin
// liveStreamId: String,
// file: File
client.liveStreams.uploadThumbnail(liveStreamId, file, object : CallBack<Boolean>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
                                                                                                                                                                                                                                       
    override fun onResponse(response: Boolean) {
        // response is Boolean
    }
})
```
#### Delete Thumbnail Live Stream
```kotlin
// liveStreamId: String,
client.liveStreams.deleteThumbnail(liveStreamId, object : CallBack<Boolean>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
                                                                                                                                                                                                                                       
    override fun onResponse(response: Boolean) {
        // response is Boolean
    }
})
```

#### Start and Stop Live Stream
```kotlin
// liveStream: LiveStream
// surfaceView : SurfaceView
// context: Context
// connectChecker: ConnectCheckerRtmp
class MyClass: ConnectCheckerRtmp {
    RtmpStreamer.Builder()
        .videoQuality(RtmpStreamer.Quality.QUALITY_1080)
        .videoFps(30)
        .build()
        .start(
            liveStream,
            surfaceView,
            context,
            connectChecker
        )
// override all methodes 

}
```
### CHAPTER

#### Upload Chapter
```kotlin
// videoId: String,
// language: Locale,
// file : File
client.chapters.upload(videoId,language, file, object : CallBack<Chapter>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
                                                                                                                                                                                                                                                                                                     
    override fun onResponse(response: Chapter) {
        // response is Chapter
    }
})
```

#### Get Chapter
```kotlin
// videoId: String
// language: Locale
client.chapters.get(videoId, language, object : CallBack<Chapter>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
                                                                                                                                                                                                                                                                                                                                                    
    override fun onResponse(response: Chapter) {
        // response is Chapter
    }
})
```

#### Get All Chapters
##### Option 1
```kotlin
// videoId: String,
// pageNumber: Int,
// pageSize: Int
client.chapters.list(videoId, pageNumber, pageSize, object : CallBack<List<Chapter>>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
                                                                                                                                                                                                                                                                                                                                                                                            
    override fun onResponse(response: List<Chapter>) {
        // response is List<Chapter>
    }
})
```
##### Option 2
```kotlin
// videoId: String,
// pageNumber: Int,
client.chapters.list(videoId, pageNumber, object : CallBack<List<Chapter>>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
                                                                                                                                                                                                                                                                                                                                                                                            
    override fun onResponse(response: List<Chapter>) {
        // response is List<Chapter>
    }
})
```

#### Delete Chapter
```kotlin
// videoId: String
// language: Locale
client.chapters.delete( videoId, language, object : CallBack<Boolean>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
                                                                                                                                                                                                                                                                                                                                                                                                                                                      
    override fun onResponse(response: Boolean) {
        // response is Boolean
    }
})
```
### CAPTION

#### Upload Caption
```kotlin
// videoId: String
// language: Locale
// file: File
client.captions.upload( videoId, language, file, object : CallBack<Caption>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
    override fun onResponse(response: Caption) {
        // response is Caption
    }
})
```

#### Get Caption
```kotlin
// videoId: String
// language: Locale
client.captions.get( videoId, language, object : CallBack<Caption>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
    override fun onResponse(response: Caption) {
        // response is Caption
    }
})
```

#### Get All Captions
##### Option 1
```kotlin
// videoId: String,
// pageNumber: Int,
// pageSize: Int,
client.captions.list(videoId,pageNumber, pageSize, object : CallBack<List<Caption>>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
    override fun onResponse(response: List<Caption>) {
        // response is List<Caption>
    }
})
```
##### Option 2
```kotlin
// videoId: String,
// pageNumber: Int,
client.captions.list(videoId,pageNumber, object : CallBack<List<Caption>>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
    override fun onResponse(response: List<Caption>) {
        // response is List<Caption>
    }
})
```

#### Update Default Value
```kotlin
// videoId: String
// language: Locale
client.captions.setDefault(videoId, language, object : CallBack<Caption>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
    override fun onResponse(response: Caption) {
        // response is Caption
    }
})
```

#### Delete Caption
```kotlin
// videoId: String
// language: Locale
client.captions.delete(videoId, language, object : CallBack<Boolean>{
    override fun onError(error: SdkResponse) {
        Log.e("error", error.toString())
    }

    override fun onFatal(e: IOException) {
        throw e
    }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
    override fun onResponse(response: Boolean) {
        // response is Boolean
    }
})
```
