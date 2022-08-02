package com.example.chattapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*

import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

import kotlin.collections.ArrayList

class Chat : AppCompatActivity() {
    private lateinit var textbox:TextView
    private lateinit var image:ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var messsageAdapter: MessageAdapter
    private lateinit var meesagelist:ArrayList<Message>
    private lateinit var mDbRef:DatabaseReference
    private  lateinit var lastmeesage:String

        var receiverRoom:String?=null
        var senderRoom:String?=null
        var change:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        textbox=findViewById(R.id.editText)
        image=findViewById(R.id.imageView)
        mDbRef=FirebaseDatabase.getInstance().reference
        recyclerView=findViewById(R.id.recyclerView2)
        meesagelist= ArrayList()


        val senderuid=FirebaseAuth.getInstance().currentUser?.uid
        val name=intent.getStringExtra("name")
        val receiveruid=intent.getStringExtra("vid")

        supportActionBar?.title=name
        supportActionBar?.setIcon(R.drawable.prp)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        senderRoom=receiveruid+senderuid
        receiverRoom=senderuid+receiveruid
        messsageAdapter= MessageAdapter(this,meesagelist,receiveruid)

        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.adapter=messsageAdapter

        mDbRef.child("Chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object :ValueEventListener{
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {

                    meesagelist.clear()

                    for(postSnapshot in snapshot.children){
                        val message=postSnapshot.getValue(Message::class.java)


                        meesagelist.add(message!!)

                        recyclerView.scrollToPosition(meesagelist.size-1)
                    }
                    messsageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })





        image.setOnClickListener {
            val  message=textbox.text.toString()
            val timestamp = System.currentTimeMillis()/1000
            val messageObject=Message(message,senderuid,timestamp)

            mDbRef.child("Chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbRef.child("Chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }

              lastmeesage=message
              textbox.setText("")

        }




    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search,menu)


        val searchviewitem= menu?.findItem(R.id.app_bar_search)
        val seachview=MenuItemCompat.getActionView(searchviewitem) as SearchView

        seachview.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                mDbRef.child("Chats").child(senderRoom!!).child("messages").
                        addValueEventListener(object :ValueEventListener{
                            @SuppressLint("ShowToast")
                            override fun onDataChange(snapshot: DataSnapshot) {
                               for(postsnashot in snapshot.children){
                                   val com=postsnashot.getValue(Message::class.java)
                                   if(query.equals(com?.message))
                                   {  Toast.makeText(this@Chat,"Message is found ",Toast.LENGTH_SHORT).show()
                                       change=1
                                       break
                                   }
                                   else if(postsnashot.equals(snapshot.children.last()))
                                       {
                                           Toast.makeText(this@Chat,"Message is not found",Toast.LENGTH_SHORT).show()
                                       }

                               }


                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }

                        })
                    return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent=Intent(this@Chat,ChatsFragment::class.java)
        finish()
        return super.onOptionsItemSelected(item)
    }
}