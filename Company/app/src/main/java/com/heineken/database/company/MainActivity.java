package com.heineken.database.company;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
        import android.support.v7.app.ActionBar;
        import android.support.v4.app.Fragment;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.os.Build;
        import android.widget.AdapterView;
        import android.widget.Button;
        import android.widget.ListAdapter;
        import android.widget.ListView;
        import android.widget.SimpleAdapter;
        import android.widget.TextView;
        import android.widget.Toast;
        import java.util.ArrayList;
        import java.util.HashMap;


public class MainActivity extends ListActivity implements android.view.View.OnClickListener {

    Button btnAdd, btnGetAll;
    TextView employee_Id;
    CompanyRepo repo = new CompanyRepo(this);


    @Override
    public void onClick(View view) {
        if (view == findViewById(R.id.btnAdd)) {

            Intent intent = new Intent(this, EmployeeDetail.class);
            intent.putExtra("employee_Id", 0);
            startActivity(intent);
            RepoActivity();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RepoActivity();

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

    }

    private void RepoActivity() {
        CompanyRepo repo = new CompanyRepo(this);
        ArrayList<HashMap<String, String>> employeeList = repo.getEmployeeList();
        ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                employee_Id = (TextView) view.findViewById(R.id.employee_Id);
                String employeeId = employee_Id.getText().toString();
                Intent objIndent = new Intent(getApplicationContext(), EmployeeDetail.class);
                objIndent.putExtra("employee_Id", Integer.parseInt(employeeId));
                startActivity(objIndent);
            }
        });
        ListAdapter adapter = new SimpleAdapter(MainActivity.this, employeeList, R.layout.view_employee_entry, new String[]{"id","name", "surname", "age", "city"}, new int[]{R.id.employee_Id, R.id.employee_name, R.id.employee_surname, R.id.employee_age, R.id.employee_city});
        setListAdapter(adapter);

            if (employeeList.size()== 0) {
                        Toast.makeText(this, this.getString(R.string.list_empty), Toast.LENGTH_SHORT).show();
            }
    }
        protected void onResume() {
            super.onResume();
        RepoActivity();
    }

    }








