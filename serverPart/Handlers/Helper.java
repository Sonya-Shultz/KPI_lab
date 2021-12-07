package main.java.serverPart.Handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

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
}
