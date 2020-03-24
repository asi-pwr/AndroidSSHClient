package com.asi.sshclient.auth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asi.sshclient.R

class AuthAdapter(private val auth: Array<AuthData>, private val listener: AuthListener) :
    RecyclerView.Adapter<AuthAdapter.MyViewHolder>() {

    class MyViewHolder(view: View, val listener: AuthListener) : RecyclerView.ViewHolder(view),
        View.OnClickListener {

        val textView: TextView

        init {
            textView = view.findViewById(R.id.auth_text)
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onClick(adapterPosition)
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_layout, parent, false)
        return MyViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView.text = auth[position].toString()
    }

    override fun getItemCount() = auth.size

}