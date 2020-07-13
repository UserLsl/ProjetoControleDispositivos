package br.edu.fatec.projeto.dto;

import java.util.Date;

public class Lancamento {
    private int idLancamento;
    private Funcionario funcionario;
    private Dispositivo dispositivo;
    private String login;
    private String senha;
    private Date dataLancamento;
    private String observacao;

    public Lancamento() { }

    public Lancamento(int idLancamento, Funcionario funcionario, Dispositivo dispositivo, String login, String senha, Date dataLancamento, String observacao) {
        this.idLancamento = idLancamento;
        this.funcionario = funcionario;
        this.dispositivo = dispositivo;
        this.login = login;
        this.senha = senha;
        this.dataLancamento = dataLancamento;
        this.observacao = observacao;
    }

    public int getIdLancamento() {
        return idLancamento;
    }

    public void setIdLancamento(int idLancamento) {
        this.idLancamento = idLancamento;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
