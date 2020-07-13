package br.edu.fatec.projeto.dto;

import java.util.Date;

public class Devolucao {
    private int idDevolucao;
    private Funcionario funcionario;
    private Dispositivo dispositivo;
    private Date dataDevolucao;
    private String observacao;

    public Devolucao() { }

    public Devolucao(int idDevolucao, Funcionario funcionario, Dispositivo dispositivo, Date dataDevolucao, String observacao) {
        this.idDevolucao = idDevolucao;
        this.funcionario = funcionario;
        this.dispositivo = dispositivo;
        this.dataDevolucao = dataDevolucao;
        this.observacao = observacao;
    }

    public int getIdDevolucao() {
        return idDevolucao;
    }

    public void setIdDevolucao(int idDevolucao) {
        this.idDevolucao = idDevolucao;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
