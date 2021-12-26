package main.java.serverPart;

import main.java.serverPart.Handlers.Helper;
import main.java.serverPart.Objects.Restoran;
import main.java.serverPart.tables.ExternalSup;

public class Server {
    public static void main(String[] args) throws Exception {
        Facade.setDBConnection();
        Facade.firstSup = new ExternalSup();
        Facade.secSup = new ExternalSup();
        //Helper.putAllTable();
        Restoran rest = Facade.setRestoran();
        Facade.StartServer("localhost", 5000, rest);
        Thread thread = new Thread(){
            public void run(){
                Facade.StartFilterServer("localhost", 5015);
            }
        };
        thread.start();
    }
}
