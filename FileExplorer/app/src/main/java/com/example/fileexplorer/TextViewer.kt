package com.example.fileexplorer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fileexplorer.databinding.ActivityTextViewerBinding
import java.io.File

class TextViewer : AppCompatActivity() {
    lateinit var binding: ActivityTextViewerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val file = intent.getSerializableExtra("file") as File

        binding.fileContent.text = file.name
    }
}