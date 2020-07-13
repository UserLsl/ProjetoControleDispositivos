package br.edu.fatec.projeto.dto;

import java.util.Date;

public class Movimentacao {
    private int IdLancamento;
    private Date DataLancamento;
    private Date DataDevolucao;
    private String nome;
    private String marca;
    private String modelo;
    private String status;

    public Movimentacao() { }

    public Movimentacao(int idLancamento, Date dataLancamento, Date dataDevolucao, String nome, String marca, String modelo, String status) {
        IdLancamento = idLancamento;
        DataLancamento = dataLancamento;
        DataDevolucao = dataDevolucao;
        this.nome = nome;
        this.marca = marca;
        this.modelo = modelo;
        this.status = status;
    }

    public Date getDataLancamento() {
        return DataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        DataLancamento = dataLancamento;
    }

    public Date getDataDevolucao() {
        return DataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        DataDevolucao = dataDevolucao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdLancamento() {
        return IdLancamento;
    }

    public void setIdLancamento(int idLancamento) {
        IdLancamento = idLancamento;
    }
}
