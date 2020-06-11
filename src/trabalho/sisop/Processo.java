/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho.sisop;

/**
 *
 * @author Anderson Rodrigues
 */
public class Processo {

    private String pid;
    private Integer qtMem;
    private Integer posInicio;
    private Integer posFim;
    private String instrucao;

    Processo() {
        this.instrucao = "";
    }

    Processo(String pid, Integer qtMem, Integer inicio, Integer fim) {
        this.pid = pid;
        this.qtMem = qtMem;
        this.posInicio = inicio;
        this.posFim = fim;
        this.instrucao = "";
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getPosInicio() {
        return posInicio;
    }

    public void setPosInicio(Integer posInicio) {
        this.posInicio = posInicio;
    }

    public Integer getPosFim() {
        return posFim;
    }

    public void setPosFim(Integer posFim) {
        this.posFim = posFim;
    }

    public Integer getQtMem() {
        return qtMem;
    }

    public void setQtMem(Integer qtMem) {
        this.qtMem = qtMem;
    }

    public String getInstrucao() {
        return instrucao;
    }

    public void setInstrucao(String instrucao) {
        this.instrucao = instrucao;
    }

}
