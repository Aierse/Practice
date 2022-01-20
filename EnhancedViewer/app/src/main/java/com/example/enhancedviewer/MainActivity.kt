package com.example.enhancedviewer

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.enhancedviewer.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.URI

class MainActivity : AppCompatActivity() {
    private val REQUEST_OPEN_FILE = 40
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "text/plain"
        }

        startActivityForResult(intent, REQUEST_OPEN_FILE)
    }



    private fun readFile(uri: URI): String {
        val stringBuilder = StringBuilder()

        try {
            val inputStream = contentResolver.openInputStream(uri)
            val reader = BufferedReader(InputStreamReader(inputStream))

            while (true) {
                var currentline: String? = reader.readLine() ?: break

                stringBuilder.append(currentline + "\n")
            }

            inputStream?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return stringBuilder.toString()
    }
}