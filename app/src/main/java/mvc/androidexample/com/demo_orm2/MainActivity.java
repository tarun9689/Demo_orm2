package mvc.androidexample.com.demo_orm2;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Emp_DatabaseHelper emp_database  = null;

    EditText edt_uname, edt_udesig;
    Button btn_sub, btn_clr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_uname = (EditText)findViewById(R.id.edt_name);
        edt_udesig = (EditText)findViewById(R.id.edt_desig);

        btn_sub = (Button)findViewById(R.id.btn_sub);
        btn_clr  =(Button)findViewById(R.id.btn_clr);

        btn_sub.setOnClickListener(this);

        btn_clr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emp_intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(emp_intent);


            }
        });
    }

    private Emp_DatabaseHelper getHelper()
    {
        if (emp_database == null)
        {
            emp_database = OpenHelperManager.getHelper(this,Emp_DatabaseHelper.class);
        }
        return emp_database;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (emp_database != null)
        {
            OpenHelperManager.releaseHelper();
            emp_database = null;
        }
    }

    public void onClick(View v)
    {
        if(edt_uname.getText().toString().trim().length() > 0 && edt_udesig.getText().toString().trim().length() > 0)
        {
            final Employee techDetails = new Employee();
            techDetails.Emp_Name  = edt_uname.getText().toString();
            techDetails.Emp_Desig  = edt_udesig.getText().toString();

            try
            {
                final Dao<Employee, Integer> techerDao = getHelper().getEmpdao();

                techerDao.create(techDetails);



            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }

            edt_uname.setText(""); edt_udesig.setText("");
        }
        else
        {
            showMessageDialog("All fields are mandatory !!");
        }

    }

    private void showMessageDialog(final String message)
    {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
