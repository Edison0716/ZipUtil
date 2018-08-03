package com.junlong0716.zip

import android.app.Application
import android.support.v7.app.AppCompatActivity
import java.io.File

/**
 *@author: EdsionLi
 *@description: 解压缩基类
 *@date: Created in 2018/8/3 下午4:07
 *@modified by:
 */
abstract class BaseArchiver(application: Application) : MyWeakReferenceHandler<Application>(application) {
    /**
     * 压缩文件
     * @param files
     * @param destpath
     */
    abstract fun doArchiver(files: Array<File>, zipPath: String)

    /**
     * 解压文件
     * @param srcfile
     * @param unrarPath
     */
    abstract fun doUnArchiver(zipPath: String, unZipPath: String, password: String, callback: IArchiverCallback)
}