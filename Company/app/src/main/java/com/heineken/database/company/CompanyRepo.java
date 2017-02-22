package com.heineken.database.company;

/**
 * Created by Volkov on 21.02.2017.
 */
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class CompanyRepo {
    private DBHelper dbHelper;

    public CompanyRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(Company company) {

        //Open connection
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Company.KEY_age, company.age);
        values.put(Company.KEY_surname, company.surname);
        values.put(Company.KEY_name, company.name);
        values.put(Company.KEY_city, company.city);
        values.put(Company.KEY_job, company.job);
        // Inserting Row
        long employee_Id = db.insert(Company.TABLE, null, values);
        db.close(); // Closing database
        return (int) employee_Id;
    }

    public void delete(int employee_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
               db.delete(Company.TABLE, Company.KEY_ID + "= ?", new String[]{String.valueOf(employee_Id)});
        db.close(); // Closing database
    }

    public void update(Company company) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Company.KEY_age, company.age);
        values.put(Company.KEY_surname, company.surname);
        values.put(Company.KEY_name, company.name);
        values.put(Company.KEY_city, company.city);
        values.put(Company.KEY_job, company.job);


        db.update(company.TABLE, values, Company.KEY_ID + "= ?", new String[]{String.valueOf(company.employee_ID)});
        db.close(); // Closing database
    }

    public ArrayList<HashMap<String, String>> getEmployeeList() {
        //read
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Company.KEY_ID + "," +
                Company.KEY_name + "," +
                Company.KEY_surname + "," +
                Company.KEY_age + "," +
                Company.KEY_city + "," +
                Company.KEY_job +
                " FROM " + Company.TABLE;


        ArrayList<HashMap<String, String>> companyList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int diff =0;



        if (cursor.moveToFirst()) {
            do {

          if (cursor.getString(cursor.getColumnIndex(Company.KEY_age))!= null && !cursor.getString(cursor.getColumnIndex(Company.KEY_age)).isEmpty())  {
             int yearofbith=Integer.parseInt(cursor.getString(cursor.getColumnIndex(Company.KEY_age)));
                  diff = year - Integer.parseInt(cursor.getString(cursor.getColumnIndex(Company.KEY_age)));
                }
                HashMap<String, String> company = new HashMap<String, String>();
                company.put("id", cursor.getString(cursor.getColumnIndex(Company.KEY_ID)));
                company.put("name",  cursor.getString(cursor.getColumnIndex(Company.KEY_name)));
                company.put("surname", cursor.getString(cursor.getColumnIndex(Company.KEY_surname)));
                company.put("age",Integer.toString(diff));
                company.put("city", cursor.getString(cursor.getColumnIndex(Company.KEY_city)));
                companyList.add(company);
                diff=0;

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return companyList;

    }

    public Company getEmployeeById(int Id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Company.KEY_ID + "," +
                Company.KEY_name + "," +
                Company.KEY_surname + "," +
                Company.KEY_age + "," +
                Company.KEY_city + "," +
                Company.KEY_job +
                " FROM " + Company.TABLE
                + " WHERE " +
                Company.KEY_ID + "=?";

        int iCount = 0;
        Company company = new Company();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(Id)});

        if (cursor.moveToFirst()) {
            do {
                company.employee_ID= cursor.getInt(cursor.getColumnIndex(Company.KEY_ID));
                company.name = cursor.getString(cursor.getColumnIndex(Company.KEY_name));
                company.surname = cursor.getString(cursor.getColumnIndex(Company.KEY_surname));
                company.age = cursor.getString(cursor.getColumnIndex(Company.KEY_age));
                company.city = cursor.getString(cursor.getColumnIndex(Company.KEY_city));
                company.job = cursor.getString(cursor.getColumnIndex(Company.KEY_job));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return company;
    }
}
