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
public class Emprestimo {

    private int cod_emprestimo,  codigo_assoc, codigo_func;
    private int num_exmeplar, ISBN_exemplar;
    private Date data_emp, prazo_dev;
    private char finalizado;

    public Emprestimo(int codigo_assoc, int codigo_func, int num_exmeplar, int ISBN_exemplar, Date data_emp,
            Date prazo_dev, char finalizado) {
        this.codigo_assoc = codigo_assoc;
        this.codigo_func = codigo_func;
        this.num_exmeplar = num_exmeplar;
        this.ISBN_exemplar = ISBN_exemplar;
        this.data_emp = data_emp;
        this.prazo_dev = prazo_dev;
        this.finalizado = finalizado;
    }
    public Emprestimo(){}; 
    
    public int getCod_emprestimo() {
        return cod_emprestimo;
    }

    public void setCod_emprestimo(int cod_emprestimo) {
        this.cod_emprestimo = cod_emprestimo;
    }

   

    public int getCodigo_assoc() {
        return codigo_assoc;
    }

    public void setCodigo_assoc(int codigo_assoc) {
        this.codigo_assoc = codigo_assoc;
    }

    public int getCodigo_func() {
        return codigo_func;
    }

    public void setCodigo_func(int codigo_func) {
        this.codigo_func = codigo_func;
    }

    public int getNum_exmeplar() {
        return num_exmeplar;
    }

    public void setNum_exmeplar(int num_exmeplar) {
        this.num_exmeplar = num_exmeplar;
    }

    public int getISBN_exemplar() {
        return ISBN_exemplar;
    }

    public void setISBN_exemplar(int ISBN_exemplar) {
        this.ISBN_exemplar = ISBN_exemplar;
    }

    public Date getData_emp() {
        return data_emp;
    }

    public void setData_emp(Date data_emp) {
        this.data_emp = data_emp;
    }

    public Date getPrazo_dev() {
        return prazo_dev;
    }

    public void setPrazo_dev(Date prazo_dev) {
        this.prazo_dev = prazo_dev;
    }

    public char getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(char finalizado) {
        this.finalizado = finalizado;
    }

}
