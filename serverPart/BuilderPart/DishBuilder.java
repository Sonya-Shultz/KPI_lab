package main.java.serverPart.BuilderPart;
import java.util.ArrayList;

import main.java.serverPart.Objects.*;

public class DishBuilder implements Builder{
    Dish result;
    public void reset(){ this.result = new Dish();};
    public Dish getResult(){return this.result;};

    public void setId(int id){this.result.id=id;};
    public void setName(String name){this.result.name=name;};
    public void setStatus(String status){this.result.dish_type=status;};
    public void setDescription(String description){this.result.description = description;};
    public void setFloat(Float amount){this.result.price=amount;};
    public void setProduct(ArrayList<Product> product){this.result.products_list=product;};

    public void setDate(String date){};
    public void setChecks(ArrayList<Check> checks){};
    public void setPersonalData(String name, String email, String phone){};
    public void setTable(ArrayList<Table> tables){};
    public void setDish(ArrayList<Dish> dish){};
    public void setEmployee(ArrayList<Employee> employee){};
    public void setMenu(ArrayList<Menu> menu){};
    public void setReserve(ArrayList<Reserve> reserve){}; //can be price
    public void setInt(int amount){};
}
