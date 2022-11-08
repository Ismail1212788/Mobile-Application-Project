package com.example.cash_tree

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Welcome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
    }

    fun login_to(v: View){
    val i=Intent(this,Login::class.java)
    startActivity(i)
    }
    fun register_to(v: View){

        val i=Intent(this,Registration::class.java)
        startActivity(i)
    }


}