package com.example.cash_tree

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class updateFirebase {

   public fun updateRegistration(x:Users,y:String){
        val dr=FirebaseDatabase.getInstance().reference.child("Users").child(y).setValue(x)


    }
}