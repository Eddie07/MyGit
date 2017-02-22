package com.heineken.database.company;

/**
 * Created by Volkov on 21.02.2017.
 */
import android.app.Dialog;
import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.os.Build;
import android.view.Window;
import android.widget.NumberPicker;
import android.widget.PopupMenu;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class EmployeeDetail extends AppCompatActivity implements android.view.View.OnClickListener{
        Button btnSave ,  btnDelete;
    Button btnClose;
    EditText editTextName;
    EditText editTextSurname;
    EditText editTextAge;
    EditText editTextCity;
    EditText editTextJob;


private int _Employee_Id =0;
    static Dialog d;
    java.util.Calendar c = java.util.Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("yyyy");

    String getyear = df.format(c.getTime());

    int year = Integer.parseInt(getyear);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detail);

//city picker
        editTextCity = (EditText) findViewById(R.id.editTextCity);
        editTextCity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(EmployeeDetail.this, editTextCity);

                popup.getMenuInflater().inflate(R.menu.city_popup, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        editTextCity.setText(item.getTitle());

                        return true;
                    }
                });
                popup.show();
            }
        });


        //job picker
        editTextJob = (EditText) findViewById(R.id.editTextJob);
        editTextJob.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(EmployeeDetail.this, editTextJob);
                popup.getMenuInflater().inflate(R.menu.job_popup, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        editTextJob.setText(item.getTitle());
                        return true;
                    }
                });
                popup.show();
            }
        });


//year picker

        editTextAge = (EditText) findViewById(R.id.editTextAge);
            editTextAge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showYearDialog();
                }
          });

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextSurname = (EditText) findViewById(R.id.editTextSurname);
        editTextJob = (EditText) findViewById(R.id.editTextJob);

        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);


        _Employee_Id =0;
        Intent intent = getIntent();
        _Employee_Id =intent.getIntExtra("employee_Id", 0);
        CompanyRepo repo = new CompanyRepo(this);
        Company company = new Company();
        company = repo.getEmployeeById(_Employee_Id);
        editTextAge.setText(company.age);
        editTextName.setText(company.name);
        editTextSurname.setText(company.surname);
        editTextCity.setText(company.city);
        editTextJob.setText(company.job);

    }


    public void showYearDialog() {
        final Dialog d = new Dialog(EmployeeDetail.this);

        d.setTitle(R.string.year_birth);
        d.setContentView(R.layout.yeardialog);
        Button set = (Button) d.findViewById(R.id.button1);
        Button cancel = (Button) d.findViewById(R.id.button2);
        final NumberPicker nopicker = (NumberPicker) d.findViewById(R.id.numberPicker1);
        nopicker.setMaxValue(year + 50);
        nopicker.setMinValue(year - 50);
        nopicker.setWrapSelectorWheel(false);
        nopicker.setValue(year);
        nopicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextAge.setText(String.valueOf(nopicker.getValue()));
                d.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });

        d.show();
    }

    public void onClick(View view) {
        if (view == findViewById(R.id.btnSave)){
            CompanyRepo repo = new CompanyRepo(this);
            Company company = new Company();
            company.age=editTextAge.getText().toString();
            company.surname=editTextSurname.getText().toString();
            company.name=editTextName.getText().toString();
            company.city=editTextCity.getText().toString();
            company.job=editTextJob.getText().toString();
            company.employee_ID=_Employee_Id;

            if (_Employee_Id==0){
                _Employee_Id= repo.insert(company);

                Toast.makeText(this,this.getString(R.string.created_entry),Toast.LENGTH_SHORT).show();
            }else{

                repo.update(company);
                Toast.makeText(this,this.getString(R.string.updated_entry),Toast.LENGTH_SHORT).show();
            }
        }else if (view== findViewById(R.id.btnDelete)){
            CompanyRepo repo = new CompanyRepo(this);
            repo.delete(_Employee_Id);
            Toast.makeText(this, this.getString(R.string.deleted_entry), Toast.LENGTH_SHORT);
            finish();
        }else if (view== findViewById(R.id.btnClose)){
            finish();

        }


    }


}
