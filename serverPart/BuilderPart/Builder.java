package main.java.serverPart.BuilderPart;

import java.util.ArrayList;

import main.java.serverPart.Objects.*;

public interface Builder {
    public void reset();

    public void setId(int id);
    public void setName(String name);
    public void setStatus(String status);
    public void setDescription(String description);
    public void setDate(String date);
    public void setTable(ArrayList<Table> tables);
    public void setProduct(ArrayList<Product> product);
    public void setDish(ArrayList<Dish> dish);
    public void setMenu(ArrayList<Menu> menu);
    public void setChecks(ArrayList<Check> checks);
    public void setReserve(ArrayList<Reserve> reserve);
    public void setEmployee(ArrayList<Employee> employee);
    public void setPersonalData(String name, String email, String phone);
    public void setFloat(Float amount); //can be price
    public void setInt(int amount);


}
