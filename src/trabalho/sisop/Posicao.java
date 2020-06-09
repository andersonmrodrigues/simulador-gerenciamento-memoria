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
public class Posicao {

    private String pid;
    private String value;

    Posicao(String pid) {
        this.pid = pid;
        this.value = "";
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
