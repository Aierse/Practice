package com.example.fileexplorer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.fileexplorer.R
import java.io.File
import java.text.SimpleDateFormat

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
        else if (file.extension == "txt"){
            fileName.text = file.name
            fileSize.text = (file.length() / 1024).toString() + "KB"
            modifiedDate.text = SimpleDateFormat("yyyy/MM/dd").format(file.lastModified())
        }

        return row
    }
}