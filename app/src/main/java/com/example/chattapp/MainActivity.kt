package com.example.chattapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.internal.NavigationMenu
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private  lateinit var mAuth:FirebaseAuth
    private lateinit var tabbar:TabLayout
    private lateinit var viewPager:ViewPager2
    private lateinit var addPageAdapter:AppPageAdapter

    var title= arrayListOf("Chats","Calls")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth= FirebaseAuth.getInstance()
        tabbar=findViewById(R.id.tabLayout)
        viewPager=findViewById(R.id.viewpage)
        val name=""
        supportActionBar?.title=name
        supportActionBar?.setIcon(R.drawable.set)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        addPageAdapter=AppPageAdapter(this)
        viewPager.adapter=addPageAdapter
        TabLayoutMediator(tabbar,viewPager){
         tab,postion-> tab.text= title[postion]

        }.attach()
       }



    class AppPageAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity){
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0->ChatsFragment()
                1->CallFragment()
                else->ChatsFragment()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.logout){
            mAuth.signOut()
            Prefis(this).putBoolean("islogin",false)
            val intent=Intent(this@MainActivity,Login::class.java)
            startActivity(intent)
            finish()
            return true
        }

        return true

    }

}