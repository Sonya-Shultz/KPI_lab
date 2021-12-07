package main.java.serverPart;
import java.net.InetSocketAddress;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.sun.net.httpserver.HttpServer;

import main.java.serverPart.dataBase.DataBase;
import main.java.serverPart.dataBase.Request;
import main.java.serverPart.Handlers.Handlers;
import main.java.serverPart.Objects.*;
import main.java.serverPart.BuilderPart.*;

public class Facade {
    public static Restoran rest;
    public static void setDBConnection(){DataBase.connectDB();}
    public static Restoran setRestoran(){return RestoranFacade.setRestoranData();}
    public static void StartServer(String name, int port, Restoran rest){
        try {
            InetSocketAddress address = new InetSocketAddress(name, port);
            HttpServer httpServer = HttpServer.create(address, 0);
            Facade.rest = rest;
            httpServer.createContext("/price-list", new Handlers.PriceListHandler());
            httpServer.createContext("/search", new Handlers.SearchHandler());
            httpServer.createContext("/details/", new Handlers.DetailsHandler());
            httpServer.createContext("/login", new Handlers.LoginHandler());
            httpServer.createContext("/register", new Handlers.RegisterHandler());
            httpServer.createContext("/tables", new Handlers.TableHandler());
            httpServer.createContext("/reserve", new Handlers.ReserveHandler());
            httpServer.createContext("/reserveSet", new Handlers.ReserveSetHandler());
            httpServer.createContext("/menu", new Handlers.MenuHandler());
            httpServer.createContext("/dishes", new Handlers.DishHandler());
            httpServer.createContext("/products", new Handlers.ProductHandler());
            httpServer.createContext("/checks", new Handlers.ChecksHandler());
            httpServer.createContext("/productList", new Handlers.ProductListHandler());
            httpServer.createContext("/reserveNext", new Handlers.ReserveNextHandler());
            httpServer.createContext("/dayBills", new Handlers.DayBillsHandler());

            httpServer.setExecutor(null); // creates a default executor
            httpServer.start();
            System.out.println("Create HTTP server on port 5000");

        } catch (Exception exception) {
            System.out.println("Failed to create HTTP server on port " + port + " of " + name);
            exception.printStackTrace();
        }
    }

    public static class RestoranFacade {
        public static Restoran setRestoranData(){
            RestoranBuilder restb = new RestoranBuilder();
            ProductBuilder pb = new ProductBuilder();
            DishBuilder dishb = new DishBuilder();
            MenuBuilder menub = new MenuBuilder();
            EmployeeBuilder empb = new EmployeeBuilder();
            TableBuilder tb = new TableBuilder();
            ReserveBuilder rb = new ReserveBuilder();
            CheckBuilder chb = new CheckBuilder();

            Director director = new Director(pb);

            ArrayList<Product> prod = getAllProducts(director, pb);
            ArrayList<Dish> dish = getAllDish(director, dishb, prod);
            ArrayList<Menu> menu = getAllMenu(director, menub, dish);
            ArrayList<Employee> emp = getAllEmployee(director, empb);
            ArrayList<Table> table = getAllTable(director, tb);
            ArrayList<Reserve> reserve = getAllReserve(director, rb, table);
            ArrayList<Check> checks = getAllChecks(director, chb, table, emp, dish);

            Restoran rest = getRestoran(director, restb, prod, dish, menu, emp, table, reserve, checks);
            System.out.println(rest);
            return rest;
        }

        public static Restoran getRestoran(Director director, RestoranBuilder restb, ArrayList<Product> prod, ArrayList<Dish> dish, ArrayList<Menu> menu, ArrayList<Employee> emp, ArrayList<Table>table, ArrayList<Reserve> reserve, ArrayList<Check>checks){
            director.changeBuilder(restb);
            director.innerData.status = "Open";
            director.innerData.product = prod;
            director.innerData.table = table;
            director.innerData.dish = dish;
            director.innerData.empl = emp;
            director.innerData.menu = menu;
            director.innerData.reserve = reserve;
            director.innerData.checks = checks;
            
            director.make("restoran");
            return restb.getResult();
        }

