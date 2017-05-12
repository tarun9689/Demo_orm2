package mvc.androidexample.com.demo_orm2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by tarunsharma on 9/5/17.
 */

public class SecondActivity extends AppCompatActivity {

    TextView unmae, udesig;
    private Emp_DatabaseHelper empdatabase;
    private ListView listview;
    Button btn_clr;
    List<Employee> notess;
    Dao<Employee, Integer> techerDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondacivity);

        listview = (ListView) findViewById(R.id.listview);
        btn_clr = (Button) findViewById(R.id.btn_clr);
        try {
            techerDao = getHelper().getEmpdao();

            notess = techerDao.queryForAll();



        } catch (SQLException se) {
            se.printStackTrace();
        }

        listview.setAdapter(new RecordArrayAdapter(this, R.layout.listitems, notess, techerDao));
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//               String ss= listview.getItemAtPosition(position).toString();
//                System.out.println("    "+ss);
//            }
//        });



    }

    private Emp_DatabaseHelper getHelper()
    {
        if (empdatabase == null)
        {
            empdatabase = OpenHelperManager.getHelper(this,Emp_DatabaseHelper.class);
        }
        return empdatabase;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (empdatabase != null)
        {
            OpenHelperManager.releaseHelper();
            empdatabase = null;
        }
    }


}
