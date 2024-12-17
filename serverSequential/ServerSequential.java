/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.serverSequential;

import com.mycompany.lab7.Matrix;
import java.io.*;
import java.net.*;

/**
 *
 * @author Милана
 */
public class ServerSequential {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Сервер запущен и ожидает подключения...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                    InputStream inputStream = clientSocket.getInputStream();
                    OutputStream outputStream = clientSocket.getOutputStream()) {

                    // Получение матрицы от клиента
                    ObjectInputStream in = new ObjectInputStream(inputStream);
                    Matrix matrix = (Matrix) in.readObject();

                    // Вычисление суммы нечётных элементов
                    double sumOfOddElements = Matrix.sumOddElements(matrix);

                    // Отправка результата обратно клиенту
                    DataOutputStream out = new DataOutputStream(outputStream);
                    out.writeDouble(sumOfOddElements);
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("Ошибка обработки клиента: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка запуска сервера: " + e.getMessage());
        }
    }
}
