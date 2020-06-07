/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho.sisop;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Anderson Rodrigues
 */
public class Memoria {

    private static final String NOME_ARQUIVO = "memoria.txt";
    private static final String CAMINHO_ARQUIVO = "D:\\Projetos\\simulador-gerenciamento-memoria\\";
    private File file;

    Memoria() throws IOException {
        boolean existe = verificaGeraArquivo();
        if (!existe) {
            preencheMetadeDoArquivo();
        }
    }

    private boolean verificaGeraArquivo() throws IOException {
        file = new File(CAMINHO_ARQUIVO + NOME_ARQUIVO);
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

}
