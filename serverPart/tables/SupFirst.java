package main.java.serverPart.tables;
import java.util.ArrayList;

import main.java.serverPart.Objects.Table;
import main.java.serverPart.BuilderPart.*;

public class SupFirst {

    public ArrayList<Table> return_all_list(){
        
        TableBuilder tb = new TableBuilder();
        Director director = new Director(tb);
        director.innerData.name = "Comp1";
        ArrayList<Table> all_data =  new ArrayList<>();
        ArrayList<OneTable> all_list = this.create_list();
        all_list.forEach((el)->{
            director.innerData.id=el.id;
            director.innerData.status=el.status_in_moment;
            director.innerData.amountI=el.size;
            director.make("table");
            all_data.add(tb.getResult());
        });
        return all_data;
    } ;

    public Table return_details(int id){
        Table all_data = null;
        TableBuilder tb = new TableBuilder();
        Director director = new Director(tb);
        director.innerData.name = "Comp1";
        ArrayList<OneTable> all_list = this.create_list();
        for (int i=0; i<all_list.size(); i++){
            OneTable el = all_list.get(i);
            if (el.id == id){
                director.innerData.id=el.id;
                director.innerData.status=el.status_in_moment;
                director.innerData.amountI=el.size;
                director.innerData.desc=el.description;
                director.make("table");
                return tb.getResult();
            }
        }
        return all_data;
    } ;

    private ArrayList<OneTable> create_list() {
        ArrayList<OneTable> table_list = new ArrayList<>();
        table_list.add(new OneTable().setTable("free", "good table", 4, 1));
        table_list.add(new OneTable().setTable("free", "not good table", 2, 2));
        table_list.add(new OneTable().setTable("free", "good table", 4, 3));
        table_list.add(new OneTable().setTable("take", "just table", 1, 4));
        table_list.add(new OneTable().setTable("free", "good table", 3, 5));
        table_list.add(new OneTable().setTable("take", "fake table", 7, 6));
        table_list.add(new OneTable().setTable("take", "good table", 10, 7));
        table_list.add(new OneTable().setTable("free", "not a table at all", 4, 8));
        table_list.add(new OneTable().setTable("free", "banana", 5, 9));
        return table_list;
    };
}
