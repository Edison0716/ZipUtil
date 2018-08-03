package com.junlong0716.zip

import android.app.Application
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import net.lingala.zip4j.core.ZipFile
import net.lingala.zip4j.exception.ZipException
import net.lingala.zip4j.model.FileHeader
import java.io.File

/**
 *@author: EdsionLi
 *@description:
 *@date: Created in 2018/8/3 下午4:19
 *@modified by:
 */
class ZipArchiver(application: Application) : BaseArchiver(application) {

    override fun handleMessage(msg: Message, weakReferenceContext: Application) {}

    override fun doArchiver(files: Array<File>, zipPath: String) {
        //todo 压缩 文件
    }

    override fun doUnArchiver(zipPath: String, unZipPath: String, password: String, callback: IArchiverCallback) {
        if (TextUtils.isEmpty(zipPath) || TextUtils.isEmpty(unZipPath)) return //判断文件路径是否为空
        val zipFile = File(zipPath)
        if (!zipFile.exists()) return //判断文件是否存在
        try {
            //进行解压操作
            val mZipFile = ZipFile(zipFile)
            mZipFile.setFileNameCharset("GBK")

            Log.d("Zip File Name",mZipFile.file.name)

            if (!mZipFile.isValidZipFile) throw ZipException("文件不合法！")

            val mUnZipPath = File(unZipPath)

            if (mUnZipPath.isDirectory && !mUnZipPath.exists()) {
                //文件不存在则创建文件
                mUnZipPath.mkdir()
            }

            //如果压缩文件上锁
            if (mZipFile.isEncrypted) {
                mZipFile.setPassword(password.toCharArray())
            }

            //开始解压
            this.post { callback.onStartArchiver() }

            var fileHeader: FileHeader? = null

            //获取文件大小
            val totalSize = mZipFile.fileHeaders.size

            //遍历 压缩文件 子文件
            for (i in 0 until mZipFile.fileHeaders.size) {
                fileHeader = mZipFile.fileHeaders[i] as FileHeader?

                //解压文件
                mZipFile.extractFile(fileHeader, unZipPath)

                //当前解压到第几个文件
                val currentTotalSize = i

                //进度回调
                this.post { callback.onProgressArchiver(currentTotalSize + 1, totalSize) }
            }
        } catch (e: ZipException) {
            e.printStackTrace()
        }
        this.post{
            //解压完成
            callback.onEndArchiver()
        }
    }
}