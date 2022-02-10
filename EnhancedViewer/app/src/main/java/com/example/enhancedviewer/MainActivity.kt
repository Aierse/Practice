package com.example.enhancedviewer

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.widget.EditText
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder
import android.text.InputType
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.example.enhancedviewer.databinding.ActivityMainBinding
import com.example.enhancedviewer2.Filter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val OPEN_REQUEST_CODE = 41
    lateinit var textAdapter: TextAdapter
    val datas = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val onScrollListener: RecyclerView.OnScrollListener =
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = binding.textBar.layoutManager as LinearLayoutManager
                    val first = layoutManager.findFirstVisibleItemPosition()

                    binding.nowLine.text = (first + 1).toString()
                }
            }

        binding.textBar.addOnScrollListener(onScrollListener)
        binding.textBar.addOnItemTouchListener(object :
            RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                if (binding.menu.visibility == View.VISIBLE) {
                    binding.menu.visibility = View.INVISIBLE
                    return true
                }

                if(e.action == MotionEvent.ACTION_UP && binding.textBar.scrollState == RecyclerView.SCROLL_STATE_IDLE){
                    val layoutManager = binding.textBar.layoutManager as LinearLayoutManager
                    var movement: Int = 0
                    val smoothScroller: LinearSmoothScroller
                    val center = binding.textBar.height / 2

                    if (center < e.y) {
                        // 증앙 아래 터치 시 마지막 요소를 맨 위까지 스크롤
                        movement = layoutManager.findLastCompletelyVisibleItemPosition() + 1
                        smoothScroller = object : LinearSmoothScroller(context) {
                            override fun getVerticalSnapPreference(): Int {
                                return LinearSmoothScroller.SNAP_TO_START
                            }
                        }
                    }
                    else {
                        movement = layoutManager.findFirstCompletelyVisibleItemPosition() - 1
                            smoothScroller = object : LinearSmoothScroller(context) {
                                override fun getVerticalSnapPreference(): Int {
                                    return LinearSmoothScroller.SNAP_TO_END
                                }
                            }
                    }

                    smoothScroller.targetPosition = movement
                    layoutManager.startSmoothScroll(smoothScroller)
                }

                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                TODO("not implemented")
            }
        })

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
        } else {
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

    fun addBookMark(view: View) {

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
                var value = edit.text.toString().toInt() - 1
                val input = if (value < 0) 0
                else if (value >= textAdapter.itemCount) textAdapter.itemCount - 1
                else value

                val layoutManager = binding.textBar.layoutManager as LinearLayoutManager

                layoutManager.scrollToPositionWithOffset(input, 0)
            }
            setNegativeButton("취소") { dialog, which ->
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun initRecycler(textList: ArrayList<String>) {
        textAdapter = TextAdapter(this, binding.textBar.height)
        binding.textBar.adapter = textAdapter

        datas.apply {
            clear()
            for (i in textList) {
                add(i)
            }
        }

        textAdapter.datas = datas
        textAdapter.notifyDataSetChanged()
    }
}