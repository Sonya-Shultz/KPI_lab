package main.java.firstSupl;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.serverPart.Handlers.Helper;
import main.java.serverPart.Objects.Reserve;
import main.java.serverPart.Objects.Table;
import main.java.serverPart.dataBase.DataBase;

import java.io.IOException;
import java.util.ArrayList;

public class Handlers {

    public static class TablesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            t.getResponseHeaders().add("Access-Control-Allow-Headers","Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
            t.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE, UPDATE");
            if("GET".equals(t.getRequestMethod())){
                try{
                    ArrayList<Table> tables = DataProc.getAllTables();
                    Thread.sleep(25000);
                    Helper.handleResponce(t,tables);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else{Helper.handleResponce(t,"technical massage");}
        }
    }
    public static class ReservesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            t.getResponseHeaders().add("Access-Control-Allow-Headers","Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
            t.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE, UPDATE");
            if("GET".equals(t.getRequestMethod())){
                try{
                    ArrayList<Reserve> tables = DataProc.getAllReserves();
                    Helper.handleResponce(t,tables);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else if("POST".equals(t.getRequestMethod())){
                try{
                    String query = Helper.parseRequest(t);
                    String all_data =  DataProc.createNewReserve(query);
                    Helper.handleResponce(t,all_data);
                    FrDataBase.closeConnection();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else if("DELETE".equals(t.getRequestMethod())){
                try{
                    String query = Helper.parseRequest(t);
                    String all_data =  DataProc.deleteReserve(query);
                    Helper.handleResponce(t,all_data);
                    FrDataBase.closeConnection();

                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            else{Helper.handleResponce(t,"technical massage");}
        }
    }



}
