package mvc.androidexample.com.demo_orm2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by tarunsharma on 8/5/17.
 */

public class Emp_DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DatabaseName = "employee_data.db";
    private static final int DatabaseVersion = 1;

    private Dao<Employee, Integer> empDao = null;
    private RuntimeExceptionDao<Employee, Integer> empRuntimeExceptionDao = null;

    public Emp_DatabaseHelper(Context context){
        super(context, DatabaseName, null,DatabaseVersion, R.raw.ormlite_config);
    }

    public void onCreate(SQLiteDatabase Sqdb, ConnectionSource connectionSource){
        try
        {

            TableUtils.createTable(connectionSource, Employee.class);
        }

        catch (SQLException se)
        {
            se.printStackTrace();
        }
    }

    public void onUpgrade(SQLiteDatabase sqdb, ConnectionSource connectionSource, int OldVersion, int NewVersion){

        try{
            TableUtils.dropTable(connectionSource, Employee.class,true);
            onCreate(sqdb,connectionSource);
        }
        catch(SQLException se){
            se.printStackTrace();
        }
    }

    public Dao<Employee, Integer> getEmpdao() throws SQLException{
        if(empDao == null)
        {
            empDao = getDao(Employee.class);
        }
        return empDao;
    }

    public RuntimeExceptionDao<Employee, Integer> getEmpRuntimeExceptionDao() {
        if(empRuntimeExceptionDao == null){
            empRuntimeExceptionDao = getRuntimeExceptionDao(Employee.class);
        }
        return empRuntimeExceptionDao;
    }

    public Dao.CreateOrUpdateStatus createOrUpdate(Employee obj) throws SQLException {
        Dao<Employee, ?> dao = (Dao<Employee, ?>) getDao(obj.getClass());
        return dao.createOrUpdate(obj);
    }


}
