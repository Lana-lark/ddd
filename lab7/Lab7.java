/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.lab7;

import javax.swing.SwingUtilities;

/**
 *
 * @author Милана
 */
public class Lab7 {

    public static void main(String[] args) {
        Matrix m = new Matrix(4,8);
        System.out.println("Rows: " + m.getRows() + "\nCols: " + m.getCols());
        SwingUtilities.invokeLater(MatrixClient::new);
    }
}
