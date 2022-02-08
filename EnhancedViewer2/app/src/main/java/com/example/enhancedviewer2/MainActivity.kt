package com.example.enhancedviewer2

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.widget.EditText
import com.example.enhancedviewer2.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder
import android.text.InputType

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val OPEN_REQUEST_CODE = 41
    lateinit var textAdapter: TextAdapter
    val datas = mutableListOf<String>()

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

                            val content = Filter().arrangement(readFile(it))

                            initRecycler(content)

                            binding.totalLine.text = textAdapter.itemCount.toString()
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

            while (true) {
                var currentline = reader.readLine() ?: break

                stringBuilder.append(Filter.deleteGarbageText(currentline) + "\n")
            }

            inputStream?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val target: String = stringBuilder.toString()

        return target.substring(0, target.length - 1) // 마지막 엔터 제외
    }

    fun menu(view: View) {
        binding.menu.visibility = if (binding.menu.visibility == View.INVISIBLE) View.VISIBLE else View.INVISIBLE
    }

    fun move(view: View) {
        binding.menu.visibility = View.INVISIBLE

        val edit = EditText(this).apply {
            inputType = InputType.TYPE_CLASS_NUMBER
        }

        val dialog = AlertDialog.Builder(this).apply {
            setTitle("페이지 이동")
            setMessage("페이지를 입력하세요.")
            setView(edit)

            setPositiveButton("확인") { dialog, which ->
                //binding.textBar.line = edit.text.toString().toInt() - 1
            }
            setNegativeButton("취소") { dialog, which ->
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun initRecycler(textList: ArrayList<String>) {
        textAdapter = TextAdapter(this)
        binding.textBar.adapter = textAdapter


        datas.apply {
            for (i in textList) {
                add(i)
            }
        }

        textAdapter.datas = datas
        textAdapter.notifyDataSetChanged()
    }
}