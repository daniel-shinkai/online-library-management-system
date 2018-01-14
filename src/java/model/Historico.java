
package model;

import java.sql.Date;

/**
 *
 * @author daniel
 */
public class Historico {
    
    private int cod_emprestimo, ISBN_exemplar, num_exemplar, ano;
    private String titulo, editora;
    private Date data_emp;
    String data_dev;

    public Historico(int cod_emprestimo, int ISBN_exemplar, int num_exemplar, int ano, String titulo, String editora, Date data_emp, String data_dev) {
        this.cod_emprestimo = cod_emprestimo;
        this.ISBN_exemplar = ISBN_exemplar;
        this.num_exemplar = num_exemplar;
        this.ano = ano;
        this.titulo = titulo;
        this.editora = editora;
        this.data_emp = data_emp;
        this.data_dev = data_dev;
    }

    public int getCod_emprestimo() {
        return cod_emprestimo;
    }

    public void setCod_emprestimo(int cod_emprestimo) {
        this.cod_emprestimo = cod_emprestimo;
    }

    public int getISBN_exemplar() {
        return ISBN_exemplar;
    }

    public void setISBN_exemplar(int ISBN_exemplar) {
        this.ISBN_exemplar = ISBN_exemplar;
    }

    public int getNum_exemplar() {
        return num_exemplar;
    }

    public void setNum_exemplar(int num_exemplar) {
        this.num_exemplar = num_exemplar;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public Date getData_emp() {
        return data_emp;
    }

    public void setData_emp(Date data_emp) {
        this.data_emp = data_emp;
    }

    public String getData_dev() {
        return data_dev;
    }

    public void setData_dev(String data_dev) {
        this.data_dev = data_dev;
    }
    
}
