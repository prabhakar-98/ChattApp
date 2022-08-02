package com.example.chattapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

import android.widget.ImageView

class Splash : AppCompatActivity() {
    private  lateinit var imageView: ImageView
    private  var splacescreentime=4000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        imageView=findViewById(R.id.image3)
         supportActionBar?.hide()
        val islogin=Prefis(this).getBoolean("islogin")

       Handler(Looper.getMainLooper()).postDelayed(
           {
               if(islogin) {
                   val intent = Intent(this@Splash, MainActivity::class.java)
                   startActivity(intent)
                   finish()
               }
               else
               {
                   val intent =Intent(this@Splash,Login::class.java)
                   startActivity(intent)
                   finish()
               }
           },splacescreentime.toLong()
       )

    }
}