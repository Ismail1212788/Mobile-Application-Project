package com.example.cash_tree

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class Withdraw : AppCompatActivity() {

    lateinit var a_title:String
    var amount:Int=0
    lateinit var _accounttype:String
    lateinit var _accountnumber:String
    lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_withdraw)

    }

    fun sendRequest(v: View){
        var flag:Boolean =true
        var flag1:Boolean =true
        database= Firebase.database.reference
        a_title=findViewById<EditText>(R.id.w_name_textfeild).text.toString()
        amount=findViewById<EditText>(R.id.w_amount_textfeild).text.toString().toInt()
        _accountnumber=findViewById<EditText>(R.id.w_accountnumber_textfeild).text.toString()

        if(a_title==""||_accountnumber==""){
            Toast.makeText(this,"Please Fill all of feilds", Toast.LENGTH_LONG).show()
            flag=false
        }
        if(findViewById<RadioButton>(R.id.w_jazzcash).isChecked){ _accounttype="Jazzcash" }
        else{ _accounttype="Easypaisa" }
        if(amount>100){
            flag1=true
        }
        else{
            flag=false
            Toast.makeText(this,"Amount must be greater than Rs:100", Toast.LENGTH_LONG).show()
        }


        if(flag ==true && flag1==true){

            val sh=getSharedPreferences("useridnew", MODE_PRIVATE)
            val val_=sh.getString("id","")

            database.child("Users").child(val_!!).get().addOnSuccessListener {
                if(it.exists()){
                    val b2=it.child("totalb").value.toString().toInt()
                    val b3=it.child("totalw").value.toString().toInt()
                    if(b2>=amount){
                        database.child("Users").child(val_).child("totalb").setValue((b2-amount))
                        database.child("Users").child(val_).child("totalw").setValue((b3+amount))
                        finish()
                    }
                    else{
                        Toast.makeText(applicationContext,"Your Current Balance is less then Withdraw Amount",Toast.LENGTH_LONG).show()
                    }
                }
            }.addOnFailureListener{

            }

        }
    }


    fun updateTransaction():Boolean{
    return true
    }

}