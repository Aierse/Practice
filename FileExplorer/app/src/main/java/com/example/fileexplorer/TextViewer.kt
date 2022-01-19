package com.example.fileexplorer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fileexplorer.databinding.ActivityTextViewerBinding
import java.io.File
import java.io.FileReader
import java.io.IOException

class TextViewer : AppCompatActivity() {
    lateinit var binding: ActivityTextViewerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val file = intent.getSerializableExtra("file") as File

        try {
            val read = FileReader(file)
            var content: String = read.readText()

            binding.fileContent.text = content
        }
        catch (e: IOException){

        }
    }
}