package com.heineken.database.company;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Volkov on 21.02.2017.
 */


public class DBHelper  extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "company_v1.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_COMPANY = "CREATE TABLE " + Company.TABLE + "("
                + Company.KEY_ID + " ID ,"
                + Company.KEY_name + " TEXT, "
                + Company.KEY_age + " TEXT, "
                + Company.KEY_surname + " TEXT, "
                + Company.KEY_city + " TEXT, "
                + Company.KEY_job + " TEXT )";

        db.execSQL(CREATE_TABLE_COMPANY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
               db.execSQL("DROP TABLE IF EXISTS " + Company.TABLE);
               onCreate(db);

    }
}


