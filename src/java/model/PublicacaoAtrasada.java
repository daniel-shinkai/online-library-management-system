/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import java.text.DateFormat;
import java.util.Locale;

/**
 *
 * @author Igor Santos
 */
public class PublicacaoAtrasada extends Publicacao {

    public PublicacaoAtrasada(int numExemplar, Date dataEmp, Date prazoDev, int isbn, String titulo, String autor, String editora, int ano) {
        super(isbn, titulo, autor, editora, ano);
        this.numExemplar = numExemplar;
        this.dataEmp = dataEmp;
        this.prazoDev = prazoDev;
    }

    public int getNumExemplar() {
        return numExemplar;
    }

    public void setNumExemplar(int numExemplar) {
        this.numExemplar = numExemplar;
    }

    public String getDataEmp() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("pt", "BR"));
        String dataFormatada = df.format(dataEmp);
        return dataFormatada;
    }

    public void setDataEmp(Date dataEmp) {
        this.dataEmp = dataEmp;
    }

    public String getPrazoDev() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("pt", "BR"));
        String dataFormatada = df.format(prazoDev);
        return dataFormatada;
    }

    public void setPrazoDev(Date prazoDev) {
        this.prazoDev = prazoDev;
    }

    int numExemplar;
    Date dataEmp;
    Date prazoDev;

}
