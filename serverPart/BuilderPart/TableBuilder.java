package main.java.serverPart.BuilderPart;
import java.util.ArrayList;

import main.java.serverPart.Objects.*;

public class TableBuilder implements Builder {
    Table result;
    public void reset(){ this.result = new Table();};
    public Table getResult(){return this.result;};

    public void setId(int id){this.result.id=id;};
    public void setStatus(String status){this.result.status_in_moment=status;};
    public void setDescription(String description){this.result.description=description;};
    public void setName(String name){this.result.from = name;};
    public void setInt(int amount){this.result.sit=amount;};

    public void setDate(String date){};
    public void setTable(ArrayList<Table> tables){};
    public void setChecks(ArrayList<Check> checks){};
    public void setProduct(ArrayList<Product> product){};
    public void setDish(ArrayList<Dish> dish){};
    public void setMenu(ArrayList<Menu> menu){};
    public void setReserve(ArrayList<Reserve> reserve){};
    public void setEmployee(ArrayList<Employee> employee){};
    public void setPersonalData(String name, String email, String phone){};
    public void setFloat(Float amount){}; //can be price
}
