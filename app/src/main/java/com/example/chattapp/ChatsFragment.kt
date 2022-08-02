package com.example.chattapp

import android.annotation.SuppressLint
import android.app.Activity


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


    class ChatsFragment : Fragment() {
    private  lateinit var userrecyclerview:RecyclerView
     private  lateinit var cadapter:UserAdapter
    private lateinit var mDbRef:DatabaseReference
    private lateinit var mAuth:FirebaseAuth
    private lateinit var chatlayour:RecyclerView.LayoutManager
    private  lateinit var  userlist:ArrayList<User>
        var senderRoom:String?=null


        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view= inflater.inflate(R.layout.fragment_chats, container, false)

        userrecyclerview=view.findViewById(R.id.recyclerview)

        mAuth= FirebaseAuth.getInstance()
        mDbRef=FirebaseDatabase.getInstance().getReference()
            chatlayour=LinearLayoutManager(context as Activity,LinearLayoutManager.VERTICAL,false)
            userrecyclerview.layoutManager=chatlayour
            userlist=ArrayList()
            

        mDbRef.child("USER").addValueEventListener(object: ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                userlist.clear()
                for (postSnapshot in snapshot.children) {

                    val currentuser = postSnapshot.getValue(User::class.java)



                    if (mAuth.currentUser?.uid != currentuser?.vid) {
                        userlist.add(currentuser!!)
                    }
                }
                userrecyclerview.addItemDecoration(
                    DividerItemDecoration(
                        context,
                        LinearLayoutManager.VERTICAL
                    )
                )

                cadapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }



        })






            cadapter=UserAdapter(context as Activity ,userlist)
            userrecyclerview.adapter=cadapter



        return view
    }


}