package main.java.serverPart.BuilderPart;
import java.util.ArrayList;

import main.java.serverPart.Objects.*;

public class ReserveBuilder implements Builder{
    Reserve result;
    public void reset(){ this.result = new Reserve();};
    public Reserve getResult(){return this.result;};

    public void setId(int id){this.result.id=id;};
    public void setStatus(String status){this.result.time_along=status;};
    public void setDescription(String description){this.result.special_description = description;};
    public void setDate(String date){this.result.date_time=date;};
    public void setPersonalData(String name, String email, String phone){
        this.result.full_name = name;
        this.result.phone = phone;
    };
    public void setTable(ArrayList<Table> tables){this.result.table=tables.get(0);};

    public void setName(String name){};
    public void setDish(ArrayList<Dish> dish){};
    public void setFloat(Float amount){};
    public void setChecks(ArrayList<Check> checks){};
    public void setEmployee(ArrayList<Employee> employee){};
    public void setProduct(ArrayList<Product> product){};
    public void setMenu(ArrayList<Menu> menu){};
    public void setReserve(ArrayList<Reserve> reserve){}; //can be price
    public void setInt(int amount){};
}
