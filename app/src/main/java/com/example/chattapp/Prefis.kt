package com.example.chattapp

import android.content.Context
import android.content.SharedPreferences

class Prefis(context: Context) {
   var prefence:SharedPreferences=context.getSharedPreferences("NAME",Context.MODE_PRIVATE)

    fun putBoolean(key:String,value:Boolean){
        prefence.edit().putBoolean(key,value).apply()
    }
    fun getBoolean(key: String):Boolean{

        return prefence.getBoolean(key,false)
    }



}