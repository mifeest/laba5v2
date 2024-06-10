package Main;

import Commands.Exit;
import Commands.Starting;

import java.io.IOException;
import java.net.SocketException;

public class Client {
    public static void main(String[] args) throws IOException {
        Starting.start(args);
        try {
            Network netWork = new Network("localhost", 2675);
            Requestor requestor = new Requestor(netWork);
            requestor.startQuerying();
        }catch (SocketException e){
            System.out.println("Сервер завершил работу, клиент тоже завершает");
            new Exit();
        }
    }
}
