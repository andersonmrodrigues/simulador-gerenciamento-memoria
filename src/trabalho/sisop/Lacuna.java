/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho.sisop;

public class Lacuna {

    private Integer qtd;
    private Integer posInicio;
    private Integer posfim;

    Lacuna(int qtd, Integer posInicio, Integer posFim) {
        this.qtd = qtd;
        this.posInicio = posInicio;
        this.posfim = posFim;
    }

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    public Integer getPosInicio() {
        return posInicio;
    }

    public void setPosInicio(Integer posInicio) {
        this.posInicio = posInicio;
    }

    public Integer getPosfim() {
        return posfim;
    }

    public void setPosfim(Integer posfim) {
        this.posfim = posfim;
    }

}
