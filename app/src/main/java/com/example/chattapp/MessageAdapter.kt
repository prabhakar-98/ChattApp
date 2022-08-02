package com.example.chattapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MessageAdapter(var context: Context,var meesagelist:ArrayList<Message>,var recviervid:String?):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var senderrom:String?=null


    val item_receive=1;
    val item_send =2

    override fun getItemViewType(position: Int): Int {
        val Cmessage=meesagelist[position]
        return if(FirebaseAuth.getInstance().currentUser?.uid.equals(Cmessage.senderId)){
            item_send
        } else {
            item_receive
        }
    }


    class SentViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
       val  sentmessage=itemView.findViewById<TextView>(R.id.txt_send)
    }

    class ReceiverViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
     val receivemessage=itemView.findViewById<TextView>(R.id.recview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType==1) {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.receive, parent, false)
            ReceiverViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.send, parent, false)
            SentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val Cmessage=meesagelist[position]
        senderrom=recviervid+FirebaseAuth.getInstance().currentUser?.uid
        if(holder.javaClass==SentViewHolder::class.java){
            val viewHolder=holder as SentViewHolder

            holder.sentmessage.text=Cmessage.message
            holder.sentmessage.setOnClickListener {

                showdialog(Cmessage.message,senderrom)
            }
        }
        else{
            val viewHolder=holder as ReceiverViewHolder

            holder.receivemessage.text=Cmessage.message
            holder.receivemessage.setOnClickListener {

                showdialog(Cmessage.message,senderrom)
            }
        }

    }

    override fun getItemCount(): Int {
        return meesagelist.size
    }


        private fun showdialog( message:String?,senderoom:String?){
        val bulider=AlertDialog.Builder(context)
        val view=LayoutInflater.from(context).inflate(R.layout.customdialog,null)
        bulider.setView(view)
        val btn=view.findViewById<TextView>(R.id.edtd)
        btn.setOnClickListener {
         FirebaseDatabase.getInstance().getReference().child("Chats").child(senderoom!!).
                  child("message").addValueEventListener(object :ValueEventListener{
             override fun onDataChange(snapshot: DataSnapshot) {
                 for(postsnaphot in snapshot.children){
                    val com=postsnaphot.getValue(Message::class.java)
                     if(com?.message.equals(message))
                     {
                         postsnaphot.ref.removeValue()
                         break

                     }
                 }
             }

             override fun onCancelled(error: DatabaseError) {
                 TODO("Not yet implemented")
             }

         })



        }



        bulider.setCancelable(true)
        bulider.show()
    }
}