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
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Anderson Rodrigues
 */
public class Memoria {

    private static final String NOME_ARQUIVO_MEMORIA = "memoria.txt";
    private static final String NOME_ARQUIVO_PROCESSOS1 = "processos1.txt";
    private static final String NOME_ARQUIVO_PROCESSOS2 = "processos2.txt";
    private static final String CAMINHO = "D:\\Projetos\\simulador-gerenciamento-memoria\\";
    public static final Integer FIRST_FIT = 1;
    public static final Integer BEST_FIT = 2;
    public static final Integer WORST_FIT = 3;
    public static final Integer CIRCULAR_FIT = 4;
    private Integer inicio;
    private Integer fim;
    private Integer algSelecionado;
    private String content;
    private String nmArquivoMemoria;
    private BufferedReader brArqMemoria;
    private StringBuilder sb;
    private File file;
    private FileWriter writer;
    private FileReader frMemoria;
    private List<Lacuna> lacunaList;
    private List<Posicao> memoriaList;
    private Queue<Processo> fifo;

    Memoria(Integer algoritmo) throws IOException {
        fifo = new LinkedList<>();
        memoriaList = new ArrayList<>(Collections.nCopies(1000000, null));
        lacunaList = new ArrayList<>();
        this.algSelecionado = algoritmo;
        boolean existe = verificaGeraArquivo();
        if (!existe) {
            preencheMetadeDoArquivo();
        }
        leitorDeProcessos();
    }

    private void leitorDeProcessos() throws FileNotFoundException, IOException {
        BufferedReader brProcessoArq1, brProcessoArq2 = null;
        String linha = "";
        String divisor = "\\|";
        brProcessoArq1 = new BufferedReader(new FileReader(CAMINHO + NOME_ARQUIVO_PROCESSOS1));
        brProcessoArq2 = new BufferedReader(new FileReader(CAMINHO + NOME_ARQUIVO_PROCESSOS2));
        nmArquivoMemoria = CAMINHO + NOME_ARQUIVO_MEMORIA;
        preencheInicioFimDaMemoria();
        System.out.println("------------");
        while ((linha = brProcessoArq1.readLine()) != null) {
            String[] data = linha.split(divisor);
            Integer posFim = inicio + Integer.valueOf(data[1]) - 1;
            Processo processo = new Processo(data[0], Integer.valueOf(data[1]), inicio, posFim);
            for (int i = inicio; i <= processo.getPosFim(); i++) {
                Posicao p = new Posicao(processo.getPid());
                memoriaList.set(i, p);
            }
            System.out.println("MEMORIA ALOCADA PARA O PROCESSO " + processo.getPid()
                    + " - INICIO:" + (processo.getPosInicio() + 1) + " | FIM:" + (processo.getPosFim() + 1));
            inicio = processo.getPosFim() + 1;
            verificaProcesso(data, processo);
            System.out.println("------------");
        }
        System.out.println("ACABOU ARQUIVO 1");
        while ((linha = brProcessoArq2.readLine()) != null) {
            String[] data = linha.split(divisor);
            Processo processo = new Processo(data[0], Integer.valueOf(data[1]), null, null);
            if (algSelecionado.equals(BEST_FIT)) {
                processo = bestFit(processo);
            } else if (algSelecionado.equals(FIRST_FIT)) {
                processo = firstFit(processo);
            } else if (algSelecionado.equals(WORST_FIT)) {
                processo = worstFit(processo);
            } else if (algSelecionado.equals(CIRCULAR_FIT)) {
                processo = circularFit(processo);
            }
            for (int i = processo.getPosInicio(); i <= processo.getPosFim(); i++) {
                Posicao p = new Posicao(processo.getPid());
                memoriaList.set(i, p);
            }
            System.out.println("MEMORIA ALOCADA PARA O PROCESSO " + processo.getPid()
                    + " - INICIO:" + (processo.getPosInicio() + 1) + " | FIM:" + (processo.getPosFim()));
            inicio = processo.getPosFim() + 1;
            verificaProcesso(data, processo);
            System.out.println("------------");
        }
        String acabou = "";
//        for (Processo processo : fifo) {
//            System.out.println("PROCESSO " + processo.getPid() + " " + processo.getPosInicio() + " " + processo.getPosFim());
//        }
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
        for (int i = 1; i <= 1000000; i++) {
            data += i < 500000 ? "x" : " ";
        }
        return data;
    }

