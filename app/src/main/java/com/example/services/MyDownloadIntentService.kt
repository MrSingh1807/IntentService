package com.example.services

import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.services.Constants.MESSAGE_KEY
import com.example.services.Constants.MY_SERVICE_INTENT
import com.example.services.Constants.TAG

class MyDownloadIntentService : IntentService("MyDownloadIntentService") {

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "On Create: Called" )
        Log.d(TAG, "On Create: "+ Thread.currentThread().name )

        setIntentRedelivery(true)
    }
    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "On Handle: Called" )
        Log.d(TAG, "On Handle: "+ Thread.currentThread().name )

        val songName = intent?.getStringExtra(MESSAGE_KEY)!!
        downloadSong(songName)
        sendMSZToUI(songName)
    }

    private fun sendMSZToUI(songName: String){
        val intent = Intent(MY_SERVICE_INTENT)
        intent.putExtra(MESSAGE_KEY, songName)

        LocalBroadcastManager.getInstance(applicationContext)
            .sendBroadcast(intent)
    }
    private fun downloadSong(songName: String){

        Log.d(Constants.TAG, "Service: Downloading... is Start" )


        try {
            Thread.sleep(3000)
            Log.d(Constants.TAG, "Thread :" + Thread.currentThread().name )
        } catch (e: IllegalAccessException){
            e.printStackTrace()
        }
        Log.d(Constants.TAG, "Service: $songName downloading Completed")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "On Destroy: Called" )
        Log.d(TAG, "On Destroy: "+ Thread.currentThread().name )
    }

}