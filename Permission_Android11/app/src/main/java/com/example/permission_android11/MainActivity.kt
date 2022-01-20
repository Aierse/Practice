package com.example.permission_android11

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.permission_android11.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val CREATE_REQUEST_CODE = 40
    private val OPEN_REQUEST_CODE = 41
    private val SAVE_REQUEST_CODE = 42
    private val TREE_REQUEST_CODE = 43

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)

        startActivityForResult(intent, TREE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var currentUri: Uri? = null

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CREATE_REQUEST_CODE -> {
                    if (data != null) {
                        binding.fileText.setText("")
                    }
                }
                SAVE_REQUEST_CODE -> {
                    data?.let {
                        currentUri = it.data
                        currentUri?.let {
                            writeFileContent(it)
                        }
                    }
                }
                OPEN_REQUEST_CODE -> {
                    data?.let {
                        currentUri = it.data
                        currentUri?.let {
                            val content = readFileContent(it)
                            binding.textView.text = content
                        }
                    }
                }
            }
        }
    }

    private fun writeFileContent(uri: Uri) {
        try {
            val pfd = contentResolver.openFileDescriptor(uri, "w")
            val fileOutputStream = FileOutputStream(pfd?.fileDescriptor)

            val textContent = binding.fileText.text.toString()

            fileOutputStream.write(textContent.toByteArray())
            fileOutputStream.close()
            pfd?.close()
        } catch (e: Throwable) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun readFileContent(uri: Uri): String {
        val stringBuilder = StringBuilder()

        try {
            val inputStream = contentResolver.openInputStream(uri)
            val reader = BufferedReader(InputStreamReader(inputStream))

            while (true) {
                var currentline = reader.readLine()

                if (currentline == null)
                    break

                stringBuilder.append(currentline + "\n")
            }

            inputStream?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return stringBuilder.toString()
    }

    fun newFile(view: View) {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)

        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TITLE, "newfile.txt")

        startActivityForResult(intent, CREATE_REQUEST_CODE)
    }

    fun saveFile(view: View) {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)

        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "text/plain"

        startActivityForResult(intent, SAVE_REQUEST_CODE)
    }

    fun openFile(view: View) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)

        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "text/plain"

        startActivityForResult(intent, OPEN_REQUEST_CODE)
    }
}