    private void preencheInicioFimDaMemoria() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(CAMINHO + NOME_ARQUIVO_MEMORIA));
        StringBuffer sb = new StringBuffer();
        String str = br.readLine();
        sb.append(str);
        char[] data = str.toCharArray();
        inicio = str.lastIndexOf("x") + 1;
        fim = data.length;
    }

    private void carregaVariaveisParaLeituraAndEscrita() throws IOException {
        frMemoria = new FileReader(CAMINHO + NOME_ARQUIVO_MEMORIA);
        brArqMemoria = new BufferedReader(frMemoria);
        content = brArqMemoria.readLine();
        sb = new StringBuilder(content);
    }

    private void encerraEscritaNoArquivo() throws IOException {
        brArqMemoria.close();
        writer = new FileWriter(nmArquivoMemoria);
        writer.write(sb.toString());
        writer.close();
    }

    private void percorreAndPreecheArquivoByFgLacuna(Processo processo, boolean fgLacuna, int pos, String[] palavra) throws IOException {
        carregaVariaveisParaLeituraAndEscrita();
        Integer insereApartirDe = fgLacuna ? processo.getPosInicio() : (processo.getPosInicio() + pos);
        Integer acabaEm = fgLacuna ? processo.getPosFim() : (insereApartirDe + 3);
        int cont = 0;
        for (int j = insereApartirDe; j <= acabaEm; j++) {
            Posicao p = memoriaList.get(j);
            p.setValue(fgLacuna ? "L" : palavra[cont]);
            memoriaList.set(j, p);
            sb.setCharAt(j, fgLacuna ? 'L' : palavra[cont].charAt(0));
            cont++;
        }
        if (fgLacuna) {
            lacunaList.add(new Lacuna(cont, processo.getPosInicio(), processo.getPosFim()));
        }
        encerraEscritaNoArquivo();
    }

    private void verificaProcesso(String[] data, Processo processo) throws IOException {
        boolean es = false;
        boolean acessoIlegal = false;
        for (int i = 2; i < data.length; i++) {
            if (data[i].equals("-")) {
                continue;
            }
            if (es) {
                String instrucao = processo.getInstrucao().isEmpty() ? data[i] : "|" + data[i];
                processo.setInstrucao(processo.getInstrucao() + instrucao);
                continue;
            }
            if (data[i].equals("ES")) {
                es = true;
                System.out.println("ENTRADA/SAIDA DO PROCESSO " + processo.getPid() + "\nENVIADO PARA O FIM DA FILA");
            } else if (data[i].startsWith("sw")) {
                String[] instrucao = data[i].split(",");
                String[] palavra = instrucao[1].split("");
                int pos = Integer.valueOf(instrucao[2]) - 1;
                if ((processo.getPosInicio() + pos + 3) > processo.getPosFim()) {
                    //ACESSO ILEGAL TO DO
                    System.out.println("ACESSO ILEGAL DO PROCESSO " + processo.getPid()
                            + " NA POSICAO " + (processo.getPosInicio() + pos + 1) + " LACUNA DE "
                            + processo.getQtMem() + "bits\nPROCESSO ENCERRADO");
                    percorreAndPreecheArquivoByFgLacuna(processo, true, 0, null);
                    break;
                } else {
                    percorreAndPreecheArquivoByFgLacuna(processo, false, pos, palavra);
                    System.out.println("STOREWORD DO PROCESSO " + processo.getPid()
                            + " DA PALAVRA " + instrucao[1] + " NA POSICAO "
                            + (processo.getPosInicio() + pos + 1));
                }

            } else if (data[i].startsWith("lw")) {
                String[] instrucao = data[i].split(",");
                int pos = Integer.valueOf(instrucao[1]);
                Integer posicaoNoArq = processo.getPosInicio() + pos - 1;
                carregaVariaveisParaLeituraAndEscrita();
                String valorNaPosicao = memoriaList.get(posicaoNoArq).getValue();
                String valorNaPosicaoNoArq = String.valueOf(sb.charAt(posicaoNoArq));
                System.out.println("LOADWORD DO PROCESSO " + processo.getPid()
                        + " DA POSICAO " + posicaoNoArq);

            }
            if (i == (data.length - 1) && !es) {
                System.out.println("PROCESSO ENCERRADO " + processo.getPid()
                        + "\nLACUNA DE " + processo.getQtMem() + "bits");
                percorreAndPreecheArquivoByFgLacuna(processo, true, 0, null);
                break;
            }
        }
        if (es) {
            this.fifo.add(processo);
        }
    }

    private Processo bestFit(Processo processo) {
        return processo;
    }

    private Processo firstFit(Processo processo) {
        for (int i = 0; i < lacunaList.size(); i++) {
            if (lacunaList.get(i) != null && lacunaList.get(i).getQtd() >= processo.getQtMem()) {
                processo.setPosInicio(lacunaList.get(i).getPosInicio());
                processo.setPosFim(processo.getPosInicio() + processo.getQtMem());
                if (lacunaList.get(i).getQtd() > processo.getQtMem()) {
                    lacunaList.get(i).setPosInicio(processo.getPosFim());
                    lacunaList.get(i).setQtd(lacunaList.get(i).getQtd() - processo.getQtMem());
                } else {
                    lacunaList.remove(i);
                }
                return processo;
            }

        }
        return processo;
    }

    private Processo worstFit(Processo processo) {
        return processo;
    }

    private Processo circularFit(Processo processo) {
        return processo;
    }

}
