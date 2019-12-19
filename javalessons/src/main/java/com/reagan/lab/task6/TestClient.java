package com.reagan.lab.task6;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TestClient {
    public static void main(String[] args) {
        System.out.println("Начало работы клиента чата...");
        try {
            Socket socket = new Socket("localhost", 8000);
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String messageIn="", messageOut="";
            while (!messageOut.equals("end")) {
                messageOut = bufferedReader.readLine();
                dataOutputStream.writeUTF(messageOut);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
