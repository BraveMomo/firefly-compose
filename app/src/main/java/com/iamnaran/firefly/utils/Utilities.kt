package com.iamnaran.firefly.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ShareCompat
import com.iamnaran.firefly.R

class Utilities {

//    private fun createShareIntent(activity: Activity, plantName: String) {
//        val shareText = activity.getString(R.string.share_text_plant, plantName)
//        val shareIntent = ShareCompat.IntentBuilder(activity)
//            .setText(shareText)
//            .setType("text/plain")
//            .createChooserIntent()
//            .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
//        activity.startActivity(shareIntent)
//    }

    fun openAppSetting(context: Context) {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", context.packageName, null)
        ).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}