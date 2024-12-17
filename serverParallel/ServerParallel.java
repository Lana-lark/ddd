/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.serverParallel;

import com.mycompany.lab7.Matrix;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 *
 * @author Милана
 */
public class ServerParallel {
    private static final ExecutorService executor = Executors.newFixedThreadPool(10); // Настраиваемый пул потоков

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12346); // Замените на нужный порт
        System.out.println("Server started on port 12346");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepted connection from " + clientSocket.getInetAddress());
            executor.submit(new ClientHandler(clientSocket));
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                 ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream())) {

                Matrix matrix = (Matrix) ois.readObject();
                double sum = Matrix.sumOddElements(matrix);
                oos.writeObject(String.valueOf(sum));
                System.out.println("Sent result to client: " + sum);

            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error handling client: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("Error closing socket: " + e.getMessage());
                }
            }
        }
    }
}
