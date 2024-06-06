package com.ai.ai_gen.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ai.ai_gen.bean.User;
import com.ai.ai_gen.bean.UserInfo;

import java.util.ArrayList;

public class DBOpenHelper extends SQLiteOpenHelper {
    /**
     * 声明一个AndroidSDK自带的数据库变量db
     */
    private final SQLiteDatabase db;

    public DBOpenHelper(Context context) {
        super(context, "db_Gen", null, 1);
        db = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS user(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "avatar TEXT," +
                "password TEXT," +
                "email TEXT," +
                "phonenum TEXT," +
                "login_status INTEGER DEFAULT 0)"  // 新增的列，默认值为 0 表示未登录
        );
        db.execSQL("CREATE TABLE IF NOT EXISTS userinfo(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "phonenum TEXT," +
                "name TEXT," +
                "avatar TEXT," +
                "work TEXT," +
                "qm TEXT," +
                "sex TEXT)"
        );
    }

    //版本适应
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

    /**
     * 增加用户
     */
    public void add(String name, String avatar, String password, String email, String phonenum) {
        db.execSQL("INSERT INTO user (name, avatar, password, email, phonenum) VALUES(?,?,?,?,?)",
                new Object[]{name, avatar, password, email, phonenum});
    }

    /**
     * 删除用户
     */
    public void delete(String name, String password) {
        db.execSQL("DELETE FROM user WHERE name = ? AND password = ?", new Object[]{name, password});
    }

    /**
     * 更新密码
     */
    public void updatePassword(String phonenum, String newPassword) {
        ContentValues values = new ContentValues();
        values.put("password", newPassword);
        db.update("user", values, "phonenum = ?", new String[]{phonenum});
    }

    public void updateUserInfo(String phonenum, String newName, String newAvatar) {
        ContentValues values = new ContentValues();
        values.put("name", newName);
        values.put("avatar", newAvatar);
        db.update("user", values, "phonenum = ?", new String[]{phonenum});
    }
    public boolean isUserExist(String phonenum) {
        @SuppressLint("Recycle") Cursor cursor = db.query("user", null, "phonenum = ?", new String[]{phonenum}, null, null, null);
        return cursor.moveToFirst();
    }

    /**
     * 更新登录状态
     */
    public void updateLoginStatus(String phonenum, int loginStatus) {
        ContentValues values = new ContentValues();
        values.put("login_status", loginStatus);
        db.update("user", values, "phonenum = ?", new String[]{phonenum});
//        onUpgrade(db,1,2);
    }

    /**
     * 获取所有用户数据
     */
    public ArrayList<User> getAllData() {
        ArrayList<User> list = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = db.query("user", null, null, null, null, null, "name DESC");
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String avatar = cursor.getString(cursor.getColumnIndex("avatar"));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));
            @SuppressLint("Range") String phonenum = cursor.getString(cursor.getColumnIndex("phonenum"));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("password"));
            @SuppressLint("Range") int loginStatus = cursor.getInt(cursor.getColumnIndex("login_status"));

            list.add(new User(name, avatar, password, email, phonenum, loginStatus));
        }
        return list;
    }

    /**
     * 根据手机号查询用户信息
     */
    public User getUserByPhoneNum(String phonenum) {
        User user = null;
        @SuppressLint("Recycle") Cursor cursor = db.query("user", null, "phonenum = ?", new String[]{phonenum}, null, null, null);
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String avatar = cursor.getString(cursor.getColumnIndex("avatar"));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("password"));
            @SuppressLint("Range") int loginStatus = cursor.getInt(cursor.getColumnIndex("login_status"));

            user = new User(name, avatar, password, email, phonenum, loginStatus);
        }
        return user;
    }

    /**
     * 查询所有登录状态为1的用户
     */
    public ArrayList<User> getAllLoggedInUsers() {
        ArrayList<User> list = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = db.query("user", null, "login_status = ?", new String[]{"1"}, null, null, "name DESC");
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String avatar = cursor.getString(cursor.getColumnIndex("avatar"));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));
            @SuppressLint("Range") String phonenum = cursor.getString(cursor.getColumnIndex("phonenum"));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("password"));
            @SuppressLint("Range") int loginStatus = cursor.getInt(cursor.getColumnIndex("login_status"));

            list.add(new User(name, avatar, password, email, phonenum, loginStatus));
        }

        return list;
    }

    /**
     * 更新或插入用户信息
     */
    public void upsertUserInfo(String phonenum, String name, String avatar, String work, String qm,String sex) {
        if (isUserInfoExist(phonenum)) {
            updateUserInfo(phonenum, name, avatar, work, qm,sex);
        } else {
            addUserInfo(phonenum, name, avatar, work, qm,sex);
        }
    }

    /**
     * 检查用户信息是否存在
     */
    private boolean isUserInfoExist(String phonenum) {
        @SuppressLint("Recycle") Cursor cursor = db.query("userinfo", null, "phonenum = ?", new String[]{phonenum}, null, null, null);
        return cursor.moveToFirst();
    }

    /**
     * 更新用户信息
     */
    public void updateUserInfo(String phonenum, String name, String avatar, String work,String qm, String sex) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("avatar", avatar);
        values.put("work", work);
        values.put("qm", qm);
        values.put("sex", sex);
        db.update("userinfo", values, "phonenum = ?", new String[]{phonenum});
    }

    /**
     * 添加用户信息
     */
    public void addUserInfo(String phonenum, String name, String avatar, String work,String qm, String sex) {
        ContentValues values = new ContentValues();
        values.put("phonenum", phonenum);
        values.put("name", name);
        values.put("avatar", avatar);
        values.put("work", work);
        values.put("qm", qm);
        values.put("sex", sex);
        db.insert("userinfo", null, values);
    }

    public UserInfo getUserInfoByPhoneNum(String phonenum) {
        UserInfo userInfo = null;
        @SuppressLint("Recycle") Cursor cursor = db.query("userinfo", null, "phonenum = ?", new String[]{phonenum}, null, null, null);
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String avatar = cursor.getString(cursor.getColumnIndex("avatar"));
            @SuppressLint("Range") String work = cursor.getString(cursor.getColumnIndex("work"));
            @SuppressLint("Range") String qm = cursor.getString(cursor.getColumnIndex("qm"));
            @SuppressLint("Range") String sex = cursor.getString(cursor.getColumnIndex("sex"));

            userInfo = new UserInfo(phonenum, name, avatar, work,qm, sex);
        }
        return userInfo;
    }

}
