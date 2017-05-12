package mvc.androidexample.com.demo_orm2;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by tarunsharma on 8/5/17.
 */

public class Employee {

    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField
    String Emp_Name;

    @DatabaseField
    String Emp_Desig;

    public Employee (){

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmp_Name() {
        return Emp_Name;
    }

    public void setEmp_Name(String Emp_Name) {
        this.Emp_Name = Emp_Name;
    }

    public String getEmp_Desig() {
        return Emp_Desig;
    }

    public void setEmp_Desig(String Emp_Desig) {
        this.Emp_Desig = Emp_Desig;
    }


    public  Employee(String Emp_Name, String Emp_Desig)
    {
        super();
        this.Emp_Name = Emp_Name;
        this.Emp_Desig = Emp_Desig;
    }

    public  void  setEmployeeData( int id, String Emp_Name, String Emp_Desig)
    {

        this.id = id;
        this.Emp_Name = Emp_Name;
        this.Emp_Desig = Emp_Desig;
    }

    @Override
    public String toString()
    {
        return "Employee[id= " + id +", Emp_Name= " + Emp_Name +", Emp_Desig=" + Emp_Desig +"]";
    }
}
