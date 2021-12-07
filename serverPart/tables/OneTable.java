package main.java.serverPart.tables;

public class OneTable {
    String status_in_moment = "free";
    String description = "";
    int size = 3;
    int id = 0;

    public OneTable setTable(String st, String des, int size, int id){
        this.description = des;
        this.size = size;
        this.status_in_moment = st;
        this.id = id;
        return this;
    };

}
