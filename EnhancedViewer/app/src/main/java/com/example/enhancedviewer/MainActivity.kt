package com.example.enhancedviewer

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.enhancedviewer.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_OPEN_FILE -> {
                    data?.let { it ->
                        var currentUri = it.data
                        currentUri?.let {
                            val content = readFile(it)
                            binding.textView.text = content
                        }
                    }
                }
            }
        }
    }

    private fun readFile(uri: Uri): String {
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

        Toast.makeText(this, stringBuilder.toString() + "AAAA", Toast.LENGTH_SHORT)

        return stringBuilder.toString()
    }
}