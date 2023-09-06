package com.example.hackapplab;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class local extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME="local.db";

    private static final String LOGGED_STATUS_TABLE="login_status";
    private static final String COLUMN_STATUS="status";
    private static final String COLUMN_EMAIL="email";




    private static final String LIKED_POSTS_TABLE="liked_posts";
    private static final String COLUMN_UID="uid";



    public local(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
