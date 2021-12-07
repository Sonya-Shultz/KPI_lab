package main.java.serverPart.BuilderPart;
import java.util.ArrayList;

import main.java.serverPart.Objects.*;

public class Director {
    Builder builder;
    public InnerData innerData;
    public Director(Builder builder){
        this.builder=builder;
        innerData=new InnerData();
    }
    public void changeBuilder(Builder builder){
        this.builder=builder;
    }
    public class InnerData{
        public int id=-1;
        public String name=null;
        public String status=null;
        public String desc=null;
        public String email=null;
        public String phone=null;
        public ArrayList<Product> product=null;
        public ArrayList<Table> table=null;
        public ArrayList<Dish> dish=null;
        public ArrayList<Menu> menu=null;
        public ArrayList<Employee> empl=null;
        public ArrayList<Reserve> reserve=null;
        public ArrayList<Check> checks=null;
        public String date = null;
        public Float amountF = (float) 0;
        public int amountI = 0;
    }
    public void make(String type){
        builder.reset();
        builder.setId(this.innerData.id);
        builder.setName(this.innerData.name);
        builder.setStatus(this.innerData.status);
        builder.setDescription(this.innerData.desc);
        builder.setDate(this.innerData.date);
        builder.setTable(this.innerData.table);
        builder.setProduct(this.innerData.product);
        builder.setDish(this.innerData.dish);
        builder.setMenu(this.innerData.menu);
        builder.setReserve(this.innerData.reserve);
        builder.setChecks(this.innerData.checks);
        builder.setEmployee(this.innerData.empl);
        builder.setPersonalData(this.innerData.name, this.innerData.email, this.innerData.phone);
        builder.setFloat(this.innerData.amountF);
        builder.setInt(this.innerData.amountI);
    }
    
}