        public static ArrayList<Product> getAllProducts(Director director, ProductBuilder pb){
            ArrayList<Product> prod = new ArrayList<>();
            try {
                Request req = new Request("Select * from Products");
                ResultSet res = (ResultSet) req.exec();
                director.changeBuilder(pb);
                while (res.next()) {
                    director.innerData.id = res.getInt("id");
                    director.innerData.name = res.getString("pr_name");
                    director.innerData.amountF = res.getFloat("amount");
                    director.innerData.status = res.getString("pr_status");
                    director.innerData.desc = res.getString("pr_type");
                    director.make("product");
                    prod.add(pb.getResult());
                }
                
            } catch(Exception e){
                e.printStackTrace();
            }
            return prod;
        }
        public static ArrayList<Dish> getAllDish(Director director, DishBuilder dishb, ArrayList<Product> prod){
            ArrayList<Dish> dishs = new ArrayList<>();
            try {
                Request req = new Request("Select * from Dishes");
                ResultSet res = (ResultSet) req.exec();
                Request req2 = new Request("Select * from Dish_details");
                ResultSet res2 = (ResultSet) req2.exec();
                ArrayList<Integer> conection = new ArrayList<>();  
                while (res2.next()) {
                    conection.add(res2.getInt("product_id"));
                    conection.add(res2.getInt("dish_id"));
                }
                director.changeBuilder(dishb);
                while (res.next()) {
                    director.innerData.id = res.getInt("id");
                    director.innerData.name = res.getString("d_name");
                    director.innerData.amountF = res.getFloat("price");
                    director.innerData.status = res.getString("dish_type");
                    director.innerData.desc = res.getString("d_description");
                    director.innerData.product = filterPr(prod, conection, director.innerData.id);
                    director.make("dish");
                    dishs.add(dishb.getResult());
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            return dishs;
        }
        public static ArrayList<Product> filterPr(ArrayList<Product> prList, ArrayList<Integer> data, int disdId){
            ArrayList<Integer> numb = new ArrayList<>();
            for(int i=0; i<data.size(); i+=2){
                if (data.get(i+1) == disdId){
                    numb.add(data.get(i));
                }
            }
            ArrayList<Product> pr = new ArrayList<>();
            prList.forEach(el -> {
                if (numb.contains(el.id)){
                    pr.add(el);
                }
            });
            return pr;
        };
        public static ArrayList<Menu> getAllMenu(Director director, MenuBuilder menub, ArrayList<Dish> dish){
            ArrayList<Menu> menu = new ArrayList<>();
            try {
                Request req = new Request("Select * from Menu");
                ResultSet res = (ResultSet) req.exec();
                director.changeBuilder(menub);
                while (res.next()) {
                    director.innerData.id = res.getInt("id");
                    director.innerData.amountI = res.getInt("dish_id");
                    ArrayList<Dish> di = new ArrayList<>();
                    for(int i=0; i<dish.size(); i++){
                        if (dish.get(i).id == director.innerData.amountI){
                            di.add(dish.get(i));
                            break;
                        }
                    }
                    director.innerData.dish = di;
                    director.innerData.status = res.getString("m_status");
                    director.innerData.date = res.getString("m_date").toString();// Timestamp.valueOf(res.getString("m_date").toString().replace("T", " "));
                    director.make("menu");
                    menu.add(menub.getResult());
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            return menu;
        }
        public static ArrayList<Employee> getAllEmployee(Director director, EmployeeBuilder empb){
            ArrayList<Employee> emp = new ArrayList<>();
            try {
                Request req = new Request("Select * from Employees");
                ResultSet res = (ResultSet) req.exec();
                director.changeBuilder(empb);
                while (res.next()) {
                    director.innerData.id = res.getInt("id");
                    director.innerData.name = res.getString("full_name");
                    director.innerData.phone = res.getString("phone_number");
                    director.innerData.email = res.getString("email");
                    director.innerData.status = res.getString("position");
                    director.innerData.desc = res.getString("e_password");
                    director.make("employee");
                    emp.add(empb.getResult());
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            return emp;
        }
        public static ArrayList<Table> getAllTable(Director director, TableBuilder tb){
            ArrayList<Table> table = new ArrayList<>();
            try {
                Request req = new Request("Select * from Tables_r");
                ResultSet res = (ResultSet) req.exec();
                director.changeBuilder(tb);
                director.innerData.name = "MyComp";
                while (res.next()) {
                    director.innerData.id = res.getInt("id");
                    director.innerData.status = res.getString("status_in_moment");
                    director.innerData.desc = res.getString("t_description");
                    director.innerData.amountI = res.getInt("sit");
                    director.make("table");
                    table.add(tb.getResult());
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            return table;
        }
        public static ArrayList<Reserve> getAllReserve(Director director, ReserveBuilder rb, ArrayList<Table> table){
            ArrayList<Reserve> reserve = new ArrayList<>();
            try {
                Request req = new Request("Select * from Reserve");
                ResultSet res = (ResultSet) req.exec();
                director.changeBuilder(rb);
                while (res.next()) {
                    director.innerData.id = res.getInt("id");
                    director.innerData.status = res.getString("time_along");
                    director.innerData.desc = res.getString("special_description");
                    director.innerData.date = res.getString("date_time").toString(); //Timestamp.valueOf(res.getString("date_time").toString().replace("T", " "));
                    director.innerData.phone = res.getString("phone_number");
                    director.innerData.name = res.getString("full_name");
                    director.innerData.amountI = res.getInt("table_id");
                    ArrayList<Table> tb = new ArrayList<>();
                    for(int i=0; i<table.size(); i++){
                        if (table.get(i).id == director.innerData.amountI){
                            tb.add(table.get(i));
                            break;
                        }
                    }
                    director.innerData.table = tb;
                    director.make("reserve");
                    reserve.add(rb.getResult());
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            return reserve;
        }
        public static ArrayList<Check> getAllChecks(Director director, CheckBuilder chb, ArrayList<Table> table, ArrayList<Employee> emp, ArrayList<Dish> dish){
            ArrayList<Check> checks = new ArrayList<>();
            try {
                Request req = new Request("Select * from Checks");
                ResultSet res = (ResultSet) req.exec();
                director.changeBuilder(chb);
                Request req2 = new Request("Select * from Check_details;");
                ResultSet res2 = (ResultSet) req2.exec();
                ArrayList<Integer> data = new ArrayList<>();
                while (res2.next()){
                    data.add(res2.getInt("check_id"));
                    data.add(res2.getInt("dish_id"));
                }
                while (res.next()) {
                    director.innerData.id = res.getInt("id");
                    director.innerData.status = res.getString("ch_status");
                    director.innerData.date = res.getString("date_time").toString();//Timestamp.valueOf(res.getString("date_time").toString().replace("T", " "));
                    director.innerData.amountF = res.getFloat("full_sum");
                    director.innerData.amountI = res.getInt("table_id");
                    ArrayList<Table> tb = new ArrayList<>();
                    for(int i=0; i<table.size(); i++){
                        if (table.get(i).id == director.innerData.amountI){
                            tb.add(table.get(i));
                            break;
                        }
                    }
                    director.innerData.table = tb;
                    director.innerData.amountI = res.getInt("employee_id");
                    ArrayList<Employee> empl = new ArrayList<>();
                    for(int i=0; i<emp.size(); i++){
                        if (emp.get(i).id == director.innerData.amountI){
                            empl.add(emp.get(i));
                            break;
                        }
                    }
                    director.innerData.empl = empl;
                    director.innerData.dish = filterDish(dish, data, director.innerData.id);
                    director.make("check");
                    checks.add(chb.getResult());
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            return checks;
        }
        public static ArrayList<Dish> filterDish(ArrayList<Dish> dish, ArrayList<Integer> data, int checkId){
            ArrayList<Integer> numb = new ArrayList<>();
            for(int i=0; i<data.size(); i+=2){
                if (data.get(i) == checkId){
                    numb.add(data.get(i+1));
                }
            }
            ArrayList<Dish> pr = new ArrayList<>();
            numb.forEach(el -> {
                dish.forEach(ele -> {
                    if (el == ele.id){
                        pr.add(ele);
                    }
                });
            });
            return pr;
        };
    }
    
}
