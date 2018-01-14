/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author daniel
 */
public class Devolucao {
    
    private int cod_emprestimo, codigo_func;
    private Date prazo_dev, data_dev;

    public Devolucao(int cod_emprestimo, int codigo_func, Date prazo_dev, Date data_dev) {
        this.cod_emprestimo = cod_emprestimo;
        this.codigo_func = codigo_func;
        this.prazo_dev = prazo_dev;
        this.data_dev = data_dev;
    }

    public int getCod_emprestimo() {
        return cod_emprestimo;
    }

    public void setCod_emprestimo(int cod_emprestimo) {
        this.cod_emprestimo = cod_emprestimo;
    }

    public int getCodigo_func() {
        return codigo_func;
    }

    public void setCodigo_func(int codigo_func) {
        this.codigo_func = codigo_func;
    }

    public Date getPrazo_dev() {
        return prazo_dev;
    }

    public void setPrazo_dev(Date prazo_dev) {
        this.prazo_dev = prazo_dev;
    }

    public Date getData_dev() {
        return data_dev;
    }

    public void setData_dev(Date data_dev) {
        this.data_dev = data_dev;
    }
   
}
