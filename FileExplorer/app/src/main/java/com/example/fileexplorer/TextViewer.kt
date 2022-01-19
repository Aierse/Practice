package com.example.fileexplorer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fileexplorer.databinding.ActivityMainBinding
import java.io.File

class TextViewer : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val file = intent.getStringExtra(("file"))

        binding.fileContent.text = file
    }
}