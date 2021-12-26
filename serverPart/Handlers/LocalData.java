package main.java.serverPart.Handlers;
import main.java.serverPart.Facade;
import main.java.serverPart.BuilderPart.*;
import main.java.serverPart.Objects.*;
import main.java.serverPart.dataBase.*;
import main.java.serverPart.specificationConcret.*;
import main.java.serverPart.Specification.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class LocalData {
    public static ArrayList<Table> getTable(String req){
        ArrayList<Table> all_data = new ArrayList<>();
        TableBuilder tb = new TableBuilder();
        Director director = new Director(tb);
        ArrayList<Object> data = Helper.getArrListFromJSON(req);
        ArrayList<Map<String,Object>> a = new ArrayList<>();
        data.forEach(el -> {a.add((Map<String,Object>) el);});
        a.forEach(
                el -> {
                    director.innerData.name = (String)el.get("from");
                    director.innerData.id = (Integer)el.get("id");
                    director.innerData.status = (String)el.get("status_in_moment");
                    director.innerData.desc = (String)el.get("description");
                    director.innerData.amountI = (Integer)el.get("sit");
                    director.make("table");
                    all_data.add(tb.getResult());
                }
        );
        return all_data;
    }

    public static void getAllSuplReserve(){
        Facade.firstSup.reserves = new ArrayList<>();
        try{
            URL url = new URL("http://localhost:5005/reserves");

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
            Facade.firstSup.reserves = LocalData.getUrlReserve(String.valueOf(data));
        } catch (Exception e){e.printStackTrace();}
        Facade.secSup.reserves = new ArrayList<>();
        try{
            URL url = new URL("http://localhost:5010/reserves");

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
            Facade.secSup.reserves = LocalData.getUrlReserve(String.valueOf(data));
        } catch (Exception e){e.printStackTrace();}
    }
    public static ArrayList<Reserve> getUrlReserve(String req){
        ArrayList<Reserve> all_data = new ArrayList<>();
        ReserveBuilder tb = new ReserveBuilder();
        Director director = new Director(tb);
        ArrayList<Object> data = Helper.getArrListFromJSON(req);
        ArrayList<Map<String,Object>> a = new ArrayList<>();
        data.forEach(el -> {a.add((Map<String,Object>) el);});
        a.forEach(
                el -> {
                    director.innerData.id = (Integer) el.get("id");
                    director.innerData.name = (String) el.get("full_name");
                    director.innerData.phone = (String)el.get("phone");
                    director.innerData.date = (String) el.get("date_time");
                    director.innerData.status = (String)el.get("time_along");
                    director.innerData.desc = (String)el.get("special_description");
                    Map<String,Object> table_h = (Map<String,Object>) el.get("table");

                    /*ArrayList<Map<String,Object>> table_ha = new ArrayList<>();
                    data.forEach(elem -> {table_ha.add((Map<String,Object>) elem);});
                    Map<String,Object> table_hh = table_ha.get(0);*/
                    ArrayList<Table> tab = new ArrayList<>();
                    if (director.innerData.desc.equals("First sup")) {
                        for (int i = 0; i < Facade.firstSup.tables.size(); i++) {
                            if (Facade.firstSup.tables.get(i).id == ((Integer)table_h.get("id"))) {
                                tab.add(Facade.firstSup.tables.get(i));
                                break;
                            }
                        }
                    } else if (director.innerData.desc.equals("Sec sup")){
                        for (int i = 0; i < Facade.secSup.tables.size(); i++) {
                            if (Facade.secSup.tables.get(i).id == ((Integer)table_h.get("id"))) {
                                tab.add(Facade.secSup.tables.get(i));
                                break;
                            }
                        }
                    }
                    director.innerData.table = tab;
                    director.make("reserve");
                    all_data.add(tb.getResult());
                }
        );
        return all_data;
    }

    public static ArrayList<Employee> getEmploye(String request, Restoran rest){
        ArrayList<Employee> all_data = new ArrayList<>();
        String pass = request.split("&")[1].replaceAll("&", "");
        String email = request.split("&")[0].replaceAll("&", "");
        try{
            for (int i=0; i< rest.all_employees.size(); i++){
                System.out.println(rest.all_employees.get(i).password+" "+pass);
                System.out.println(rest.all_employees.get(i).email+" "+email);
                if (rest.all_employees.get(i).email.equals(email) && rest.all_employees.get(i).password.equals(pass)){
                    all_data.add(rest.all_employees.get(i));
                    break;
                }
            }
        }
        catch(Exception e){e.printStackTrace();}
        return all_data;
    }
    public static String CreateNewEmploye(String query, Restoran rest){
        Map<String,Object>data = Helper.getFromJSON(query);

        EmployeeBuilder eb = new EmployeeBuilder();
        Director director = new Director(eb);
        director.innerData.name = "MyComp";

        director.innerData.email=(String)data.get("email");
        director.innerData.name=(String)data.get("name");
        director.innerData.status=(String)data.get("position");
        director.innerData.phone=(String)data.get("phone");
        director.innerData.desc=(String)data.get("password");
        director.make("employee");
        Employee worker = eb.getResult();

        rest.all_employees.add(worker);
        
        Request re = new Request("INSERT INTO Employees VALUES ('"+worker.full_name+"','"+worker.phone_number+"','"+worker.email+"','"+worker.position+"','"+worker.password+"');");
        String response = (String)re.exec();
        Facade.rest = Facade.setRestoran();
        return response;
    }

    public static ArrayList<Table> getallTables(Restoran rest){
        ArrayList<Table> all_data = new ArrayList<>();
        try{
            all_data = rest.all_tables;
        }
        catch(Exception e){
            System.out.println("not find in local DB");
        }
        return all_data;
    }

    public static ArrayList<Reserve> getReserve(Restoran rest){
        ArrayList<Reserve> all_data = new ArrayList<>();
        try{
            all_data.addAll(rest.all_reserves);
        }
        catch(Exception e){
            System.out.println("not find in local DB");
        }
        return  all_data;
    }
    public static ArrayList<Dish> getDishes(Restoran rest){
        ArrayList<Dish> all_data = new ArrayList<>();
        try{
            all_data = rest.all_dishes;
        }
        catch(Exception e){
            System.out.println("not find in local DB");
        }
        return  all_data;
    }
    public static ArrayList<Table> getFreeTable(String query, Restoran rest){
        ArrayList<Table> tab = new ArrayList<>();
        Specification<Table> tabS = new FreeTableScecification(rest.all_reserves, query);
        ArrayList<Table> tab1 = new ArrayList<>();
        tab1.addAll(applySpecified(rest.all_tables, tabS).subList(0,2000));
        tab.addAll(tab1);
        if (Facade.firstSup.reserves != null && Facade.firstSup.tables!= null){
            ArrayList<Table> tab2 = new ArrayList<>();
            Specification<Table> tabS2 = new FreeTableScecification(Facade.firstSup.reserves, query);
            tab2.addAll(applySpecified(Facade.firstSup.tables, tabS2).subList(0,2000));
            tab.addAll(tab2);
        }
        if (Facade.secSup.reserves != null && Facade.secSup.tables!= null){
            ArrayList<Table> tab3 = new ArrayList<>();
            Specification<Table> tabS3 = new FreeTableScecification(Facade.secSup.reserves, query);
            tab3.addAll(applySpecified(Facade.secSup.tables, tabS3).subList(0,2000));
            tab.addAll(tab3);
        }
        return tab;
    }
    public static ArrayList<Product> getAllProducts(Restoran rest){
        ArrayList<Product> all_data = new ArrayList<>();
        try{
            all_data = rest.all_products;
        }
        catch(Exception e){
            System.out.println("not find in local DB");
        }
        return  all_data;
    }
    public static String createNewProduct(String query, Restoran rest){
        Map<String,Object>res = Helper.getFromJSON(query);

        ProductBuilder rb = new ProductBuilder();
        Director director = new Director(rb);
        director.innerData.name = "MyComp";

        director.innerData.id = 0;
        director.innerData.name = (String)res.get("pr_name");
        director.innerData.status = (String)res.get("pr_status");
        director.innerData.desc = (String)res.get("pr_type");
        director.innerData.amountF = Float.parseFloat( ((Number)res.get("amount")).toString());
        
        director.make("product");
        Product prod = rb.getResult();
        
        Request re = new Request("INSERT INTO Products VALUES ('"+prod.name+"', '"+prod.pr_type+"', '"+prod.status+"', '"+prod.amount+"')");
        String response = (String)re.exec();
        Facade.rest = Facade.setRestoran();
        return response;
    }
    public static String createNewDish(String query, Restoran rest){
        Map<String,Object>res = Helper.getFromJSON(query);

        Float price = Float.parseFloat( ((Number)res.get("price")).toString());
        String name = (String)res.get("name");
        String type = (String)res.get("type");
        String desc = (String)res.get("description");
        
        Request re = new Request("INSERT INTO Dishes VALUES ('"+name+"', '"+type+"', '"+desc+"', '"+price+"')");
        String response = (String)re.exec();
        Facade.rest = Facade.setRestoran();
        return response;
    }
    public static String updateDish(String query, Restoran rest){
        Map<String,Object>res = Helper.getFromJSON(query);
        int id = Integer.parseInt( ((Number)res.get("id")).toString());
        Float price = Float.parseFloat( ((Number)res.get("price")).toString());
        String name = (String)res.get("name");
        String type = (String)res.get("type");
        String desc = (String)res.get("description");
        String[] ids = ((String)res.get("ids")).split(" ");
        
        Request re = new Request("UPDATE Dishes SET d_name='"+name+"', d_description='"+desc+"', dish_type='"+type+"', price='"+price+"' WHERE id="+id);
        String response = (String)re.exec();
        Request re2 = new Request("Delete from Dish_details where dish_id="+id);
        String response2 = (String)re2.exec();
        String data="";
        for (int i=0; i<ids.length; i++){
            data += "('"+ids[i]+"','"+id+"'),";
        }
        data = data.substring(0, data.length()-1);
        if (data!= ""){
            Request re3 = new Request("INSERT INTO Dish_details VALUES "+data);
            String response3 = (String)re3.exec();
            Facade.rest = Facade.setRestoran();
            return response + response2 + response3;}
        return response + response2;
    }
    public static String deleteReserve(Restoran rest, String query){
        try{
            Map<String,Object>res = Helper.getFromJSON(query);

            int res_id = (Integer) res.get("id");
            String spec = (String) res.get("special_description");
            System.out.println(res_id+" "+spec);
            Request re = new Request("DELETE FROM Reserve WHERE id = "+res_id+";");
            rest.all_reserves.removeIf(el -> {return el.id == res_id && el.special_description.equals(spec);});
            String response = (String)re.exec();
            return response;
        }
        catch(Exception e){
            return e.toString();
        }
    }
    public static ArrayList<Menu> getTodayMenu(String query, Restoran rest){
        ArrayList<Menu> tab = new ArrayList<>();
        Specification<Menu> menuS = new MenuSpecification(query);
        tab = applySpecified(rest.today_menu, menuS);
        return tab;
    }
    public static String createNewReserve(String query, Restoran rest){
        Map<String,Object>res = Helper.getFromJSON(query);

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

        if (reserves.special_description.equals("MyComp")){
            Request re = new Request("INSERT INTO Reserve VALUES ("+(Integer)res.get("table_id")+",'"+reserves.date_time.toString()+"','"+reserves.time_along+"','"+reserves.phone+"','"+reserves.full_name+"','"+reserves.special_description+"');");
            response = (String)re.exec();
        }
        Facade.rest = Facade.setRestoran();
        return response;
    }
    public static String updateMenuData(String query, Restoran rest){
        try{
            Map<String,Object>res = Helper.getFromJSON(query);
            int id = Integer.parseInt((String)res.get("id"));
            String status = (String) res.get("status");

            Request re = new Request("UPDATE Menu SET m_status='"+status+"' WHERE id="+id);
            String response = (String)re.exec();

            for (int i=0; i< rest.today_menu.size(); i++){
                if (rest.today_menu.get(i).id == id){rest.today_menu.get(i).status=status; break;}
            }
            return "Update. "+response;
        }catch (Exception e){
            e.printStackTrace();
            return "Error Updating";
        }
    }
    public static String updateProduct(String query, Restoran rest){
        try{
            Map<String,Object>res = Helper.getFromJSON(query);
            int id = (Integer)res.get("id");
            String status = (String) res.get("pr_status");
            String name = (String) res.get("pr_name");
            String type = (String) res.get("pr_type");
            Float amount = Float.parseFloat(((Number) res.get("amount")).toString());

            Request re = new Request("UPDATE Products SET pr_name='"+name+"', pr_status='"+status+"', pr_type='"+type+"', amount="+amount+" WHERE id="+id+";");
            String response = (String)re.exec();

            for (int i=0; i< rest.all_products.size(); i++){
                if (rest.all_products.get(i).id == id){
                    rest.all_products.get(i).name = name;
                    rest.all_products.get(i).pr_type = type;
                    rest.all_products.get(i).status = status;
                    rest.all_products.get(i).amount = amount;
                    break;}
            }
            return "Update. "+response;
        }catch (Exception e){
            e.printStackTrace();
            return "Error Updating";
        }
    }
    public static String setNextMenu(String query, Restoran rest) throws SQLException{
        Map<String,Object>res = Helper.getFromJSON(query);

        Request re1 = new Request("SELECT * FROM Menu WHERE dish_id='"+(String) res.get("dish_id")+"' AND CONVERT(VARCHAR(10), m_date)=CONVERT(VARCHAR(10), DATEADD(day,+1,GETDATE()));");
        ResultSet response1 = (ResultSet)re1.exec();
        if (! response1.next()){
            Request re = new Request("INSERT INTO Menu VALUES ('"+(String) res.get("dish_id")+"', 'OK', DATEADD(day,+1,GETDATE()));");
            String response = (String)re.exec();
            Facade.rest = Facade.setRestoran();
            return response;
        }
        else return "Already added";
        
    }
    public static ArrayList<Check> getChecks(String query, Restoran rest){
        ArrayList<Check> all_data = new ArrayList<>();
        try{
            int id = Integer.parseInt(query);
            Specification<Check> chS = new WitersChecksSpecification(id);
            all_data = applySpecified(rest.all_checks, chS);
            return all_data;
        }
        catch(Exception e){
            System.out.println("not find checks in local DB");
        }
        return  all_data;
    }
    public static String createNewCheck(String query, Restoran rest){
        Map<String,Object>res = Helper.getFromJSON(query);

        int table_id = Integer.parseInt(((Number)res.get("table_id")).toString());
        int empl_id = Integer.parseInt(((Number)res.get("empl_id")).toString());

        Request re = new Request("INSERT INTO Checks VALUES ( GETDATE(), '"+table_id+"', '"+empl_id+"','0','NEW')");
        String response = (String)re.exec();
        Facade.rest = Facade.setRestoran();
        return response;
    }
    public static String updateCheck(String query, Restoran rest){
        Map<String,Object>res = Helper.getFromJSON(query);
        Float price = Float.parseFloat( ((Number)res.get("price")).toString());
        String status = (String)res.get("status");
        String[] ids = ((String)res.get("ids")).split(" ");
        int id = Integer.parseInt(((Number)res.get("id")).toString());
        
        Request re = new Request("UPDATE Checks SET ch_status='"+status+"', full_sum='"+price+"' WHERE id="+id);
        String response = (String)re.exec();
        Request re2 = new Request("Delete from Check_details where check_id="+id);
        String response2 = (String)re2.exec();
        String data="";
        for (int i=0; i<ids.length; i++){
            try{
                data += "('"+id+"','"+Integer.parseInt(ids[i])+"'),";
            }
            catch(Exception e){}
        }
        System.out.println(data);
        if (data!= ""){
            data = data.substring(0, data.length()-1);
            Request re3 = new Request("INSERT INTO Check_details VALUES "+data);
            String response3 = (String)re3.exec();
            Facade.rest = Facade.setRestoran();
            return response + response2 + response3;}
        return response + response2;
    }
    public static ArrayList<Product> getProductList(Restoran rest){
        ArrayList<Product> all_data = new ArrayList<>();
        try{
            Specification<Product> spec = new AndSpecification<>(new ProductInNextMenuSpecification(rest.today_menu), new NotOkProductSpecification());
            all_data = applySpecified(rest.all_products, spec);
            return all_data;
        }
        catch(Exception e){
            System.out.println("not find checks in local DB");
        }
        return  all_data;
    }
    public static ArrayList<Reserve> getReserveList(String s, Restoran rest){
        ArrayList<Reserve> all_data = new ArrayList<>();
        try{
            Specification<Reserve> spec = new ReserveSpecification(s);
            all_data = applySpecified(rest.all_reserves, spec);
            return all_data;
        }
        catch(Exception e){
            System.out.println("not find checks in local DB");
        }
        return  all_data;
    }
    public static ArrayList<Check> getCheckList(String s, Restoran rest){
        ArrayList<Check> all_data = new ArrayList<>();
        try{
            Specification<Check> spec = new AndSpecification<>(new CheksDateSpecification(s), new DoneStatusCheckSpecification());
            all_data = applySpecified(rest.all_checks, spec);
            return all_data;
        }
        catch(Exception e){
            System.out.println("not find checks in local DB");
        }
        return  all_data;
    }
    static <T> ArrayList<T> applySpecified(ArrayList<T> set, Specification<T> spec) {
		ArrayList<T> data = new ArrayList<>();
        for(T t : set) {
			if( spec.isSatisfiedBy(t) ) {
				data.add(t);
			}
		}
        return data;
	}
}
