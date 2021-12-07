package main.java.serverPart.dataBase;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;

public class Request {
    private String request;
    public Request(String request){
        this.request = request;
    }
    public Object exec(){
        BasicRequest br = new BasicRequest();
        GetRequest gr = new GetRequest();
        InsertRequest ir = new InsertRequest();
        UpdateRequest ur = new UpdateRequest();
        DeleteRequest dr = new DeleteRequest();
        br.setNext(gr);
        gr.setNext(ir);
        ir.setNext(ur);
        ur.setNext(dr);
        Object obj = br.handler(get_type(request), request);
        return obj;
    }
    private String get_type (String req){
        String type = req.split(" ")[0].toUpperCase();
        return type;
    }
    public interface HandlerRequest {
        public void setNext(HandlerRequest h);
        public Object handler(String type, String request);
    }

    public class BasicRequest implements HandlerRequest{
        public HandlerRequest next;
        public void setNext(HandlerRequest h){next=h;};
        public Object handler(String type, String request){
            if (next!=null){
                Object ans = next.handler(type, request);
                return ans;
            }
            return "Inncorect request";
        };
    }

    public class GetRequest extends BasicRequest{
        public Object handler(String type, String request){
            if (type.equals("SELECT")){

                try {
                    Connection conn = DataBase.connectDB();
                    ResultSet result = null;
                    Statement stmt = conn.createStatement();
                    result = stmt.executeQuery(request);
                    return result;
                }
                catch (Exception e) {
                    e.printStackTrace();
                    return "Error select";
                }
            }
            else{
                Object ans = next.handler(type, request);
                return ans;
            }
        };
    }
    public class InsertRequest extends BasicRequest{
        public Object handler(String type, String request){
            if (type.equals("INSERT")){
                try {
                    Connection conn = DataBase.connectDB();
                    conn.prepareStatement(request).execute();
                    return "InsertData";
                }
                catch (Exception e) {
                    e.printStackTrace();
                    return "Erroe Insert";
                }
            }
            else{
                Object ans = next.handler(type, request);
                return ans;
            }
        };
    }
    public class UpdateRequest extends BasicRequest{
        public Object handler(String type, String request){
            if (type.equals("UPDATE")){
                try {
                    Connection conn = DataBase.connectDB();
                    conn.prepareStatement(request).execute();
                    return "Update Data";
                }
                catch (Exception e) {
                    e.printStackTrace();
                    return "Error Update";
                }
            }
            else{
                Object ans = next.handler(type, request);
                return ans;
            }
        };
    }
    public class DeleteRequest extends BasicRequest{
        public Object handler(String type, String request){
            try {
                Connection conn = DataBase.connectDB();
                conn.prepareStatement(request).execute();
                return "Delete data";
            }
            catch (Exception e) {
                e.printStackTrace();
                return "Error delete data";
            }
        };
    }
}
