package com.example.fileexplorer

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.fileexplorer.adapters.FileAdapter
import com.example.fileexplorer.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    private val READ_STORAGE_PERMISSION = 100
    private val EXTERNAL_STORAGE_PATH = Environment.getExternalStorageDirectory().toString()
    private lateinit var nowPath: String
    private lateinit var binding: ActivityMainBinding
    lateinit var fileAdapter: FileAdapter
    lateinit var files: ArrayList<File>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupEvents()

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            nowPath = EXTERNAL_STORAGE_PATH
            afterGranted(EXTERNAL_STORAGE_PATH)
        }
        else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), READ_STORAGE_PERMISSION)
        }
    }

    override fun onBackPressed() {


        super.onBackPressed()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == READ_STORAGE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                nowPath = Environment.getExternalStorageDirectory().toString()
                afterGranted(nowPath)
            }
            else {
                if(!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE))  {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + BuildConfig.APPLICATION_ID))
                    startActivity(intent)

                    Toast.makeText(applicationContext, "권한 설정 후 다시 실행 해주세요.", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this, "권한 거부되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun setupEvents() {
        binding.fileListView.setOnItemClickListener { adapterView, view, i, l ->
            val clickedFile = files[i]

            if (clickedFile.isDirectory) {
                nowPath += "/" + clickedFile.name
                afterGranted(nowPath)
            }
        }
    }

    fun afterGranted(path: String) {
        files = File(path).listFiles().toCollection(ArrayList<File>())

        fileAdapter = FileAdapter(this, R.layout.file_list_item, files)

        binding.fileListView.adapter = fileAdapter
    }
}