package com.mwsniper.review_listview.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.listview_practice.R
import com.example.listview_practice.datas.Room

class RoomAdapter(val mContext: Context, val resId: Int, val mList: List<Room>) : ArrayAdapter<Room>(mContext, resId, mList) {
    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView

        if (tempRow == null) {
            tempRow = inf.inflate(R.layout.room_list_item, null)
        }

        val row = tempRow!!

        val data = mList[position]
        val price = row.findViewById<TextView>(R.id.priceTxt)
        val address = row.findViewById<TextView>(R.id.addressTxt)
        val desc = row.findViewById<TextView>(R.id.descTxt)

        price.text = "${data.deposit} / ${data.monthlyRent}"
        address.text = "${data.address}, ${data.floor}"
        desc.text = data.description

        return tempRow!!
    }
}