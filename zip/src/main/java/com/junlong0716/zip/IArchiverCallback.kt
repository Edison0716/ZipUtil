package com.junlong0716.zip

import android.os.Handler
import android.os.Message

/**
 *@author: EdsionLi
 *@description: 解压缩回调
 *@date: Created in 2018/8/3 下午4:09
 *@modified by:
 */
interface IArchiverCallback{
    fun onStartArchiver()

    fun onProgressArchiver(current: Int, total: Int)

    fun onEndArchiver()
}