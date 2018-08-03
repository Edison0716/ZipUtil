package com.junlong0716.zip

import android.app.Application
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity

import java.lang.ref.WeakReference

/**
 * @author: EdsionLi
 * @description: 防止内存泄露 Handler
 * @date: Created in 2018/8/3 下午4:45
 * @modified by:
 */
abstract class MyWeakReferenceHandler<in T : Application>(t: T) : Handler() {

    private var mWeakReferenceContext: WeakReference<T>? = null

    init {
        mWeakReferenceContext = WeakReference(t)
    }

    abstract fun handleMessage(msg: Message, weakReferenceContext: T)

    override fun handleMessage(msg: Message) {
        // TODO Auto-generated method stub
        super.handleMessage(msg)
        handleMessage(msg, mWeakReferenceContext!!.get()!!)
    }
}
