/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Igor Santos
 */
public class AssociadoAtraso {

    public AssociadoAtraso(int codigo, String nome, String sobrenome) {
        this.codigo = codigo;
        this.nome = nome;
        this.sobrenome = sobrenome;
        pAtrasada = new ArrayList<>();
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public List<PublicacaoAtrasada> getpAtrasada() {
        return pAtrasada;
    }

    public void setpAtrasada(PublicacaoAtrasada pAtrasada) {
        this.pAtrasada.add(pAtrasada);
    }

    int codigo;
    String nome;
    String sobrenome;
    List<PublicacaoAtrasada> pAtrasada;
}
