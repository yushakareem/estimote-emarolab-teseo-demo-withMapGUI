package com.example.yushakareem.emarolabteseoestimotedemowithtablegui

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

interface OnItemClickListener{
    fun OnItemClick(position: Int)
}

class CustomAdapter(val userList: ArrayList<User>): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    var mListener : OnItemClickListener? = null

    // Recycler view stuff
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_layout,parent,false)
        return ViewHolder(v, mListener!!)
    }
    override fun getItemCount(): Int {
        return userList.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user: User = userList[position]

        holder.textViewSmartWatchUID.text = user.smartWatchUID
        holder.textViewSamrtWatchSmartPhoneName.text = user.smartWatchSmartPhoneName

    }
    class ViewHolder(itemView: View, listener: OnItemClickListener): RecyclerView.ViewHolder(itemView) {
        val textViewSmartWatchUID = itemView.findViewById(R.id.textViewSmartWatchUID) as TextView
        val textViewSamrtWatchSmartPhoneName = itemView.findViewById(R.id.textViewSmartWatchConnectedToPhoneName) as TextView

        // Connect Item to onClickListener
        val bla = itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val position =  adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.OnItemClick(position)
                }
            }
        })
    }
    // On Item Click in the RecyclerView
    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }
}