package com.example.cash_tree

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class Deposite : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deposite)
    }

  fun deposit_(v: View){
   Toast.makeText(this,"Deposit Request Sent Successfully",Toast.LENGTH_LONG).show()
   finish()
  }
}