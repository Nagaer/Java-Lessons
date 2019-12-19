package com.reagan.lab.task6;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {
    public static void main(String[] args) throws IOException {
        System.out.println("Начало работы сервера");
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String messageIn="", messageOut="";
            while (!messageIn.equals("end")) {
                messageIn = dataInputStream.readUTF();
                System.out.println(messageIn);
            }
            socket.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
