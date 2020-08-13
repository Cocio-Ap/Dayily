package com.jo.dailyutils.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by atian
 * on 2020/8/10
 * describe: 文件工具（主要用途是是拷贝和打开.db文件）
 * 文件存储在【assets】文件夹
 */

public class AssertsDBFileUtil {
    private static String DB_NAME = "IVCompatibility.db";


    /**
     * /data/data 路径
     * @param context
     * @return
     */
    private static String getPath(Context context){
        return "/data/data/" + context.getPackageName() + "/databases";
    }

    /**
     * 将assets文件夹下/db的本地库拷贝到/data/data/下
     *
     * @param context
     */
    public static void copyDbFile(Context context) {
        InputStream in = null;
        FileOutputStream out = null;

        String path = getPath(context);
        File file = new File(path + "/" + DB_NAME);

        try {

            //创建文件夹
            File file_ = new File(path);
            if (!file_.exists()){
                file_.mkdirs();
            }
            //创建新的文件
            if (!file.exists()){
                file.createNewFile();
            }

            // 从assets目录下复制
            in = context.getAssets().open(DB_NAME);
            out = new FileOutputStream(file);
            int length = -1;
            byte[] buf = new byte[1024];
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null){
                    in.close();
                }
                if (out != null){
                    out.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

    /**
     * 查询
     * @param context
     * @param tableName
     * @param columns
     * @return
     */
    public static Cursor query(Context context,String tableName,String[] columns) {
        Cursor cursor = null;
        SQLiteDatabase sql = SQLiteDatabase.openOrCreateDatabase(getPath(context) + "/" + DB_NAME ,null);
        try {
            cursor = sql.query(tableName, columns, null, null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cursor;
    }
}
