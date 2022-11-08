package com.example.cash_tree

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.IOException

class Home : Fragment() {

    private final val ARG_TEXT="atsts"
    private var te:String=""

    lateinit var _amount: TextView
    lateinit var _email: TextView
    lateinit var _tb: TextView
    lateinit var _tw: TextView
    lateinit var _as: TextView
    lateinit var _tc: TextView
    lateinit var _ft: TextView
    lateinit var _pt: TextView

    lateinit var database : DatabaseReference

    fun newInstance(t:String):Home {
        val f=Home()
        val args = Bundle()
        args.putString(ARG_TEXT,t)
        f.arguments=args
        database= Firebase.database.reference
        return f
    }


/*
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }
*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v= inflater.inflate(R.layout.fragment_home, container, false)
        if(arguments!=null){
            te= arguments?.getString("atsts").toString()
        }

        _amount=v.findViewById(R.id.h_account_amount)
        _email =v.findViewById(R.id.h_email)
        _tb    =v.findViewById(R.id.tbamount)
        _tw    =v.findViewById(R.id.twamount)
        _as    =v.findViewById(R.id.acstatus)
        _tc    =v.findViewById(R.id.teamno)
        _ft    =v.findViewById(R.id.free_task)
        _pt    =v.findViewById(R.id.pretask)

        database= Firebase.database.reference
        _email.text=te

        database.child("Users").child(te).get().addOnSuccessListener {
            if(it.exists()){
                _amount.text=it.child("totalb").value.toString()+" PKR"
                _tb.text=it.child("totalb").value.toString()+" PKR"
                _tw.text=it.child("totalw").value.toString()+" PKR"
                _as.text=it.child("accs").value.toString()
                if(_as.text=="UnPaid"){
                    _as.setTextColor(Color.rgb(255,0,0))

                }else{
                    _as.setTextColor(Color.rgb(0,255,0))
                    val ac=v.findViewById<TextView>(R.id.h_bonus)
                    ac.text="Upgraded"
                    val acb=v.findViewById<Button>(R.id.h_bon)
                    acb.isClickable=false
                }
                _tc.text=it.child("teamcount").value.toString()
                _ft.text=it.child("ftask").value.toString()
                _pt.text=it.child("ptask").value.toString()
            }
            else{
                Toast.makeText(context,"No Any Account Found on this number",Toast.LENGTH_LONG).show()
            }

        }.addOnFailureListener{

        }







        val with = v.findViewById(R.id.h_with) as Button
        val dep =  v.findViewById(R.id.h_dep) as  Button
        val bonus = v.findViewById(R.id.h_bon) as Button
        val transactions =  v.findViewById(R.id.Deposit) as ImageButton

        with.setOnClickListener({
        getWithdraw()
        });

        dep.setOnClickListener({
             getDeposit()
        });

        bonus.setOnClickListener({
            getBonus()
        });
        transactions.setOnClickListener({
            transactionData()
        });

        return v
    }

    fun getWithdraw(){
        val i =Intent(activity,Withdraw::class.java)
        startActivity(i)
    }

    fun getDeposit(){
        val i =Intent(activity,Deposite::class.java)
        startActivity(i)
    }

    fun getBonus(){
       Toast.makeText(activity,"No Bonus Available Right Now Try Again later",Toast.LENGTH_LONG).show()
        database.child("Users").child(te).get().addOnSuccessListener {
            if(it.exists()){
                val b=it.child("totalb").value.toString().toInt()
                val b1=it.child("ftask").value.toString().toInt()
                val b2=it.child("ptask").value.toString().toInt()
                if(b>=300){
                    database.child("Users").child(te).child("totalb").setValue((b-300).toString())
                    database.child("Users").child(te).child("ftask").setValue((b1+10).toString())
                    database.child("Users").child(te).child("ptask").setValue((b2+10).toString())
                    database.child("Users").child(te).child("accs").setValue("Paid")
                }
                else{
                    Toast.makeText(context,"Your Amount is less then Rs:300 please deposit Rs:300 first",Toast.LENGTH_LONG).show()
                }
            }
            else{
                Toast.makeText(context,"No Any Account Found on this number",Toast.LENGTH_LONG).show()
            }

        }.addOnFailureListener{

        }


    }

    fun transactionData(){
        Toast.makeText(activity,"Transaction Data",Toast.LENGTH_LONG).show()
    }
 }
