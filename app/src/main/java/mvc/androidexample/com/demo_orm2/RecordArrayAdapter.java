package mvc.androidexample.com.demo_orm2;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import static android.R.attr.password;

@SuppressWarnings("rawtypes")
public class RecordArrayAdapter extends ArrayAdapter<Employee>
{
    private LayoutInflater inflater;
    private List records;
    private Emp_DatabaseHelper empdatabase;
    String emp_name,emp_dsg;

    private Dao<Employee, Integer> teacherDao;
    private Employee teacherDetails;

    @SuppressWarnings("unchecked")
    public RecordArrayAdapter(Context context, int resource, List objects, Dao<Employee, Integer> teacherDao)
    {
        super(context, resource, objects);

        this.records = objects;
        this.teacherDao = teacherDao;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }




    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        UserHolder holder = null;

        if(convertView == null)
            convertView = inflater.inflate(R.layout.listitems, parent, false);
        holder = new UserHolder();

        teacherDetails = (Employee) records.get(position);
        holder.empname = (TextView)convertView.findViewById(R.id.txt_name);
        holder.empdsg = (TextView)convertView.findViewById(R.id.txt_desg);
        holder.img_del = (ImageView)convertView.findViewById(R.id.img_del);
        holder.img_upd = (ImageView)convertView.findViewById(R.id.img_pen);

        holder.empname.setText(teacherDetails.Emp_Name);
        holder.empdsg.setText(teacherDetails.Emp_Desig);
        holder.img_del.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try{
                    teacherDao = getHelper().getEmpdao();
                    teacherDetails = getItem(position);
                    int _id = teacherDetails.getId();
                    teacherDao.deleteById(_id);

                    getContext().startActivity(new Intent(getContext(), SecondActivity.class));
                    ((Activity)getContext()).finish();
                }
                catch(SQLException se){
                    se.printStackTrace();
                }
            }

        });

        holder.img_upd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {



                teacherDetails = getItem(position);

                System.out.println("    "+teacherDetails.toString() );
               int eid = teacherDetails.getId();
                String sname =teacherDetails.getEmp_Name();
                System.out.println("    "+ sname +"    "+eid);
               Dialog(sname);
            }
        });
        return convertView;
    }

    private Emp_DatabaseHelper getHelper()
    {
        if (empdatabase == null)
        {
            empdatabase = OpenHelperManager.getHelper(getContext(),Emp_DatabaseHelper.class);
        }
        return empdatabase;
    }

    static class UserHolder {
        TextView empname;
        TextView empdsg;
        ImageView img_del,img_upd;
    }


    public void Dialog( String ename ) {

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Update");
        final EditText input = new EditText(getContext());
        alert.setView(input);

        input.setText(ename);

        alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {



                try{ teacherDao = getHelper().getEmpdao();
                    String srt = input.getEditableText().toString();

                    teacherDetails.setEmp_Name(srt);
                    updateStudent(teacherDetails);

                    getContext().startActivity(new Intent(getContext(), SecondActivity.class));
                    ((Activity)getContext()).finish();

                    input.setText("");
                }
                catch(SQLException se){

                }
            }
        });

        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alert.create();
        alertDialog.show();

    }

    public void updateStudent(Employee student) {

        System.out.println(""+student.toString());
        try {
            empdatabase.createOrUpdate(student);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}