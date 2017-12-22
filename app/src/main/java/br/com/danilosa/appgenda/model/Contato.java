package br.com.danilosa.appgenda.model;

import java.io.Serializable;

import br.com.danilosa.appgenda.exceptions.ValorInvalidoException;

/**
 * Created by danil on 27/02/2017.
 */

public class Contato implements Comparable<Contato>, Serializable{
    private String nome;
    private String telefone;
    private String endereco;
    private String site;
    private String dataNascimento;
    private Double nota;

    public Contato(String nome, String telefone, String endereco, String site, String dataNascimento, Double nota) throws ValorInvalidoException{
        if (nome.isEmpty() || telefone.isEmpty())
            throw new ValorInvalidoException();
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.site = site;
        this.dataNascimento = dataNascimento;
        this.nota = nota;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return nome + " - " + nota;
    }

    @Override
    public int compareTo(Contato o) {
        if(this.nome.compareToIgnoreCase(o.getNome()) > 0)
        return 1;
        if(this.nome.compareToIgnoreCase(o.getNome()) == 0)
            return 0;
        return -1;
    }
}
