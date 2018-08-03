# ZipUtil 

1、解压缩.zip</br>
2、解压缩.rar</br>
3、解压缩.7z</br>

# 使用方法

```
ArchiverManager.getInstance(this.application).doUnArchiver(source, destPath, "***压缩密码***", object : IArchiverCallback {
                override fun onStartArchiver() {
                    //解压缩开始
                    Toast.makeText(this@MainActivity, "解压缩开始", Toast.LENGTH_SHORT).show()
                }

                override fun onProgressArchiver(current: Int, total: Int) {
                    //进度回调
                    //current 当前正在压缩第几个文件
                    //total 总计压缩文件个数
                    Log.d("onZipProgress", current.toString())
                }

                override fun onEndArchiver() {
                    //解压缩完成
                    Toast.makeText(this@MainActivity, "解压缩完成", Toast.LENGTH_SHORT).show()
                }
            })
```

## 日志

2018-08-03 </br>
完成对 .zip 文件的压缩
