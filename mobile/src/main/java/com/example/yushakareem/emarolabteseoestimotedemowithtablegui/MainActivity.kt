package com.example.yushakareem.emarolabteseoestimotedemowithtablegui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable

val users = ArrayList<User>()

class MainActivity : AppCompatActivity(), Serializable {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myRef: DatabaseReference = database.reference

    private val adapter = CustomAdapter(users)

    override fun onStart() {
        super.onStart()
        Log.d("---SP: usersIsEmpty()",users.isEmpty().toString())
        if (users.isEmpty()) {
            Log.d("---SP: Here ","1")

        } else {
            //do nothing
            Log.d("---SP: Here ","4")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("---SP: Here ","2")
                if (users.isEmpty()) {
                    for (singledata in dataSnapshot.child("users").children) {
                        Log.d("---SP: Here ", "3")
                        users.add(User(singledata.key.toString(), ""))//Phonename can be added to the DB later (from smartwatch)
                        adapter.notifyItemInserted(users.count())
                    }
                } else {
                    // dont add anything
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("ErrorInReadingFirebase", "onCancelled", databaseError.toException())
            }
        })

        // Code for the List view (of Smart Watches) on the mainActivity
        val recyclerView = findViewById(R.id.recyclerView) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false) as RecyclerView.LayoutManager?

        // This allows to get the click on the items(cards) in the recyclerView
        adapter.setOnItemClickListener(object: OnItemClickListener {
            override fun OnItemClick(position: Int) {
                changeItemName(position)
            }
        })

        recyclerView.adapter = adapter

        // Button taking to Table GUI
        val buttonForTableGUI = findViewById<Button>(R.id.button)
        buttonForTableGUI.setOnClickListener{
            val intent = Intent(this,TableGUIDisplayActivity::class.java)
            startActivity(intent)
        }
    }

    private fun changeItemName(position: Int) {
        val mBuilder = AlertDialog.Builder(this@MainActivity)
        val mView = layoutInflater.inflate(R.layout.dialog,null)
        val mName = mView.findViewById<EditText>(R.id.PersonName)
        val mButton = mView.findViewById<Button>(R.id.buttonSave)

        mButton.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                if (!mName.text.toString().isEmpty()) {
                    users[position].smartWatchUID = mName.text.toString()
                    adapter.notifyItemChanged(position)
                    val intent = Intent(applicationContext,MainActivity::class.java)
                    startActivity(intent) // going back to mainActivity
                } else {
                    Toast.makeText(this@MainActivity,"Please write a name",Toast.LENGTH_SHORT).show()
                }
            }
        })

        mBuilder.setView(mView)
        val dialog = mBuilder.create()
        dialog.show()
    }

}
