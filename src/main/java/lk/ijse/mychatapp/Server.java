package lk.ijse.mychatapp;


import lk.ijse.mychatapp.model.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static ArrayList<Client> clients = new ArrayList<Client>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5001);
        Socket socket;

        while (true){
            System.out.println("Waiting for Client..");
            socket = serverSocket.accept();
            System.out.println("Client Connected");
            Client clientThread = new Client(socket,clients);
            clients.add(clientThread);
            clientThread.start();
        }
    }
}
