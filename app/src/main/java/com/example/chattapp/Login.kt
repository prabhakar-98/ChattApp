package com.example.chattapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

   private lateinit var email:EditText
  private  lateinit var password:EditText
    private lateinit var btnlogin:Button
   private lateinit var btnsignup:Button
   private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        mAuth= FirebaseAuth.getInstance()

        email=findViewById(R.id.edt_email)
        password=findViewById(R.id.edt_password)
        btnlogin=findViewById(R.id.edt_login)
        btnsignup=findViewById(R.id.edt_signup)

        btnsignup.setOnClickListener {
            val intent=Intent(this,Sign_up::class.java)
            startActivity(intent)
        }
        btnlogin.setOnClickListener {
            val edtemail=email.text.toString()
            val edtpassword=password.text.toString()

            login(edtemail,edtpassword)
        }

    }
    private  fun login(edtemail:String,edtpassword:String)
    {
        mAuth.signInWithEmailAndPassword(edtemail, edtpassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                   Prefis(this).putBoolean("islogin",true)
                    val intent=Intent(this@Login,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                   Toast.makeText(this@Login,"User does not exist",Toast.LENGTH_SHORT).show()
                }
            }
    }
}