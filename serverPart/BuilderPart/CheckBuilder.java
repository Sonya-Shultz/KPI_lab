package main.java.serverPart.BuilderPart;
import java.util.ArrayList;

import main.java.serverPart.Objects.*;

public class CheckBuilder implements Builder {
    Check result;
    public void reset(){ this.result = new Check();};
    public Check getResult(){return this.result;};

    public void setId(int id){this.result.id=id;};
    public void setStatus(String status){this.result.status=status;};
    public void setEmployee(ArrayList<Employee> employee){this.result.employee=employee.get(0);};
    public void setTable(ArrayList<Table> tables){this.result.table=tables.get(0);};
    public void setDate(String date){this.result.date_time=date;};
    public void setFloat(Float amount){this.result.sum=amount;};
    public void setDish(ArrayList<Dish> dish){this.result.dish_list=dish;};
    
    public void setChecks(ArrayList<Check> checks){};
    public void setName(String name){};
    public void setDescription(String description){};
    public void setProduct(ArrayList<Product> product){};
    public void setMenu(ArrayList<Menu> menu){};
    public void setReserve(ArrayList<Reserve> reserve){};
    public void setPersonalData(String name, String email, String phone){}; //can be price
    public void setInt(int amount){};
}
