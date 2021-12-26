package main.java.firstSupl;

import main.java.secSup.secServer;
import main.java.serverPart.BuilderPart.Director;
import main.java.serverPart.BuilderPart.ReserveBuilder;
import main.java.serverPart.Facade;
import main.java.serverPart.Handlers.Helper;
import main.java.serverPart.Objects.Reserve;
import main.java.serverPart.Objects.Restoran;
import main.java.serverPart.Objects.Table;
import main.java.serverPart.dataBase.DataBase;
import main.java.serverPart.dataBase.Request;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import static main.java.serverPart.Handlers.LocalData.getAllSuplReserve;

public class DataProc {
    public static ArrayList<Table> getAllTables(){
        ArrayList<Table> tables = new ArrayList<>();
        try{
            String req = "Select * from Tables_r";
            Connection conn = FrDataBase.connectDB();
            ResultSet result = null;
            Statement stmt = conn.createStatement();
            result = stmt.executeQuery(req);
            while (result.next()) {
                Table tb = new Table();
                tb.from = "First sup";
                tb.id = result.getInt("id");
                tb.status_in_moment = result.getString("status_in_moment");
                tb.description = result.getString("t_description");
                tb.sit = result.getInt("sit");
                tables.add(tb);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return tables;
    }
    public static ArrayList<Reserve> getAllReserves(){
        ArrayList<Reserve> reserves = new ArrayList<>();
        try{
            String req = "Select * from Reserve";
            Connection conn = FrDataBase.connectDB();
            ResultSet result = null;
            Statement stmt = conn.createStatement();
            result = stmt.executeQuery(req);
            ReserveBuilder rb = new ReserveBuilder();
            Director director = new Director(rb);
            while (result.next()) {
                director.innerData.id = result.getInt("id");
                director.innerData.status = result.getString("time_along");
                director.innerData.desc = result.getString("special_description");
                director.innerData.date = result.getString("date_time").toString(); //Timestamp.valueOf(res.getString("date_time").toString().replace("T", " "));
                director.innerData.phone = result.getString("phone_number");
                director.innerData.name = result.getString("full_name");
                director.innerData.amountI = result.getInt("table_id");
                ArrayList<Table> tb = new ArrayList<>();
                for(int i=0; i< frServer.tables.size(); i++){
                    if (frServer.tables.get(i).id == director.innerData.amountI){
                        tb.add(frServer.tables.get(i));
                        break;
                    }
                }
                director.innerData.table = tb;
                director.make("reserve");
                reserves.add(rb.getResult());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return reserves;
    }
    public static void putAllTable() throws SQLException {
        ArrayList<Table> tables = new ArrayList<>();
        for (int i=0; i<2000; i++){
            System.out.println(i);
            Table table = new Table();
            if(Math.random()<0.3)
                table.status_in_moment="take";
            else table.status_in_moment="free";
            table.sit = (int)(Math.random()*11+1);
            table.description = "banana "+i;
            String req = "INSERT INTO Tables_r VALUES ('"+table.status_in_moment+"','"+table.description+"','"+table.sit+"')";
            Connection conn = FrDataBase.connectDB();
            ResultSet result = null;
            Statement stmt = conn.createStatement();
            stmt.execute(req);
        }
    }
    public static String createNewReserve(String query) throws SQLException {
        Map<String,Object> res = Helper.getFromJSON(query);

        ReserveBuilder rb = new ReserveBuilder();
        Director director = new Director(rb);

        director.innerData.name = (String) res.get("full_name");
        director.innerData.phone = (String)res.get("phone");
        director.innerData.date = (String) res.get("date_time");
        director.innerData.status = (String)res.get("time_along");
        director.innerData.desc = (String)res.get("special_description");
        ArrayList<Table> tab = new ArrayList<>();
        tab.add(new Table());
        director.innerData.table = tab;

        director.make("reserve");
        Reserve reserves = rb.getResult();

        String response = "";

        if (reserves.special_description.equals("First sup")){
            String req = "INSERT INTO Reserve VALUES ("+(Integer)res.get("table_id")+",'"+reserves.date_time.toString()+"','"+reserves.time_along+"','"+reserves.phone+"','"+reserves.full_name+"','"+reserves.special_description+"');";
            Connection conn = FrDataBase.connectDB();
            Statement stmt = conn.createStatement();
            stmt.execute(req);
            response = "insert in first sup";
            frServer.reserves.add(reserves);
            if(Facade.firstSup != null){
            Facade.firstSup.reserves.add(reserves);
                Facade.firstSup.reserves = getAllReserves();}
        }
        //Facade.firstSup.reserves = getAllReserves();
        return response;
    }
    public static String deleteReserve(String query){
        try{
            Map<String,Object>res = Helper.getFromJSON(query);
            String response = "";
            int res_id = (Integer) res.get("id");
            String spec = (String) res.get("special_description");
            String req = "DELETE FROM Reserve WHERE id = "+res_id+";";
            Connection conn = FrDataBase.connectDB();
            ResultSet result = null;
            Statement stmt = conn.createStatement();
            result = stmt.executeQuery(req);
            response = result.toString();
            Facade.rest.all_reserves.removeIf(el -> {return el.id == res_id && el.special_description.equals(spec);});
            Facade.firstSup.reserves.removeIf(el -> {return el.id == res_id && el.special_description.equals(spec);});
            frServer.reserves.removeIf(el -> {return el.id == res_id && el.special_description.equals(spec);});
            return response;
        }
        catch(Exception e){
            return e.toString();
        }
    }
}
