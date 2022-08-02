package com.example.chattapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class UserAdapter(val context: Context, private val userlist:ArrayList<User>):RecyclerView.Adapter<UserAdapter.UserViewholder>() {

    var senderroom:String?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewholder {
       val view:View=LayoutInflater.from(parent.context).inflate(R.layout.user_layout,parent,false)
        return UserViewholder(view)
    }

    override fun onBindViewHolder(holder: UserViewholder, position: Int) {
      val currentUser=userlist[position]
        holder.textName.text=currentUser.name
        val sendervid=FirebaseAuth.getInstance().currentUser?.uid
        val  receviervid=currentUser.vid
        senderroom=receviervid+sendervid

        FirebaseDatabase.getInstance().getReference().child("Chats").child(senderroom!!).
            child("messages").addValueEventListener(object :ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
               val wer=snapshot.children.last().getValue(Message::class.java)
                holder.lastmessge.text=wer?.message
                val seen =wer?.timestamp
                holder.time.text=currrenttime(seen)

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


        holder.itemView.setOnClickListener {
            val intent=Intent(context,Chat::class.java)
            intent.putExtra("name",currentUser.name)
            intent.putExtra("vid",currentUser.vid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
       return userlist.size

    }
    class UserViewholder(itemView:View):RecyclerView.ViewHolder(itemView){
    val textName=itemView.findViewById<TextView>(R.id.name)
        val lastmessge=itemView.findViewById<TextView>(R.id.textView4)
        val time = itemView.findViewById<TextView>(R.id.textView3)
    }
    @SuppressLint("SimpleDateFormat")
    private  fun  currrenttime(time:Long?): CharSequence? {
        val date = Date(time!!)
        val format=SimpleDateFormat("HH:mm")
        return  format.format(date)
    }
}