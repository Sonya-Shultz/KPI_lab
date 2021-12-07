package main.java.serverPart.dataBase;
import java.sql.*;

public class DataBase {
    static String dbName = "mytest";
    static String serverip="localhost";
    static String serverport="1433";
    static String url = "jdbc:sqlserver://"+serverip+":"+serverport+";databaseName="+dbName+"";
    static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    private DataBase(){};
    private static Connection conn = null;

    public static Connection connectDB() {
        String databaseUserName = "administrator";
        String databasePassword = "admin";
        try {
            if(conn == null){
                Class.forName(driver).getDeclaredConstructor().newInstance();
                conn = DriverManager.getConnection(url, databaseUserName, databasePassword);
                System.out.println("Conect to DB");
            }
            else {
                if (conn.isClosed()){
                    Class.forName(driver).getDeclaredConstructor().newInstance();
                    conn = DriverManager.getConnection(url, databaseUserName, databasePassword);
                    System.out.println("Conect to DB");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
    public static void closeConnection(){
        try {
            conn.close();
            System.out.println("Close conection to DB");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
