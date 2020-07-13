package br.edu.fatec.projeto.dao;

import br.edu.fatec.projeto.dto.Departamento;
import br.edu.fatec.projeto.dto.Funcionario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public List<Funcionario> getFuncionario(String nome) {
        return selectStatement("SELECT IdFuncionario, Funcionario.Nome, Departamento.IdDepartamento, Departamento.Nome FROM Funcionario INNER JOIN Departamento on Funcionario.IdDepartamento = Departamento.IdDepartamento WHERE Nome LIKE ?", null, null, 1, nome);
    }

    public List<Funcionario> getFuncionario(int codigo) {
        return selectStatement("SELECT IdFuncionario, Funcionario.Nome as NomeFunc, Departamento.IdDepartamento as IdDepartamento, Departamento.Nome as NomeDepartamento FROM Funcionario INNER JOIN Departamento on Funcionario.IdDepartamento = Departamento.IdDepartamento WHERE IdFuncionario = ?", 1, codigo, null, null);
    }

    public List<Funcionario> getFuncionarios() {
        return selectStatement("SELECT DISTINCT IdFuncionario, Funcionario.Nome as NomeFunc, Departamento.IdDepartamento as IdDepartamento, Departamento.Nome as NomeDepartamento FROM Funcionario INNER JOIN Departamento on Funcionario.IdDepartamento = Departamento.IdDepartamento", null, null, null, null);
    }

    public List<Funcionario> getFuncionariosPendentes() {
        return selectStatement("SELECT DISTINCT Funcionario.IdFuncionario as IdFuncionario, Funcionario.Nome as NomeFunc, Departamento.IdDepartamento as IdDepartamento, Departamento.Nome as NomeDepartamento FROM Funcionario INNER JOIN Departamento on Funcionario.IdDepartamento = Departamento.IdDepartamento INNER JOIN Lancamento on Lancamento.IdFuncionario = Funcionario.IdFuncionario WHERE Status = 'P'", null, null, null, null);
    }

    public List<Funcionario> selectStatement(String command, Integer indexIdFuncionario, Integer idFuncionario, Integer indexNome, String nome) {
        List<Funcionario> funcionarios = new ArrayList<>();

        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement statement = conn.prepareStatement(command);
            if (idFuncionario != null) statement.setInt(indexIdFuncionario, idFuncionario);
            if (nome != null) statement.setString(indexNome, "%" + nome + "%");
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                funcionarios.add(new Funcionario(rs.getInt("IdFuncionario"),
                        rs.getString("NomeFunc"),
                        new Departamento(rs.getInt("IdDepartamento"), rs.getString("NomeDepartamento"))));
            }
        } catch (SQLException e) {
            System.out.println("Erro Dispositivo DAO: " + e.getMessage());
        }
        return funcionarios;
    }

    public int setFuncionario(Funcionario funcionario) {
        int codFuncionario = funcionario.getIdFuncionario();
        String query = "SELECT * FROM Funcionario WHERE IdFuncionario = ?";

        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, codFuncionario);

            if(!statement.executeQuery().next()) {
                query = "INSERT INTO funcionario (Nome, IdDepartamento) " +
                        "VALUES (?, ?)";
                PreparedStatement statementInsert = conn.prepareStatement(query);
                statementInsert.setString(1, funcionario.getNome());
                statementInsert.setInt(2, funcionario.getDepartamento().getIdDepartamento());
                return statementInsert.executeUpdate();
            } else {
                query = "UPDATE funcionario SET Nome = ?, IdDepartamento = ? WHERE IdFuncionario = ?";
                PreparedStatement statementUpdate = conn.prepareStatement(query);
                statementUpdate.setString(1, funcionario.getNome());
                statementUpdate.setInt(2, funcionario.getDepartamento().getIdDepartamento());
                statementUpdate.setInt(3, codFuncionario);
                return statementUpdate.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int delFuncionario(int codFuncionario) { //Retorna número de linhas alteradas. "-1" deu erro
        String query = "DELETE FROM Funcionario WHERE IdFuncionario = ?";
        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, codFuncionario);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
