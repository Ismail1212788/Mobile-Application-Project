package com.example.cash_tree

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.IOException

class Login : AppCompatActivity() {
    lateinit var _name: EditText
    lateinit var _password: EditText
    lateinit var btn:Button
    lateinit var sname:String
    lateinit var sPass:String

    lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        _name=findViewById(R.id.l_email)
        _password=findViewById(R.id.l_passwd)
        btn=findViewById(R.id.l_btn_val)

        val i=findViewById<EditText>(R.id.l_email)
        var te:String
        try {
            val sh=getSharedPreferences("userid", MODE_PRIVATE)
            val val_=sh.getString("id","")
            i.setText(val_)
        }
        catch (e:IOException){
        Toast.makeText(this,"Exception occured",Toast.LENGTH_LONG).show()
        }
        database= Firebase.database.reference
        }

    fun verify(v:View){
        sname=_name.text.toString().trim()
        sPass=_password.text.toString().trim()

         database.child("Users").child(sname).get().addOnSuccessListener {
            if(it.exists()){
                val i=it.child("password").value
                if(i==sPass){
                    btn.isVisible=false
                    val b=findViewById<Button>(R.id.l_btn_lo)
                    b.isVisible=true

                    val sh=getSharedPreferences("useridnew", MODE_PRIVATE)
                    val ed=sh.edit()
                    ed.putString("id",sname)
                    ed.apply()
                }
                else{
                    Toast.makeText(applicationContext,"Password does not match Please try again",Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(applicationContext,"No Any Account Found on this number",Toast.LENGTH_LONG).show()
            }

        }.addOnFailureListener{

        }
    }

    fun btn_login_clicked(v: View){
        Toast.makeText(this,"Login Successfully",Toast.LENGTH_LONG).show()
        val i=Intent(this,MainActivity::class.java)
        startActivity(i)
    }

}