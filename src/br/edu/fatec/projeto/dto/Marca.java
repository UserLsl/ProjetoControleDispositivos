package br.edu.fatec.projeto.dto;

public class Marca {
    private int idMarca;
    private String nome;

    public Marca(int idMarca, String nome) {
        this.idMarca = idMarca;
        this.nome = nome;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
