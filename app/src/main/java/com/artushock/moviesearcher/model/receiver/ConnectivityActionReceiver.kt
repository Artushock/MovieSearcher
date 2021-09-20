package com.artushock.moviesearcher.model.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ConnectivityActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        StringBuilder().apply {
            append("System message\n")
            append("${intent?.action}")
            toString().also {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }
}