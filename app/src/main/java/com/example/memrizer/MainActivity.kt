package com.example.memrizer

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var stringurl:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
     private fun ApiCall() {
         progressbar.visibility=View.VISIBLE
         val queue = Volley.newRequestQueue(this)
         val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
         val jsonRequest = JsonObjectRequest(
             Request.Method.GET, url,null,
             Response.Listener { response ->
              stringurl=response.getString("url")
                 Glide.with(this).load(stringurl).listener(object:RequestListener<Drawable>{
                     override fun onLoadFailed(
                         e: GlideException?,
                         model: Any?,
                         target: Target<Drawable>?,
                         isFirstResource: Boolean
                     ): Boolean{
                        progressbar.visibility=View.GONE
                         return false

                     }

                     override fun onResourceReady(
                         resource: Drawable?,
                         model: Any?,
                         target: Target<Drawable>?,
                         dataSource: DataSource?,
                         isFirstResource: Boolean
                     ): Boolean {
                         progressbar.visibility=View.GONE
                         return false
                     }
                 }


                 ).into(imageView)
             },
             Response.ErrorListener { Toast.makeText(getApplicationContext(),"SOMETHING WENT WRONG",Toast.LENGTH_SHORT).show();  })

// Add the request to the RequestQueue.
         queue.add(jsonRequest)
     }

    fun shareit(view: View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"HEY CHECH THIS MEME$stringurl")
        val chooser=Intent.createChooser(intent,"share using....")
        startActivity(chooser)
    }
    fun next(view: View) {
        ApiCall()
    }
}