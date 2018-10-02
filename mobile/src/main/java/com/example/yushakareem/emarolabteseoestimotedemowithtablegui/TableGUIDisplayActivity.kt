/*
If anything fails, or app crashes, check the parts of the code that have been hardcoded for demo purposes
 */
package com.example.yushakareem.emarolabteseoestimotedemowithtablegui

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_table_gui_display.*

class TableGUIDisplayActivity : AppCompatActivity() {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myRef: DatabaseReference = database.reference

    private val UIDWatchLGG6P1 = "6d390abb-96f6-4c3e-a5ef-4f2d4dc83be7"
    private val UIDWatchLGG6P2 = "52c4005d-2bd2-44b4-91db-6ed7eeda55a6"

    //Dont need drawable because un'po complicato
//    private lateinit var mDrawable: Drawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_gui_display)

        //Dont need drawable because un'po complicato
//        mDrawable = ContextCompat.getDrawable(applicationContext, R.drawable.textview_border)!!

        if (users[0].smartWatchUID.count()<10) {
            textView6.text = users[0].smartWatchUID
        } else {
            //The item view remains same as originally defined in layout
        }
//        if (users[2].smartWatchUID.count()<10) {
//            textView9.text = users[2].smartWatchUID
//        } else {
//            //The item view remains same as originally defined in layout
//        }

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
            textView8.text = dataSnapshot.child("users").child(UIDWatchLGG6P1).child("location").value.toString()

            //Check for Alarm
            if (textView8.text == "onEnterRelax"){
                textView8.setTextColor(Color.RED)
                textView8.text = textView8.text as String + " [ALARM]"
            } else {
                textView8.setTextColor(Color.BLACK)
            }

//            textView11.text = dataSnapshot.child("users").child(UIDWatchLGG6P2).child("location").value.toString()
    }
}