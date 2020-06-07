/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho.sisop;

import java.util.Scanner;

/**
 *
 * @author Anderson Rodrigues
 */
public class TrabalhoSisop {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            System.out.println("Escolha o Algoritmo de Gerenciamento de Partições: ");
            System.out.println("1: FIRST-FIT");
            System.out.println("2: BEST-FIT");
            System.out.println("3: WORST-FIT");
            System.out.println("4: CIRCULAR-FIT");
            Scanner in = new Scanner(System.in);
            int valorDoConsole = in.nextInt();
            if (valorDoConsole > 4 || valorDoConsole < 1) {
                System.out.println("Algoritmo inválido!");
            } else {
                Memoria memoria = new Memoria(valorDoConsole);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
