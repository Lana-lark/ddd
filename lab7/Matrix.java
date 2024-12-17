/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab7;

import java.io.Serializable;
import java.util.Random;

/**
 *
 * @author Милана
 */
public class Matrix implements Serializable {
    
    private double[][] matrix;
    
    public Matrix(int rows, int collums){ //rows - строки, collums - столбцы
        matrix = new double[rows][collums];
        Random rand = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < collums; j++) {
                matrix[i][j] = rand.nextDouble();
            }
        }
    }
    
    public int getRows(){
        return matrix.length;
    }
    public int getCols(){
        return matrix[0].length;
    }
    public double getData(int row, int col){
        return matrix[row][col];
    }
    public void setData(int row, int col, double data){
        matrix[row][col] = data;
    }
    
    public static double sumOddElements(Matrix m) {
        double sum = 0;
        for (double[] row : m.matrix) {
            for (double element : row) {
                if (Math.abs(element) % 2 != 0) {
                    sum += element;
                }
            }
        }
        return sum;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (double[] row : matrix) {
            for (double value : row) {
                sb.append(String.format("%.2f ", value));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
