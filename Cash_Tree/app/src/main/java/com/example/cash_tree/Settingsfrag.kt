package com.example.cash_tree

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class Settingsfrag : Fragment() {

    lateinit var database : DatabaseReference
    lateinit var _e:TextView
    lateinit var  _n:TextView
    lateinit var _a:TextView

    private final val ARG_TEXT="atsts"
    private var te:String=""

    fun newInstance(t:String):Settingsfrag {
        val f=Settingsfrag()
        val args = Bundle()
        args.putString(ARG_TEXT,t)
        f.arguments=args
        database= Firebase.database.reference
        return f
    }


/*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

         // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_settings, container, false)
        if(arguments!=null){
            te= arguments?.getString("atsts").toString()
        }

        database= Firebase.database.reference

        _n=view.findViewById(R.id.textView2)
        _e=view.findViewById(R.id.textView3)
        _a=view.findViewById(R.id.textView5)


        database.child("Users").child(te).get().addOnSuccessListener {
            if(it.exists()){
                val b1=it.child("name").value.toString()
                val b2=it.child("email").value.toString()
                val b3=it.child("totalb").value.toString()+" PKR"
                _n.text=b1
                _e.text=b2
                _a.text=b3
            }
        }.addOnFailureListener{

        }

        val b=view.findViewById<Button>(R.id.button5)
        b.setOnClickListener(){
        exit_()
        }

        return view
        }



    fun exit_(){
        val i =Intent(activity,Welcome::class.java)
        startActivity(i)
    }


}