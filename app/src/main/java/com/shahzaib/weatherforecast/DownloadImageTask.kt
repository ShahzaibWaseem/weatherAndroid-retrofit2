package com.shahzaib.weatherforecast

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import java.io.InputStream
import java.net.URL

class DownloadImageTask(ImageView: ImageView) :
    AsyncTask<String?, Void?, Bitmap?>() {
    private var imageView: ImageView = ImageView

    override fun doInBackground(vararg p0: String?): Bitmap? {
        val url = p0[0]
        var bmp: Bitmap? = null
        try {
            Log.i("Icon Download", "Downloading $url")
            val `in`: InputStream = URL(url).openStream()
            bmp = BitmapFactory.decodeStream(`in`)
        } catch (e: Exception) {
            e.message?.let { Log.e("Error", it) }
            e.printStackTrace()
        }
        return bmp
    }

    override fun onPostExecute(result: Bitmap?) {
        imageView.setImageBitmap(result)
    }
}