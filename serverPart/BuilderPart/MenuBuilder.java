package main.java.serverPart.BuilderPart;
import java.util.ArrayList;

import main.java.serverPart.Objects.*;

public class MenuBuilder implements Builder{
    Menu result;
    public void reset(){ this.result = new Menu();};
    public Menu getResult(){return this.result;};

    public void setId(int id){this.result.id=id;};
    public void setStatus(String status){this.result.status=status;};
    public void setDate(String date){this.result.date=date;};
    public void setDish(ArrayList<Dish> dish){this.result.dish=dish.get(0);};

    public void setName(String name){};
    public void setFloat(Float amount){};
    public void setChecks(ArrayList<Check> checks){};
    public void setEmployee(ArrayList<Employee> employee){};
    public void setTable(ArrayList<Table> tables){};
    public void setDescription(String description){};
    public void setProduct(ArrayList<Product> product){};
    public void setMenu(ArrayList<Menu> menu){};
    public void setReserve(ArrayList<Reserve> reserve){};
    public void setPersonalData(String name, String email, String phone){}; //can be price
    public void setInt(int amount){};
}
