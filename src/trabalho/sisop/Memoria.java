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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

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
    private Queue fifo;
    private List<Posicao> memoriaList;
    private Integer inicio;
    private Integer fim;
    private BufferedReader brArqMemoria;
    private String content;
    private StringBuilder sb;
    private FileWriter writer;
    private FileReader frMemoria;

    Memoria(Integer algoritmo) throws IOException {
        memoriaList = new ArrayList<>(Collections.nCopies(1000000, null));
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
        String nmArquivoMemoria = CAMINHO + NOME_ARQUIVO_MEMORIA;
        preencheInicioFimDaMemoria();
        while ((linha = br.readLine()) != null) {
            String[] data = linha.split(divisor);
            Processo processo = new Processo();
            processo.setPid(data[0]);
            processo.setQtMem(Integer.valueOf(data[1]));
            processo.setPosInicio(inicio);
            processo.setPosFim(inicio + processo.getQtMem());
            for (int i = inicio; i <= processo.getPosFim(); i++) {
                Posicao p = new Posicao(processo.getPid());
                memoriaList.set(i, p);
            }
            inicio = processo.getPosFim() + 1;
            for (int i = 2; i < data.length; i++) {
                if (data[i].equals("-")) {
                    continue;
                }
                if (data[i].startsWith("sw")) {
                    String[] instrucao = data[i].split(",");
                    String[] palavra = instrucao[1].split("");
                    int pos = Integer.valueOf(instrucao[2]) - 1;
                    if ((processo.getPosInicio() + pos + 3) > processo.getPosFim()) {
                        //ACESSO ILEGAL
                    } else {
                        carregaVariaveisParaLeituraAndEscrita();
                        Integer insereApartirDe = processo.getPosInicio() + pos;
                        Integer acabaEm = insereApartirDe + 3;
                        int cont = 0;
                        for (int j = insereApartirDe; j <= acabaEm; j++) {
                            Posicao p = memoriaList.get(j);
                            p.setValue(palavra[cont]);
                            memoriaList.set(j, p);
                            sb.setCharAt(j, palavra[cont].charAt(0));
                            cont++;
                        }
                        br.close();
                        writer = new FileWriter(nmArquivoMemoria);
                        writer.write(sb.toString());
                        writer.close();
                    }
                } else if (data[i].startsWith("lw")) {
                    String[] instrucao = data[i].split(",");
                    int pos = Integer.valueOf(instrucao[1]);
                    Integer posicaoNoArq = processo.getPosInicio() + pos;
                    carregaVariaveisParaLeituraAndEscrita();
                    String valorNaPosicao = memoriaList.get(posicaoNoArq).getValue(); // COLOCAR LÓGICA DE GERAR LOG OU PRINTAR NO CONSOLE
                    String valorNaPosicaoNoArq = String.valueOf(sb.charAt(Integer.valueOf(valorNaPosicao)));
                }
            }
            this.fifo.add(processo);
        }
    }

    private void preencheInicioFimDaMemoria() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(CAMINHO + NOME_ARQUIVO_MEMORIA));
        StringBuffer sb = new StringBuffer();
        String str = br.readLine();
        sb.append(str);
        char[] data = str.toCharArray();
        inicio = str.lastIndexOf("x") + 1; // ONDE ACABA O SISTEMA OPERACIONAL NA MEMORIA + 1 = ONDE PODE COMECAR A ESCRITA DOS PROCESSOS
        fim = data.length;
    }

    private void carregaVariaveisParaLeituraAndEscrita() throws IOException {
        frMemoria = new FileReader(CAMINHO + NOME_ARQUIVO_MEMORIA);
        brArqMemoria = new BufferedReader(frMemoria);
        content = brArqMemoria.readLine();
        sb = new StringBuilder(content);
    }
}
