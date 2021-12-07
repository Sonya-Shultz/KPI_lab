package main.java.serverPart.BuilderPart;
import java.util.ArrayList;

import main.java.serverPart.Objects.*;

public class RestoranBuilder implements Builder{
    Restoran result;
    public void reset(){ this.result = new Restoran();};
    public Restoran getResult(){return this.result;};

    public void setPersonalData(String name, String email, String phone){};
    public void setStatus(String status){this.result.status_in_moment=status;};
    public void setProduct(ArrayList<Product> product){this.result.all_products = product;};
    public void setTable(ArrayList<Table> tables){this.result.all_tables = tables;};
    public void setDish(ArrayList<Dish> dish){this.result.all_dishes = dish;};
    public void setEmployee(ArrayList<Employee> employee){this.result.all_employees = employee;};
    public void setMenu(ArrayList<Menu> menu){this.result.today_menu = menu;};
    public void setReserve(ArrayList<Reserve> reserve){this.result.all_reserves = reserve;};
    public void setChecks(ArrayList<Check> checks){this.result.all_checks = checks;};

    public void setId(int id){};
    public void setDate(String date){};
    public void setDescription(String description){};
    public void setFloat(Float amount){};
    public void setName(String name){}; //can be price
    public void setInt(int amount){};
}
