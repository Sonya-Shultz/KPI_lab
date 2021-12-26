package main.java.firstSupl;

import com.sun.net.httpserver.HttpServer;
import main.java.serverPart.Objects.Reserve;
import main.java.serverPart.Objects.Table;

import java.net.InetSocketAddress;
import java.sql.Connection;
import java.util.ArrayList;

public class frServer {
    //public static Table tables;
    //public static Reserve reserves;
    public static void main(String[] args) throws Exception {
        Connection db = FrDataBase.connectDB();
        tables = new ArrayList<>();
        reserves = new ArrayList<>();
        tables.addAll(DataProc.getAllTables());
        reserves.addAll(DataProc.getAllReserves());
        //DataProc.putAllTable();
        try {
            InetSocketAddress address = new InetSocketAddress("localhost", 5005);
            HttpServer httpServer = HttpServer.create(address, 0);
            httpServer.createContext("/tables", new Handlers.TablesHandler());
            httpServer.createContext("/reserve", new Handlers.ReservesHandler());

            httpServer.setExecutor(null);
            httpServer.start();
            System.out.println("Create HTTP server on port 5005");

        } catch (Exception exception) {
            System.out.println("Failed to create HTTP server on port " + 5005 + " of localhost");
            exception.printStackTrace();
        }
    }
    public static ArrayList<Table> tables = null;
    public static ArrayList<Reserve> reserves = null;
}
