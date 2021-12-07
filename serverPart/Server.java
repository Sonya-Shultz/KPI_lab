package main.java.serverPart;

import main.java.serverPart.Objects.Restoran;

public class Server {
    public static void main(String[] args) throws Exception {
        Facade.setDBConnection();
        Restoran rest = Facade.setRestoran();
        Facade.StartServer("localhost", 5000, rest);
    }
}
