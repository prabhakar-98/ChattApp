package com.example.chattapp
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast


class calling : AppCompatActivity() {
    private lateinit var imageView: ImageView
     private lateinit var textView: TextView
     private  lateinit var textmessage: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calling)



        textView=findViewById(R.id.textView)
        imageView=findViewById(R.id.imageView5)
        textmessage=findViewById(R.id.view)
        supportActionBar?.hide()

        val name=intent.getStringExtra("Name")
        val uid=intent.getStringExtra("vid")
         textView.text=name

        imageView.setOnClickListener {
            Toast.makeText(this@calling,"Call ended",Toast.LENGTH_SHORT).show()
            val intent=Intent(this@calling,CallFragment::class.java)
            finish()
        }
        textmessage.setOnClickListener {
            val intent=Intent(this@calling,Chat::class.java)
            intent.putExtra("name",name)
            intent.putExtra("vid",uid)
            startActivity(intent)
            finish()
        }


    }



}