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
    int _id;
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

//        holder.empname.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try{
//                    final Dao<Employee, Integer> techerDao1 = getHelper().getEmpdao();
//                    String s1 = techerDao1.queryForId(position).toString();
//                    System.out.println("positon   "+s1);
//                }
//                catch(SQLException se){
//                    se.printStackTrace();
//                }
//
//
//            }
//        });

        holder.img_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    final Dao<Employee, Integer> techerDao1 = getHelper().getEmpdao();
                    int _id = teacherDetails.getId();
                    techerDao1.deleteById(_id);

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
                Employee user = getItem(position);
                System.out.println(" dadad "+user);






//               try{
//                final Dao<Employee, Integer> techerDao3 = getHelper().getEmpdao();

//                    System.out.println("position"+position);
//                    teacherDetails.getId();


//                    final Dao<Employee, Integer> techerDao3 = getHelper().getEmpdao();
//                   _id  = teacherDetails.getId();
//                    emp_name = teacherDetails.getEmp_Name();
//                    emp_dsg = teacherDetails.getEmp_Desig();
//                    System.out.println("before call dialog  id   " +_id);
//                    System.out.println("before call dialog  emp   " +emp_name);
//
//                    Dialog(_id,emp_name,emp_dsg);

//                    techerDao1.deleteById(_id);
//                    getContext().startActivity(new Intent(getContext(), SecondActivity.class));
//                }
//                catch(SQLException se){
//                    se.printStackTrace();
//                }
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


    public void Dialog(final int empid, final String emp_name, final String emp_dsg) {

        System.out.println("in dialog  id   " +empid);
        System.out.println("in dialog  emp   " +emp_name);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Update");
        final EditText input = new EditText(getContext());
        alert.setView(input);

        input.setText(emp_name);
        System.out.println("input  emp   " +input);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                try{ final Dao<Employee, Integer> techerDao2 = getHelper().getEmpdao();
                    String srt = input.getEditableText().toString();
                    System.out.println("string s1  emp   " +srt);
                    System.out.println("onclick dialog  id   " +empid);
                    teacherDetails.setEmp_Name(srt);

                    techerDao2.update(teacherDetails);
                    input.setText("");
                    String ss = techerDao2.queryForId(empid).toString();
                    System.out.println("Emp data after update  " +ss);

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








}