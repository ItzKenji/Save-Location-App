package br.usjt.ucsist.savelocationusjtql.model;

import java.util.Date;

public class Local {

    private String dadosLongitude;
    private String dadosLatitude;
    private String titulo;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String dataCadastro;

    public Local(){}

    public Local(String dadosLongitude, String dadosLatitude, String titulo, String rua, String numero, String bairro, String cidade, String estado, String dataCadastro) {
        this.dadosLongitude = dadosLongitude;
        this.dadosLatitude = dadosLatitude;
        this.titulo = titulo;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.dataCadastro = dataCadastro;
    }


    public String getDadosLongitude() {
        return dadosLongitude;
    }

    public void setDadosLongitude(String dadosLongitude) {
        this.dadosLongitude = dadosLongitude;
    }

    public String getDadosLatitude() {
        return dadosLatitude;
    }

    public void setDadosLatitude(String dadosLatitude) {
        this.dadosLatitude = dadosLatitude;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}