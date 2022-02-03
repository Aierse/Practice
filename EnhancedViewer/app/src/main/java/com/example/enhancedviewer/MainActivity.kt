package com.example.enhancedviewer

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import com.example.enhancedviewer.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.view.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder
import android.text.InputType
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER


class MainActivity : AppCompatActivity(), ScrollViewListener {
    private lateinit var binding: ActivityMainBinding
    private val OPEN_REQUEST_CODE = 41
    var correctionable = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        openFileExplorer()
    }

    override fun onBackPressed() {
        if (binding.menu.visibility == View.VISIBLE) {
            binding.menu.visibility = View.INVISIBLE
            return
        }

        openFileExplorer()
    }

    override fun onScrollChanged(scrollView: ObservableScrollView, x: Int, y: Int, oldx: Int, oldy: Int) {
        binding.textBar.scrollTo(x, y)
        binding.nowLine.text = binding.textBar.scrollY.toString()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                OPEN_REQUEST_CODE -> {
                    data?.let { it ->
                        it.data?.let { it ->
                            contentResolver.query(it, null, null, null, null)?.use { cursor ->
                                val name = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                                cursor.moveToFirst()
                                binding.name.text = cursor.getString(name)
                            }

                            val content = readFile(it)
                            binding.textView.text = Filter().arrangement(content)

                            correctionScrollBar()

                            binding.textBar.apply {
                                scrollY = 0
                                nowLine = binding.nowLine
                                converter = Converter(textView.layout.height, textBar.height, textView.lineCount)
                            }

                            binding.totalLine.text = binding.textView.lineCount.toString()//binding.textView.lineCount.toString()
                        }
                    }
                }
            }
        }
        else {
            //아무런 파일도 선택되지 않았을 때 종료
            finish()
        }
    }

    private fun openFileExplorer() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "text/plain"
        }

        startActivityForResult(intent, OPEN_REQUEST_CODE)
    }

    private fun readFile(uri: Uri): String {
        val stringBuilder = StringBuilder()

        try {
            val inputStream = contentResolver.openInputStream(uri)
            val reader = BufferedReader(InputStreamReader(inputStream))

            var currentline: String? = null

            while (true) {
                currentline = reader.readLine() ?: break

                stringBuilder.append(Filter.deleteGarbageText(currentline) + "\n")
            }

            inputStream?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val target: String = stringBuilder.toString()

        return target.substring(0, target.length - 1)
    }

    fun menu(view: View) {
        if (binding.menu.visibility == View.INVISIBLE)
            binding.menu.visibility = View.VISIBLE
        else {
            binding.menu.visibility = View.INVISIBLE
        }
    }

    fun move(view: View) {
        binding.menu.visibility = View.INVISIBLE

        val edit = EditText(this).apply {
            inputType = InputType.TYPE_CLASS_NUMBER

            setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {
                    binding.textBar.line = text.toString().toInt() - 1
                }

                true
            }
        }

        val dialog = AlertDialog.Builder(this).apply {

            setTitle("페이지 이동")
            setMessage("페이지를 입력하세요.")
            setView(edit)

            setPositiveButton("확인") { dialog, which ->
                binding.textBar.line = edit.text.toString().toInt() - 1
            }
            setNegativeButton("취소") { dialog, which ->
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun correctionScrollBar() {
        if (correctionable) {
            val temp = View(this)
            temp.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, binding.textBar.height - 46, 1f)

            binding.textArea.addView(temp)
            correctionable = false
        }
    }
}