package com.example.cash_tree
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class Registration : AppCompatActivity() {

    lateinit var _name: String
    lateinit var _email: String
    lateinit var _upliner: String
    lateinit var _accounttype: String
    lateinit var _accountnumber: String
    lateinit var _password: String
    lateinit var database :DatabaseReference
    lateinit var but:Button
    var flag=true
    var flag1=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        database= Firebase.database.reference

    }


    fun Validate(v: View){
        val d=findViewById<EditText?>(R.id.r_name_textfeild).text.toString()
        val d1=findViewById<EditText?>(R.id.r_email_textfeild).text.toString()
        val d2=findViewById<EditText?>(R.id.r_refer_textfeild).text.toString()
        val d3=findViewById<EditText>(R.id.r_accountnumber_textfeild).text.toString()
        val d4=findViewById<EditText>(R.id.r_password_textfeild).text.toString()


        if(d==""||d1==""){
            Toast.makeText(this,"Please Fill all of feilds",Toast.LENGTH_LONG).show()
            flag=false
        }

        else if(findViewById<EditText>(R.id.r_password_textfeild).text.toString()==""||findViewById<EditText>(R.id.r_confirm_password_textfeild).text.toString()=="")
        {
            Toast.makeText(this,"Please provide your password",Toast.LENGTH_LONG).show()
            flag=false
        }


        else if(!findViewById<RadioButton>(R.id.r_jazzcash).isChecked && !findViewById<RadioButton>(R.id.r_easypaisa).isChecked){
            Toast.makeText(this,"Please select anyone of them Easypaisa or Jazzcash",Toast.LENGTH_LONG).show()
            flag=false
        }
        else if(findViewById<EditText>(R.id.r_password_textfeild).text.toString()!=findViewById<EditText>(R.id.r_confirm_password_textfeild).text.toString())
        {
            Toast.makeText(this,"Passwords are not Same",Toast.LENGTH_LONG).show()
            flag=false
        }
        else if(!findViewById<RadioButton>(R.id.r_agreement).isChecked){
            Toast.makeText(this,"Please Accept Terms and Conditions",Toast.LENGTH_LONG).show()
            flag=false
        }

        if(flag) {
            Register()
                val i = Intent(this, Login::class.java)
                 startActivity(i)
                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_LONG).show()
            }
        }


    fun Register(){
        _name=findViewById<EditText?>(R.id.r_name_textfeild).text.toString()
        _email=findViewById<EditText?>(R.id.r_email_textfeild).text.toString()
        _upliner=findViewById<EditText?>(R.id.r_refer_textfeild).text.toString()
        if(_upliner==""){
            _upliner="03138405757"
        }
        if(findViewById<RadioButton>(R.id.r_jazzcash).isChecked){ _accounttype="Jazzcash" }
        else{ _accounttype="Easypaisa" }
        _accountnumber=findViewById<EditText>(R.id.r_accountnumber_textfeild).text.toString()
        _password=findViewById<EditText>(R.id.r_password_textfeild).text.toString()
        val u=Users(_name,_email,_upliner,_accounttype,_accountnumber,_password,0,0,"UnPaid",0,0,0,0,0)
        val updt=updateFirebase()
        updt.updateRegistration(u,_accountnumber)
        writetofile(_accountnumber)
        Toast.makeText(this,u.toString(),Toast.LENGTH_LONG).show()
        Toast.makeText(this,"Account created successfully",Toast.LENGTH_LONG).show()
    }

     fun checkAlreadyAccount(v:View){
        val aa=findViewById<EditText>(R.id.r_accountnumber_textfeild).text.toString()
        database.child("Users").child(aa).get().addOnSuccessListener {

          if(it.exists()){
              Toast.makeText(applicationContext,"Already Account Registered on this number",Toast.LENGTH_LONG).show()
              but=findViewById(R.id.r_registernow_button)
              but.isVisible=false
              val bue=findViewById<Button>(R.id.r_validate)
              bue.isVisible=true




          }else{
              but=findViewById(R.id.r_registernow_button)
              but.isVisible=true
              val bue=findViewById<Button>(R.id.r_validate)
              bue.isVisible=false
              _upliner=findViewById<EditText?>(R.id.r_refer_textfeild).text.toString()
              if(_upliner==""){
                  _upliner="03138405757"
              }
              database.child("Users").child(_upliner!!).get().addOnSuccessListener {
                  if(it.exists()){
                      val b1=it.child("ftask").value.toString().toInt()
                      val b2=it.child("ptask").value.toString().toInt()

                          database.child("Users").child(_upliner).child("ftask").setValue((b1+10).toString())
                          database.child("Users").child(_upliner).child("ptask").setValue((b2+10).toString())


                  }
              }.addOnFailureListener{

              }

          }

            }.addOnFailureListener{

        }
    }
    fun writetofile(d:String){
        val sh=getSharedPreferences("userid", MODE_PRIVATE)
        val ed=sh.edit()
        ed.putString("id",d)
        ed.apply()
    }
}