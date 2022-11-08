package com.example.cash_tree

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {


    lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setLogo(R.drawable.dollar)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        val sh=getSharedPreferences("useridnew", MODE_PRIVATE)
        val val_=sh.getString("id","")
        val f=Home().newInstance(val_!!)
        supportFragmentManager.beginTransaction().replace(R.id.framefrag,f).commit()
        database= Firebase.database.reference

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       val i=menuInflater
        i.inflate(R.menu.action_bar,menu)
        return true

     }




    fun homeClick(v: View){
        val sh=getSharedPreferences("useridnew", MODE_PRIVATE)
        val val_=sh.getString("id","")
        val f=Home().newInstance(val_!!)
        supportFragmentManager.beginTransaction().replace(R.id.framefrag,f).commit()
        supportActionBar?.title="CASH-TREE---HOME"
    }

    fun myteam(v:View) {
        val i= Intent(this,Refferal_details::class.java)
        startActivity(i)

    }

    fun freeServices(v: View){
        val sh=getSharedPreferences("useridnew", MODE_PRIVATE)
        val val_=sh.getString("id","")

        database.child("Users").child(val_!!).get().addOnSuccessListener {
            if(it.exists()){
                val b1=it.child("ftask").value.toString().toInt()
                val b2=it.child("token").value.toString().toInt()
                if(b1>0){
                    database.child("Users").child(val_).child("ftask").setValue((b1-1).toString())
                    database.child("Users").child(val_).child("token").setValue((b2+1).toString())
                    val i= Intent(this,Free_Task::class.java)
                    startActivity(i)
                }
                else{
                    Toast.makeText(applicationContext,"Your have no any task please invite someone to get Tasks",Toast.LENGTH_LONG).show()
                }
            }
        }.addOnFailureListener{

        }

        //Toast.makeText(this,"Free Service Clicked",Toast.LENGTH_LONG).show()

    }

    fun premiumServices(v: View){
       // Toast.makeText(this,"Paid Service Clicked",Toast.LENGTH_LONG).show()
        val sh=getSharedPreferences("useridnew", MODE_PRIVATE)
        val val_=sh.getString("id","")

        database.child("Users").child(val_!!).get().addOnSuccessListener {
            if(it.exists()){
                val b1=it.child("ptask").value.toString().toInt()
                val b2=it.child("totalb").value.toString().toInt()


                if(b1>0){
                    database.child("Users").child(val_).child("ptask").setValue((b1-1).toString())
                    database.child("Users").child(val_).child("totalb").setValue((b2+20).toString())
                    val i= Intent(this,Paid_Task::class.java)
                    startActivity(i)
                }
                else{
                    Toast.makeText(applicationContext,"Your have no any task please invite someone to get Tasks",Toast.LENGTH_LONG).show()
                }
            }
        }.addOnFailureListener{

        }
    }

    fun settings(v: View){

        val sh=getSharedPreferences("useridnew", MODE_PRIVATE)
        val val_=sh.getString("id","")
        val f=Settingsfrag().newInstance(val_!!)

        supportFragmentManager.beginTransaction().replace(R.id.framefrag,f).commit()
        supportActionBar?.title="CASH-TREE---Settings"
    }



}

