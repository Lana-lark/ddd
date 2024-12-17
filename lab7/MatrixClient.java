/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

/**
 *
 * @author Милана
 */
public class MatrixClient extends JFrame{
    private JTextField rowsField;
    private JTextField colsField;
    private JTextArea resultArea;

    public MatrixClient() {
        setTitle("Matrix");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel rowsLabel = new JLabel("Введите количество строк:");
        rowsField = new JTextField(5);
        
        JLabel colsLabel = new JLabel("Введите количество столбцов:");
        colsField = new JTextField(5);

        JButton submitButton = new JButton("Отправить матрицу");
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMatrixToServer();
            }
        });

        add(rowsLabel);
        add(rowsField);
        add(colsLabel);
        add(colsField);
        add(submitButton);
        add(new JScrollPane(resultArea));

        setVisible(true);
    }

    private void sendMatrixToServer() {
        try {
            int rows = Integer.parseInt(rowsField.getText());
            int cols = Integer.parseInt(colsField.getText());

            if (rows <= 0 || cols <= 0) {
                throw new NumberFormatException("Размеры должны быть положительными целыми числами.");
            }

            Matrix matrix = new Matrix(rows, cols);
            //Сериализация матрицы
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(matrix);
            out.flush();
            byte[] matrixBytes = byteOut.toByteArray();

            //Установление соединения с сервером
            try (Socket socket = new Socket("localhost", 12345);
                OutputStream outputStream = socket.getOutputStream();
                InputStream inputStream = socket.getInputStream()) {
                //Отправка матрицы на сервер
                outputStream.write(matrixBytes);
                outputStream.flush();
                //Получение результата от сервера
                ObjectInputStream in = new ObjectInputStream(inputStream);
                Double sumOfOddElements = in.readDouble();
                // Вывод результата
                resultArea.setText("Сгенерированная матрица:\n" + matrix.toString());
                resultArea.append("Сумма нечётных элементов: " + sumOfOddElements);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ошибка ввода: " + ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Ошибка соединения с сервером: " + ex.getMessage());
        }
    }
}
