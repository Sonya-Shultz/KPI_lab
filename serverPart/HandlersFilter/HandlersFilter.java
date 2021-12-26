package main.java.serverPart.HandlersFilter;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.serverPart.Facade;
import main.java.serverPart.Handlers.Helper;
import main.java.serverPart.Objects.Restoran;
import main.java.serverPart.Objects.Table;
import main.java.serverPart.Server;
import main.java.serverPart.Specification.AndSpecification;
import main.java.serverPart.Specification.Specification;
import main.java.serverPart.specificationConcret.FreeStatusTableSpecification;
import main.java.serverPart.specificationConcret.FreeTableScecification;
import main.java.serverPart.specificationConcret.TableSitSpaceSpecification;
import main.java.serverPart.specificationConcret.TableSuplSpecification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;

public class HandlersFilter {
    public static class AllTableHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            t.getResponseHeaders().add("Access-Control-Allow-Headers","Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
            t.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE, UPDATE");
            if("GET".equals(t.getRequestMethod())) {
                try {
                    Restoran rest = Facade.rest;
                    rest.all_tables.addAll(Facade.firstSup.tables);
                    rest.all_tables.addAll(Facade.secSup.tables);
                    ArrayList<Table> tab = new ArrayList<>();
                    String query = Helper.handleGetRequest(t);
                    String[] q = query.split("&");
                    String status = "free";
                    String supl = "";
                    int sit_max = 100;
                    int sit_min = 0;
                    for(int i =0; i< q.length;i++){
                        if(q[i].contains("st+")){
                            q[i] = q[i].replaceAll("st","");
                            q[i] = q[i].replaceAll("\\+","");
                            q[i] = q[i].replaceAll("&","");
                            status = q[i];
                        }
                        if(q[i].contains("max+")){
                            q[i] = q[i].replaceAll("max","");
                            q[i] = q[i].replaceAll("\\+","");
                            q[i] = q[i].replaceAll("&","");
                            try{sit_max = Integer.parseInt(q[i]);}catch (Exception e){e.printStackTrace();}
                        }
                        if(q[i].contains("min+")){
                            q[i] = q[i].replaceAll("min","");
                            q[i] = q[i].replaceAll("\\+","");
                            q[i] = q[i].replaceAll("&","");
                            try{sit_min = Integer.parseInt(q[i]);}catch (Exception e){e.printStackTrace();}
                        }
                        if (q[i].contains("supl+")){
                            q[i] = q[i].replaceAll("supl","");
                            q[i] = q[i].replaceAll("\\+","");
                            q[i] = q[i].replaceAll("&","");
                            q[i] = q[i].replaceAll("%20"," ");
                            supl = q[i];
                        }
                    }
                    System.out.println(status + " "+ sit_max+" "+sit_min+" "+supl);
                    Specification<Table> allS = new AndSpecification<>(new TableSitSpaceSpecification(sit_max, sit_min),
                            new FreeStatusTableSpecification(status)).and(new TableSuplSpecification(supl));
                    tab = applySpecified(rest.all_tables, allS);
                    Helper.handleResponce(t,tab);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{Helper.handleResponce(t,"technical massage");}
        }
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
