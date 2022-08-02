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


class CallFragment : Fragment() {
      private  lateinit var callrecycler:RecyclerView
      private lateinit var  calllist:ArrayList<User>
      private lateinit var adapter:CallAdaptert
      private lateinit var layout: RecyclerView.LayoutManager
      private lateinit var mDbRef:DatabaseReference
      private lateinit var mAuth:FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view= inflater.inflate(R.layout.fragment_call, container, false)
        callrecycler=view.findViewById(R.id.callrecyclerview)
        layout=LinearLayoutManager(context as Activity)
        calllist= ArrayList()
        adapter= CallAdaptert(context as Activity,calllist)
        callrecycler.adapter=adapter
        callrecycler.layoutManager=layout
        mAuth= FirebaseAuth.getInstance()
        mDbRef=FirebaseDatabase.getInstance().getReference()
        callrecycler.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )

        mDbRef.child("USER").addValueEventListener(object: ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                calllist.clear()
                for (postSnapshot in snapshot.children) {

                    val currentuser = postSnapshot.getValue(User::class.java)
                    if (mAuth.currentUser?.uid != currentuser?.vid) {
                        calllist.add(currentuser!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }


        })
//// testing
            return view
    }

}