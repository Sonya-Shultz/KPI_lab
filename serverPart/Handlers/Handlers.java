package main.java.serverPart.Handlers;

import java.io.*;
import java.lang.reflect.Executable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import main.java.serverPart.Facade;
import main.java.serverPart.Objects.*;
import main.java.serverPart.dataBase.DataBase;
import main.java.serverPart.tables.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class Handlers{
    public static class PriceListHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "respone for all price";
            SupFirst ans = new SupFirst();
            List<Table> data = ans.return_all_list();
            response = createResponce(data);
            t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
        public String createResponce(List<Table> data){
            try{
                String ans = Helper.getJSON(data);
                return ans;
            }
            catch(Exception e){
                return e.toString();
            }
        }
    }
    public static class DetailsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String requestParamValue=null;
            requestParamValue = handleGetRequest(t);
            int id = -1;
            try{
            id = Integer.parseInt(requestParamValue);}
            catch(Exception e){}
            SupFirst ans = new SupFirst();
            Table data = ans.return_details(id);
            Helper.handleResponce(t,data);
        }
        private String handleGetRequest(HttpExchange httpExchange) {
            try{
                return httpExchange.
                        getRequestURI()
                        .toString()
                        .split("/")[2]
                        .toString();}
            catch(Exception exception){
                return exception.toString();
            }
        }
    }

    public static class SearchHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String requestParamValue=null;
            requestParamValue = Helper.handleGetRequest(t);
            SupSec ans = new SupSec();
            List<Table> data = ans.return_all_list(requestParamValue);
            Helper.handleResponce(t,data);
        }
    }
    public static class LoginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            try{
                String requestParamValue=null;
                requestParamValue = Helper.handleGetRequest(t);
                ArrayList<Employee> e = LocalData.getEmploye(requestParamValue, Facade.rest);
                Employee data = new Employee();
                data.position = "gest";
                data.id = -1;
                if (e.size()>0){
                    data = LocalData.getEmploye(requestParamValue, Facade.rest).get(0);}
                Helper.handleResponce(t,data);
                DataBase.closeConnection();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public static class RegisterHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            try{
                //{email=1, password=1, phone=1, position=1, name=1}
                System.out.println(t.getRequestMethod());
                String query = Helper.parseRequest(t);

                String response = "OK";
                if("POST".equals(t.getRequestMethod())){
                    response = LocalData.CreateNewEmploye(query, Facade.rest);
                }

                t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                t.getResponseHeaders().add("Access-Control-Allow-Headers","Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.toString().getBytes());
                os.close();
                DataBase.closeConnection();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public static class TableHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            try{
                ArrayList<Table> all_data = new ArrayList<>();
                all_data.addAll(LocalData.getallTables(Facade.rest));


                if (Facade.firstSup.tables == null){
                    Facade.firstSup.tables = new ArrayList<>();
                        try{
                        URL url = new URL("http://localhost:5005/tables");

                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");

                        StringBuilder data = new StringBuilder();
                        try (BufferedReader in = new BufferedReader(
                                new InputStreamReader(connection.getInputStream()))) {
                            String line;
                            while ((line = in.readLine()) != null) {
                                data.append(line);
                            }
                        }
                        Facade.firstSup.tables = LocalData.getTable(String.valueOf(data));
                    } catch (Exception e){e.printStackTrace();}
                }
                if (Facade.firstSup.tables != null)
                    all_data.addAll(Facade.firstSup.tables);

                if (Facade.secSup.tables == null){
                    Facade.secSup.tables = new ArrayList<>();
                    try{
                    for (int i=1; i<6; i++) {
                        URL url = new URL("http://localhost:5010/tables?p="+i);

                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");

                        StringBuilder data = new StringBuilder();
                        try (BufferedReader in = new BufferedReader(
                                new InputStreamReader(connection.getInputStream()))) {
                            String line;
                            while ((line = in.readLine()) != null) {
                                data.append(line);
                            }
                        }
                        Facade.secSup.tables.addAll(LocalData.getTable(String.valueOf(data)));
                    }} catch (Exception e){e.printStackTrace();}

                }
                if (Facade.secSup.tables != null)
                    all_data.addAll(Facade.secSup.tables);

                Helper.handleResponce(t,all_data);
                DataBase.closeConnection();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public static class DishHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            t.getResponseHeaders().add("Access-Control-Allow-Headers","Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
            t.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE, UPDATE");
            if("GET".equals(t.getRequestMethod())){
                try{
                    ArrayList<Dish> all_data =  LocalData.getDishes(Facade.rest);
                    Helper.handleResponce(t,all_data);
                    DataBase.closeConnection();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else if("POST".equals(t.getRequestMethod())){
                try{
                    String query = Helper.parseRequest(t);
                    String all_data =  LocalData.createNewDish(query,Facade.rest);
                    Helper.handleResponce(t,all_data);
                    DataBase.closeConnection();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else if("UPDATE".equals(t.getRequestMethod())){
                try{
                    String query = Helper.parseRequest(t);
                    String all_data =  LocalData.updateDish(query,Facade.rest);
                    Helper.handleResponce(t,all_data);
                    DataBase.closeConnection();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else{Helper.handleResponce(t,"technical massage");}
        }
    }
    public static class ChecksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            t.getResponseHeaders().add("Access-Control-Allow-Headers","Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
            t.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE, UPDATE");
            if("GET".equals(t.getRequestMethod())){
                try{
                    String query = Helper.handleGetRequest(t);
                    ArrayList<Check> all_data =  LocalData.getChecks(query, Facade.rest);
                    Helper.handleResponce(t,all_data);
                    DataBase.closeConnection();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else if("POST".equals(t.getRequestMethod())){
                try{
                    String query = Helper.parseRequest(t);
                    String all_data =  LocalData.createNewCheck(query,Facade.rest);
                    Helper.handleResponce(t,all_data);
                    DataBase.closeConnection();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else if("UPDATE".equals(t.getRequestMethod())){
                try{
                    String query = Helper.parseRequest(t);
                    String all_data =  LocalData.updateCheck(query,Facade.rest);
                    Helper.handleResponce(t,all_data);
                    DataBase.closeConnection();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else{Helper.handleResponce(t,"technical massage");}
        }
    }
    public static class ReserveHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            //LocalData.getAllSuplReserve();
            t.getResponseHeaders().add("Access-Control-Allow-Headers","Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
            t.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");
            
            String query = Helper.parseRequest(t);
            if("GET".equals(t.getRequestMethod())){
                try{
                    ArrayList<Reserve> all_data =  LocalData.getReserve(Facade.rest);
                    LocalData.getAllSuplReserve();
                    all_data.addAll(Facade.firstSup.reserves);
                    all_data.addAll(Facade.secSup.reserves);
                    Helper.handleResponce(t,all_data);
                    DataBase.closeConnection();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else if("DELETE".equals(t.getRequestMethod())){
                try{
                    LocalData.getAllSuplReserve();
                    String all_data =  LocalData.deleteReserve(Facade.rest, query);
                    Helper.handleResponce(t,all_data);
                    DataBase.closeConnection();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            else{Helper.handleResponce(t,"technical massage");}
        }
    }
    public static class ReserveSetHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            if (Facade.firstSup.reserves==null ||Facade.secSup.reserves==null)
                LocalData.getAllSuplReserve();
            //t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            t.getResponseHeaders().add("Access-Control-Allow-Headers","Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
            if("GET".equals(t.getRequestMethod())){
                try{
                    ArrayList<Table> all_data =  LocalData.getFreeTable(Helper.handleGetRequest(t), Facade.rest);
                    Helper.handleResponce(t,all_data);
                    DataBase.closeConnection();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else if("POST".equals(t.getRequestMethod())){
                try{
                    String query = Helper.parseRequest(t);
                    String all_data =  LocalData.createNewReserve(query,Facade.rest);
                    Helper.handleResponce(t,all_data);
                    DataBase.closeConnection();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else{Helper.handleResponce(t,"technical massage");}
        }
    }
    public static class ProductHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            //t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            t.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE, UPDATE");
            t.getResponseHeaders().add("Access-Control-Allow-Headers","Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
            if("GET".equals(t.getRequestMethod())){
                try{
                    ArrayList<Product> all_data =  LocalData.getAllProducts(Facade.rest);
                    Helper.handleResponce(t,all_data);
                    DataBase.closeConnection();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else if("POST".equals(t.getRequestMethod())){
                try{
                    String query = Helper.parseRequest(t);
                    String all_data =  LocalData.createNewProduct(query,Facade.rest);
                    Helper.handleResponce(t,all_data);
                    DataBase.closeConnection();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else if("UPDATE".equals(t.getRequestMethod())){
                try{
                    String query = Helper.parseRequest(t);
                    String all_data =  LocalData.updateProduct(query,Facade.rest);
                    Helper.handleResponce(t,all_data);
                    DataBase.closeConnection();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else{Helper.handleResponce(t,"technical massage");}
        }
    }
    public static class ProductListHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            //t.getResponseHeaders().add("Access-Control-Allow-Origin","*");
            t.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE, UPDATE");
            t.getResponseHeaders().add("Access-Control-Allow-Headers","Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
            if("GET".equals(t.getRequestMethod())){
                try{
                    ArrayList<Product> all_data =  LocalData.getProductList(Facade.rest);
                    Helper.handleResponce(t,all_data);
                    DataBase.closeConnection();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else{Helper.handleResponce(t,"technical massage");}
        }
    }
    public static class ReserveNextHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            //t.getResponseHeaders().add("Access-Control-Allow-Origin","*");
            t.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE, UPDATE");
            t.getResponseHeaders().add("Access-Control-Allow-Headers","Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
            if("GET".equals(t.getRequestMethod())){
                try{
                    String query = Helper.handleGetRequest(t);
                    ArrayList<Reserve> all_data =  LocalData.getReserveList(query, Facade.rest);
                    Helper.handleResponce(t,all_data);
                    DataBase.closeConnection();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else{Helper.handleResponce(t,"technical massage");}
        }
    }
    public static class DayBillsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            //t.getResponseHeaders().add("Access-Control-Allow-Origin","*");
            t.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE, UPDATE");
            t.getResponseHeaders().add("Access-Control-Allow-Headers","Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
            if("GET".equals(t.getRequestMethod())){
                try{
                    String query = Helper.handleGetRequest(t);
                    ArrayList<Check> all_data =  LocalData.getCheckList(query, Facade.rest);
                    Helper.handleResponce(t,all_data);
                    DataBase.closeConnection();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else{Helper.handleResponce(t,"technical massage");}
        }
    }
    public static class MenuHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            
            t.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE, UPDATE");
            t.getResponseHeaders().add("Access-Control-Allow-Headers","Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
            if("GET".equals(t.getRequestMethod())){
                try{
                    String requestParamValue = Helper.handleGetRequest(t);
                    ArrayList<Menu> all_data =  LocalData.getTodayMenu(requestParamValue ,Facade.rest);
                    Helper.handleResponce(t,all_data);
                    DataBase.closeConnection();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else if("POST".equals(t.getRequestMethod())){
                try{
                    String query = Helper.parseRequest(t);
                    String all_data =  LocalData.setNextMenu(query ,Facade.rest);
                    Helper.handleResponce(t,all_data);
                    DataBase.closeConnection();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else if("UPDATE".equals(t.getRequestMethod())){
                try{
                    String query = Helper.parseRequest(t);
                    String all_data =  LocalData.updateMenuData(query ,Facade.rest);
                    Helper.handleResponce(t,all_data);
                    DataBase.closeConnection();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else{Helper.handleResponce(t,"technical massage");}
        }
    }
}