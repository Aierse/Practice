package com.example.fileexplorer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.fileexplorer.R
import java.io.File

class FileAdapter(
    private val mContext: Context,
    private val resId: Int,
    private val mList: List<File>) : ArrayAdapter<File>(mContext, resId, mList) {
    private val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView ?: inf.inflate(R.layout.file_list_item, null)

        val file = mList[position]
        val fileName = row.findViewById<TextView>(R.id.fileName)
        val fileSize = row.findViewById<TextView>(R.id.fileSize)
        val modifiedDate = row.findViewById<TextView>(R.id.modifiedDate)

        if(file.isDirectory) {
            fileName.text = file.name
            fileSize.text = ""
            modifiedDate.text = ""
        }
        else {
            fileName.text = file.name
            fileSize.text = file.length().toString()
            modifiedDate.text = file.lastModified().toString()
        }

        return row
    }
}