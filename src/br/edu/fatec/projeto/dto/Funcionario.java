package br.edu.fatec.projeto.dto;

public class Funcionario {
    private int idFuncionario;
    private String nome;
    private Departamento departamento;

    public Funcionario(int idFuncionario, String nome, Departamento departamento) {
        this.idFuncionario = idFuncionario;
        this.nome = nome;
        this.departamento = departamento;
    }

    public Funcionario(int idFuncionario, String nome) {
        this.idFuncionario = idFuncionario;
        this.nome = nome;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
}
