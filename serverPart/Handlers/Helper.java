package main.java.serverPart.Handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import main.java.firstSupl.FrDataBase;
import main.java.serverPart.Objects.Table;
import main.java.serverPart.dataBase.DataBase;

public class Helper {
    public static void handleResponce(HttpExchange httpExchange, Object data)throws  IOException{
        try{
            String ans = Helper.getJSON(data);
            httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            httpExchange.sendResponseHeaders(200, ans.getBytes().length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(ans.getBytes());
            outputStream.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
    public static String getJSON(Object result){
        String ans="";
        ObjectMapper mapper = new ObjectMapper();
            try {
                String json = mapper.writeValueAsString(result);
            return json;
            } catch (Exception e) {
            e.printStackTrace();
            }
        return ans;
    }
    public static String parseRequest(HttpExchange t) throws IOException{
        InputStreamReader isr = new InputStreamReader(t.getRequestBody(),"utf-8");
        BufferedReader br = new BufferedReader(isr);
        int b;
        StringBuilder buf = new StringBuilder(512);
        while ((b = br.read()) != -1) {
            buf.append((char) b);
        }
        br.close();
        isr.close();
        String query = buf.toString();
        return query;
    }
    public static Map<String,Object> getFromJSON(String data){
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = new HashMap<>();
        try {
            map = mapper.readValue(data, new TypeReference<Map<String, Object>>(){});
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return map;
        }
    }
    public static ArrayList<Object> getArrListFromJSON(String data){
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Object> map = new ArrayList<>();
        try{
            map = mapper.readValue(data, new TypeReference<ArrayList<Object>>() {});
        }catch(Exception e){
            e.printStackTrace();
        }
        return map;
    }
    public static String handleGetRequest(HttpExchange httpExchange) {
        try{
            return httpExchange.
                    getRequestURI()
                    .toString()
                    .split("=")[1]
                    .toString();}
        catch(Exception exception){
            return exception.toString();
        }
    }
    public static void putAllTable() throws SQLException {
        ArrayList<Table> tables = new ArrayList<>();
        for (int i=0; i<100000; i++){
            System.out.println(i);
            Table table = new Table();
            if(Math.random()<0.3)
                table.status_in_moment="take";
            else table.status_in_moment="free";
            table.sit = (int)(Math.random()*11+1);
            table.description = "just table "+i;
            String req = "INSERT INTO Tables_r VALUES ('"+table.status_in_moment+"','"+table.description+"','"+table.sit+"')";
            Connection conn = DataBase.connectDB();
            ResultSet result = null;
            Statement stmt = conn.createStatement();
            stmt.execute(req);
        }
    }
}
