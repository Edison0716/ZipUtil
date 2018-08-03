package com.junlong0716.zip

import android.app.Application
import android.content.ContentValues.TAG
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import java.io.File
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 *@author: EdsionLi
 *@description:
 *@date: Created in 2018/8/3 下午4:11
 *@modified by:
 */
class ArchiverManager private constructor(application: Application) : BaseArchiver(application) {

    override fun handleMessage(msg: Message, weakReferenceContext: Application) {

    }

    companion object {
        //单例
        @Volatile
        private var INSTANCE: ArchiverManager? = null

        fun getInstance(application: Application): ArchiverManager {
            if (INSTANCE == null) {
                synchronized(ArchiverManager::class) {
                    if (INSTANCE == null) {
                        INSTANCE = ArchiverManager(application)
                    }
                }
            }
            return INSTANCE!!
        }
    }

    private var mThreadPool: Executor? = null
    private var mApplication = application
    private var mCurrentArchiver: BaseArchiver? = null
    init {
        //创建一个单线程类
        mThreadPool = Executors.newSingleThreadExecutor()
    }

    override fun doArchiver(files: Array<File>, zipPath: String) {

    }

    override fun doUnArchiver(zipPath: String, unZipPath: String, password: String, callback: IArchiverCallback) {
        mCurrentArchiver = getCorrectArchiver(getFileType(zipPath)!!)

        mThreadPool!!.execute {
            mCurrentArchiver!!.doUnArchiver(zipPath, unZipPath, password, callback)
        }
    }


    /**
     * 获取文件类型
     * @param filename
     * @return
     */
    private fun getFileType(filename: String): String? {
        Log.i(TAG, "getFileType: $filename")
        var type: String? = null
        if (TextUtils.isEmpty(filename))
            return type
        val temp = filename.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        type = temp[temp.size - 1]
        return type
    }

    private fun getCorrectArchiver(type: String): BaseArchiver? {
        return when (type) {
            ArchiverType._ZIP -> ZipArchiver(mApplication)
            else -> null
        }
    }

    object ArchiverType {
        val _RAR = "rar" //TODO 待实现
        val _ZIP = "zip"
        val _7Z = "7z" //TODO 待实现
    }
}