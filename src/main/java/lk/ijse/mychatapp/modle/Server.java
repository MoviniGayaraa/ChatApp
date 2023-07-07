package lk.ijse.mychatapp.modle;


import lk.ijse.mychatapp.modle.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static ArrayList<Client> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(6000);

        Socket accept;

        while (true){
            System.out.println("Waiting for Client....");
            accept = serverSocket.accept();
            System.out.println("Client Connected");
            Client clientThread = new Client(accept, clients);
            clients.add(clientThread);
            clientThread.start();


        }
    }

}
