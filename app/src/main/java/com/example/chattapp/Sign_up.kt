package com.example.chattapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Sign_up : AppCompatActivity() {
    private lateinit var name:EditText
    private lateinit var username:EditText
    private lateinit var pword:EditText
    private lateinit var signup:Button
    private lateinit var mAuth: FirebaseAuth
    private  lateinit var mDbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.title=""
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mAuth= FirebaseAuth.getInstance()
        name=findViewById(R.id.edt_name)
        username=findViewById(R.id.edt_usernmae)
        pword=findViewById(R.id.password)
        signup=findViewById(R.id.signin)


        signup.setOnClickListener {
            val email=username.text.toString()
            val password=pword.text.toString()
            val name=name.text.toString()
            signup( name,email,password)
        }
    }
    private fun signup(name:String ,email:String,password:String){

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                     addUserTodatabase(name,email,mAuth.currentUser?.uid!!)

                   val intent=Intent(this@Sign_up,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                   Toast.makeText(this@Sign_up,"Some error are occurred",Toast.LENGTH_SHORT).show()
                }
            }

    }
    private fun  addUserTodatabase(name:String,email:String,uid:String){
        mDbRef=FirebaseDatabase.getInstance().getReference()
        mDbRef.child("USER").child(uid).setValue(User(name,email,uid))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent =Intent(this@Sign_up,Login::class.java)
        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }

}