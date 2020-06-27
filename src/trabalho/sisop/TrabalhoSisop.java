/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho.sisop;

import java.util.Scanner;
 
public class TrabalhoSisop {
    public static void main(String[] args) {
        try {
            String console = "Escolha o Algoritmo de Gerenciamento de Partições: " + System.lineSeparator()
                    + "1: FIRST-FIT" + System.lineSeparator()
                    + "2: BEST-FIT" + System.lineSeparator()
                    + "3: WORST-FIT" + System.lineSeparator()
                    + "4: CIRCULAR-FIT" + System.lineSeparator();
            System.out.println(console);
            Scanner in = new Scanner(System.in);
            int valorDoConsole = in.nextInt();
            if (valorDoConsole > 4 || valorDoConsole < 1) {
                System.out.println("Algoritmo inválido!");
            } else {
                console += "ALGORITMO ESCOLHIDO = 4" + System.lineSeparator();
                Memoria memoria = new Memoria(valorDoConsole, console);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

