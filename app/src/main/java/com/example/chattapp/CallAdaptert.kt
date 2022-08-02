package com.example.chattapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CallAdaptert(var context: Context, var arrayList: ArrayList<User>):RecyclerView.Adapter<CallAdaptert.callHolder>() {

    class callHolder(itemview:View):RecyclerView.ViewHolder(itemview){
        var textview=itemview.findViewById<TextView>(R.id.textcall)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): callHolder {
        val view:View=LayoutInflater.from(context).inflate(R.layout.callayout,parent,false)
        return callHolder(view)
    }

    override fun onBindViewHolder(holder: callHolder, position: Int) {
       val usercall=arrayList[position]
        holder.textview.text=usercall.name
        holder.itemView.setOnClickListener {
         val  intent=Intent(context,calling::class.java)
           intent.putExtra("Name",usercall.name)
            intent.putExtra("vid",usercall.vid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
     return   arrayList.size
    }
}