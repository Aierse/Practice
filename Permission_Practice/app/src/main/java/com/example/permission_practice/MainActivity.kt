package com.example.permission_practice

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.permission_practice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val READ_STORAGE_PERMISSION = 100

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED -> {
                afterGranted("AlreadyGranted")
            }
            else -> {
                // You can directly ask for the permission.
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), READ_STORAGE_PERMISSION)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            READ_STORAGE_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    afterGranted(("PermissionGranted"))
                }
                else {
                    if(!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE))  {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                    .setData(Uri.parse("package:" + BuildConfig.APPLICATION_ID))

                        startActivity(intent)
                        Toast.makeText(applicationContext, "권한 설정 후 다시 실행 해주세요.", Toast.LENGTH_SHORT).show()

                    }
                    else {
                        Toast.makeText(this, "권한 거부되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun afterGranted(text: String) {
        binding.text.text = text
    }
}