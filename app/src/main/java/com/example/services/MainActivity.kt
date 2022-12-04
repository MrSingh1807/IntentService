package com.example.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.services.Constants.MESSAGE_KEY
import com.example.services.Constants.MY_SERVICE_INTENT
import com.example.services.Constants.TAG
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val localBroadcastManager = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {

            if (intent?.getStringExtra(MESSAGE_KEY) != null) {
                val song = intent.getStringExtra(MESSAGE_KEY)

                Log.d(TAG, "On Receive: " + Thread.currentThread().name)
                if (song != null) {
                    log(song)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_startService.setOnClickListener {
            createService()
        }

        btn_stopService.setOnClickListener {
            killService()
            clearOutPut()
        }
    }

    override fun onStart() {
        super.onStart()

        LocalBroadcastManager.getInstance(applicationContext)
            .registerReceiver(localBroadcastManager, IntentFilter(MY_SERVICE_INTENT))
    }

    private fun createService() {
        log("Service is going to Start")
        displayProgressBar(true)

//       startService(intent)
        for (song in Playlist().songs){
            val intent = Intent(this, MyDownloadIntentService::class.java)
            intent.putExtra(MESSAGE_KEY, song)

            startService(intent)
        }
    }

    private fun killService() {

        val intent = Intent(this, MyDownloadIntentService::class.java)
        stopService(intent)

        displayProgressBar(false)
    }

    private fun clearOutPut() {
        txtVw_Log.text = ""
        scrollTextToEnd()
    }

    private fun log(message: String) {
        Log.i(TAG, message)
        txtVw_Log.append(message + "\n")
        scrollTextToEnd()
    }

    private fun scrollTextToEnd() {
        scrollLog.post { scrollLog.fullScroll(ScrollView.FOCUS_DOWN) }
    }

    private fun displayProgressBar(display: Boolean) {
        if (display) {
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.isVisible = false
        }
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(applicationContext)
            .unregisterReceiver(localBroadcastManager)
    }

}