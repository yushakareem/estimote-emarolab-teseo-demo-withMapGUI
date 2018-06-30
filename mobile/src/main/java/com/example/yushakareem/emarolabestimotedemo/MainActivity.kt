package com.example.yushakareem.emarolabestimotedemo

import android.content.Context
import android.nfc.Tag
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var button4: Button
    private lateinit var button6: Button

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    private val myRef: DatabaseReference = database.reference

    private lateinit var userID: String
    private lateinit var loc: String

    private val UIDWatchLGG6P1 = "997ce849-78f0-43c1-b033-7da567cb1d91"
    private val UIDWatchLGG6P2 = "f0b03ed8-9527-49e0-ad24-5a70a2cf254e"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.imageView = this.findViewById(R.id.imageView)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                showData(dataSnapshot)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("ErrorInReadingFirebase", "onCancelled", databaseError.toException())
            }
        })
    }

    private fun showData(dataSnapshot: DataSnapshot) {
        loc = dataSnapshot.child("users").child(UIDWatchLGG6P1).child("location").value.toString()
        showImageOnScreen(loc)
    }

    private fun showImageOnScreen(locationString :String) {
        when (locationString) {
            "lab_office" -> this.imageView.setImageResource(R.drawable.lab_office)
            "lab_mocap" -> this.imageView.setImageResource(R.drawable.lab_mocap)
            "lab_office_q1" -> this.imageView.setImageResource(R.drawable.lab_office_q1)
            "lab_office_q2" -> this.imageView.setImageResource(R.drawable.lab_office_q2)
            "lab_office_q3" -> this.imageView.setImageResource(R.drawable.lab_office_q3)
            "lab_office_q4" -> this.imageView.setImageResource(R.drawable.lab_office_q4)
            "unknown" -> this.imageView.setImageResource(R.drawable.lab_empty)
        }
    }
}

//Note:
/*
Add this app:srcCompat="@drawable/emarolabmap"  at the end of ImageView in activity_main.xml, to have a particular image in imageView from the beginning

To work later:
singleSnapshot has two children .. so take care of that --->
for (singleSnapshot in dataSnapshot.children) {
    println(dataSnapshot.childrenCount)
    println(singleSnapshot.childrenCount)
    loc = singleSnapshot.child("users").child(UIDWatchLGG6P1).child("location").value.toString()
    println(loc)
    loc = singleSnapshot.child("users").child(UIDWatchLGG6P2).child("location").value.toString()
    println(loc)
}

myRef.addListenerForSingleValueEvent(object : ValueEventListener {
    override fun onDataChange(p0: DataSnapshot) {
        showData(p0)
    }

    override fun onCancelled(p0: DatabaseError) {
        Log.e("ErrorInReadingFirebase", "onCancelled", p0.toException())
    }
})
 */
