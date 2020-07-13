package br.edu.fatec.projeto.dao;

import br.edu.fatec.projeto.dto.*;
import br.edu.fatec.projeto.exception.PersistenciaException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DevolucaoDAO {

    public List<Devolucao> getDevolucao(int idDevolucao) {
        return selectStatement("", 1, idDevolucao);
    }

    public List<Devolucao> getDevolucoes() {
        return selectStatement("SELECT IdDevolucao, Funcionario.IdFuncionario, Funcionario.Nome as NomeFuncionario, Dispositivo.IdDispositivo, Modelo, Marca.IdMarca as IdMarca, Marca.Nome as NomeMarca, Processador, MemoriaRam, Armazenamento, ServiceTag, TamanhoTela, Dispositivo.Data, DataDevolucao, Observacao FROM Devolucao INNER JOIN Funcionario ON Devolucao.IdFuncionario = Funcionario.IdFuncionario INNER JOIN Dispositivo ON Devolucao.IdDispositivo = Dispositivo.IdDispositivo INNER JOIN Marca ON Dispositivo.IdMarca = Marca.IdMarca", null, null);
    }

    public List<Devolucao> selectStatement(String command, Integer indexidDevolucao, Integer idDevolucao) {
        List<Devolucao> devolucoes = new ArrayList<>();

        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement statement = conn.prepareStatement(command);
            if (idDevolucao != null) statement.setInt(indexidDevolucao, idDevolucao);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                devolucoes.add(new Devolucao(rs.getInt("idDevolucao"),
                        new Funcionario(rs.getInt("IdFuncionario"), rs.getString("NomeFuncionario")),
                        new Dispositivo(rs.getInt("IdDispositivo"),
                                rs.getString("Modelo"),
                                new Marca(rs.getInt("IdMarca"), rs.getString("NomeMarca")),
                                rs.getString("Processador"),
                                rs.getString("MemoriaRam"),
                                rs.getString("Armazenamento"),
                                rs.getString("ServiceTag"),
                                rs.getString("TamanhoTela"),
                                rs.getDate("Data")),
                        rs.getDate("DataDevolucao"),
                        rs.getString("Observacao")));
            }
        } catch (SQLException e) {
            System.out.println("Erro Lancamento DAO: " + e.getMessage());
        }
        return devolucoes;
    }

    public void inserir(Devolucao obj) throws PersistenciaException {
        String sql = "INSERT INTO Devolucao " +
                "VALUES (?, ?, GETDATE(), ?)";
        try {
            PreparedStatement insert = Conexao.getConexao().prepareStatement(sql);
            insert.setInt(1, obj.getFuncionario().getIdFuncionario());
            insert.setInt(2, obj.getDispositivo().getIdDispositivos());
            insert.setString(3, obj.getObservacao());

            if(insert.executeUpdate() == 0) {
                throw new PersistenciaException("Lançamento de devolução não inserido");
            }
        } catch (SQLException | PersistenciaException throwables) {
            throw new PersistenciaException("Erro crítico ao salvar devolução!");
        }
    }
}
