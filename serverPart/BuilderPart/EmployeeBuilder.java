package main.java.serverPart.BuilderPart;
import java.util.ArrayList;

import main.java.serverPart.Objects.*;

public class EmployeeBuilder implements Builder{
    Employee result;
    public void reset(){ this.result = new Employee();};
    public Employee getResult(){return this.result;};

    public void setId(int id){this.result.id=id;};
    public void setPersonalData(String name, String email, String phone){
        this.result.email=email;
        this.result.full_name=name;
        this.result.phone_number=phone;
    };
    public void setStatus(String status){this.result.position=status;};
    public void setDescription(String description){this.result.password = description;};

    public void setDate(String date){};
    public void setFloat(Float amount){};
    public void setChecks(ArrayList<Check> checks){};
    public void setName(String name){};
    public void setProduct(ArrayList<Product> product){};
    public void setTable(ArrayList<Table> tables){};
    public void setDish(ArrayList<Dish> dish){};
    public void setEmployee(ArrayList<Employee> employee){};
    public void setMenu(ArrayList<Menu> menu){};
    public void setReserve(ArrayList<Reserve> reserve){}; //can be price
    public void setInt(int amount){};
}
