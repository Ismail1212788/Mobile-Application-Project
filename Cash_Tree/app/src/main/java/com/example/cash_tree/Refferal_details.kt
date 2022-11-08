package com.example.cash_tree

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView

class Refferal_details : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refferal_details)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setLogo(R.drawable.dollar)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.title="CASH-TREE---Refferal Details"

        val a= arrayOf("Names","Ismail","Saqib","Ali")
        val b= arrayOf("Numbers","03012477977","03012478987","03541587984")
        val li=findViewById<ListView>(R.id.list1)
        val li2=findViewById<ListView>(R.id.list2)
        val adap=ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,a)
        val adap1=ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,b)
        li.adapter=adap
        li2.adapter=adap1
    }

}