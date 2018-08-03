package com.junlong0716.ziputil

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.junlong0716.zip.ArchiverManager
import com.junlong0716.zip.IArchiverCallback
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {
    private val source = Environment.getExternalStorageDirectory().absolutePath + File.separator + "testRar.zip"
    private val destpath = Environment.getExternalStorageDirectory().absolutePath + File.separator + "testRar" + File.separator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rxPermissions = RxPermissions(this)
        rxPermissions.request(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE).subscribe {
        }

        bt_unzip.setOnClickListener {
            ArchiverManager.getInstance(this.application).doUnArchiver(source, destpath, "", object : IArchiverCallback {
                override fun onStartArchiver() {
                    Toast.makeText(this@MainActivity, "解压缩开始", Toast.LENGTH_SHORT).show()
                }

                override fun onProgressArchiver(current: Int, total: Int) {
                    Log.d("onZipProgress", current.toString())
                }

                override fun onEndArchiver() {
                    Toast.makeText(this@MainActivity, "解压缩完成", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
