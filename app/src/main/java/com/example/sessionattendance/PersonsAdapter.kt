package com.example.sessionattendance

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.net.Inet4Address

class PersonsAdapter(
    var personNameList: ArrayList<String>,
    var personPhoneNumberList: ArrayList<String>,
    var personCourseList: ArrayList<String>,
    var personAddressList: ArrayList<String>,
    var context : Context) : RecyclerView.Adapter<PersonsAdapter.PersonViewHolder>(){

    class PersonViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var textViewPersonName : TextView = itemView.findViewById(R.id.name)
        var textViewPhoneNumber : TextView = itemView.findViewById(R.id.phone_number)
        var textViewCourse : TextView = itemView.findViewById(R.id.course)
        var textViewAddress : TextView = itemView.findViewById(R.id.address)
        var cardView : CardView = itemView.findViewById(R.id.card_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_view, parent, false)

        return PersonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return personNameList.size
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {

        holder.textViewPersonName.text = personNameList.get(position)
        holder.textViewPhoneNumber.text = personPhoneNumberList.get(position)
        holder.textViewCourse.text = personCourseList.get(position)
        holder.textViewAddress.text = personAddressList.get(position)

        var alert = AlertDialog.Builder(context)
        alert.setTitle("Delete!")
        alert.setMessage("Do you want to delete this item from the list?")
        alert.setCancelable(false)
        alert.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->

            dialogInterface.cancel()

        })
        alert.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->

            MainActivity.deleted()

        })

        alert.create().show()
    }

}
