/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho.sisop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Anderson Rodrigues
 */
public class Memoria {

    private static final String NOME_ARQUIVO_MEMORIA = "memoria.txt";
    private static final String NOME_ARQUIVO_PROCESSOS = "processos1.txt";
    private static final String CAMINHO = "D:\\Projetos\\simulador-gerenciamento-memoria\\";
    private File file;
    public static final Integer FIRST_FIT = 1;
    public static final Integer BEST_FIT = 2;
    public static final Integer WORST_FIT = 3;
    public static final Integer CIRCULAR_FIT = 4;

    Memoria(Integer algoritmo) throws IOException {
        boolean existe = verificaGeraArquivo();
        if (!existe) {
            preencheMetadeDoArquivo();
        }
        leitorDeProcessos();
    }

    private boolean verificaGeraArquivo() throws IOException {
        file = new File(CAMINHO + NOME_ARQUIVO_MEMORIA);
        if (!file.exists()) {
            file.createNewFile();
            return false;
        }
        return true;
    }

    private void preencheMetadeDoArquivo() throws IOException {
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        String content = geraDados();
        bw.write(content);
        bw.close();
    }

    private String geraDados() {
        String data = "";
        for (int i = 0; i < 1000000; i++) {
            data += i < 500000 ? "x" : " ";
        }
        return data;
    }

    private void leitorDeProcessos() throws FileNotFoundException, IOException {
        BufferedReader br = null;
        String linha = "";
        String divisor = "\\|";
        br = new BufferedReader(new FileReader(CAMINHO + NOME_ARQUIVO_PROCESSOS));
        while ((linha = br.readLine()) != null) {
            String[] data = linha.split(divisor);
        }
    }

}
