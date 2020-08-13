package com.jo.dailyutils.utils

import android.content.Context
import android.util.Log
import java.io.*

/**
 *Created by atian
 *on 2020/8/7
 *describe:文件工具（主要用途是是拷贝和打开.txt文件）
 * 文件存储在【assets】文件夹
 */

class AssetsTxtFileUtil {
    companion object{
        fun mReadTxtFile(strFilePath: String): String? {
            var content = "" //文件内容字符串
            //打开文件
            val file = File(strFilePath)
            //如果path是传递过来的参数，可以做一个非目录的判断
            if (file.isDirectory) {
            } else {
                try {
                    val instream: InputStream = FileInputStream(file)
                    if (instream != null) {
                        var line: String? = null
                        val buffreader = BufferedReader(
                            InputStreamReader(
                                FileInputStream(file), "UTF-8"
                            )
                        )
                        //分行读取
                        while (buffreader.readLine().also { line = it } != null) {
                            content += "$line;"
                        }
                        instream.close()
                    }
                } catch (e: FileNotFoundException) {
                    Log.d("TestFile", "The File doesn't not exist.")
                } catch (e: IOException) {
                    Log.d("TestFile", e.message!!)
                }
            }
            return content
        }

         fun readFromAssets(fileName: String,context : Context):String? {
            try {
                val `in`: InputStream = context.getAssets().open(fileName)
                return readTextFile(`in`)
            } catch (e: IOException) {
                e.printStackTrace()
            }
             return ""
        }

         fun readTextFile(inputStream: InputStream): String? {
            val outputStream = ByteArrayOutputStream()
            val buf = ByteArray(1024)
            var len: Int
            try {
                while (inputStream.read(buf).also { len = it } != -1) {
                    outputStream.write(buf, 0, len)
                }
                outputStream.close()
                inputStream.close()
            } catch (e: IOException) {
            }
            return outputStream.toString()
        }
    }
}