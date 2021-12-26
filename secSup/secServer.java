package main.java.secSup;

import com.sun.net.httpserver.HttpServer;
import main.java.serverPart.Objects.Reserve;
import main.java.serverPart.Objects.Table;
import main.java.serverPart.dataBase.DataBase;

import java.net.InetSocketAddress;
import java.sql.Connection;
import java.util.ArrayList;

public class secServer {
    public static void main(String[] args) throws Exception {
        Connection db = SecDataBase.connectDB();
        tables = new ArrayList<>();
        reserves = new ArrayList<>();
        tables.addAll(DataProc.getAllTables(1));
        tables.addAll(DataProc.getAllTables(2));
        tables.addAll(DataProc.getAllTables(3));
        tables.addAll(DataProc.getAllTables(4));
        tables.addAll(DataProc.getAllTables(5));
        reserves.addAll( main.java.secSup.DataProc.getAllReserves());
        try {
            InetSocketAddress address = new InetSocketAddress("localhost", 5010);
            HttpServer httpServer = HttpServer.create(address, 0);
            httpServer.createContext("/tables", new Handlers.TablesHandler());
            httpServer.createContext("/reserve", new Handlers.ReservesHandler());

            httpServer.setExecutor(null);
            httpServer.start();
            //DataProc.putAllTable();
            System.out.println("Create HTTP server on port 5010");

        } catch (Exception exception) {
            System.out.println("Failed to create HTTP server on port " + 5010 + " of localhost");
            exception.printStackTrace();
        }
    }
    public static ArrayList<Table> tables = null;
    public static ArrayList<Reserve> reserves = null;
}
