package main.java.serverPart.tables;

import java.util.ArrayList;

import main.java.serverPart.BuilderPart.*;
import main.java.serverPart.Objects.Table;

public class SupSec {
    public ArrayList<Table> return_all_list(String filter){
        ArrayList<Table> all_data =  new ArrayList<>();
        ArrayList<OneTable> all_list = this.create_list();
        TableBuilder tb = new TableBuilder();
        Director director = new Director(tb);
        director.innerData.name = "Comp2";
        all_list = this.create_filter(filter, all_list);
        all_list.forEach((el)->{
            director.innerData.id=el.id;
            director.innerData.status=el.status_in_moment;
            director.innerData.amountI=el.size;
            director.innerData.desc=el.description;
            director.make("table");
            all_data.add(tb.getResult());
        });
        return all_data;
    };

    private ArrayList<OneTable> create_filter(String filter, ArrayList<OneTable> all_list){
        String[] stat = filter.split("status");
        if(stat.length > 1){
            String status = (stat[1]).substring(1);
            String[] helps = status.split("&");
            if (helps.length > 1){ status = helps[0].replace("&", "");}
            all_list = this.filter_by_status(status, all_list);
        }
        String[] am = filter.split("sit");
        if(am.length > 1){
            String amount = (am[1]).substring(1);
            String[] helps = amount.split("&");
            if (helps.length > 1){amount = helps[0].replace("&", "");}
            int amo = Integer.parseInt(amount);
            all_list = this.filter_by_amount(amo, all_list);
        }
        return all_list;
    }

    private ArrayList<OneTable> filter_by_status(String status, ArrayList<OneTable> all_list){
        all_list.removeIf(el->(!el.status_in_moment.equals(status)));
        return all_list;
    }
    private ArrayList<OneTable> filter_by_amount(int amount, ArrayList<OneTable> all_list){
        all_list.removeIf(el->(el.size != amount));
        return all_list;
    }

    private ArrayList<OneTable> create_list() {
        ArrayList<OneTable> table_list = new ArrayList<>();
        table_list.add(new OneTable().setTable("take", "good table", 4, 1));
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
