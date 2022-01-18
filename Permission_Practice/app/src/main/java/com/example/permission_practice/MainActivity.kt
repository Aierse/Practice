package com.example.permission_practice

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
import com.example.permission_practice.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    val READ_STORAGE_PERMISSION = 100
    private lateinit var binding: ActivityMainBinding
    private val EXTERNAL_STORAGE_PATH = Environment.getExternalStorageDirectory().toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            afterGranted(EXTERNAL_STORAGE_PATH)
        }
        else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), READ_STORAGE_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == READ_STORAGE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                afterGranted(EXTERNAL_STORAGE_PATH)
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

    fun afterGranted(path: String) {
        val files = File(path).listFiles()

        var strFileList: String? = "App Directory File Lists\n"
        for(file in files){
            strFileList += "->" + file.name + "\n"
        }
        binding.text.text = strFileList
    }
}