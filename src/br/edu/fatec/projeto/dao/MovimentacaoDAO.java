package br.edu.fatec.projeto.dao;

import br.edu.fatec.projeto.dto.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovimentacaoDAO {

    public List<Movimentacao> getMovimentacoes() {
        return selectStatement("SELECT IdLancamento, DataLancamento, DataDevolucao, Nome, Marca, Modelo, Status FROM vw_MovimentacaoCompleta ORDER BY DataLancamento DESC", null, null);
    }

    public List<Movimentacao> getMovimentacao(int IdLancamento) {
        return selectStatement("SELECT IdLancamento, DataLancamento, DataDevolucao, Nome, Marca, Modelo, Status FROM vw_MovimentacaoCompleta WHERE IdLancamento = ? ORDER BY DataLancamento DESC", 1, IdLancamento);
    }

    public List<Movimentacao> selectStatement(String command, Integer indexidLancamento, Integer idLancamento) {
        List<Movimentacao> movimentacoes = new ArrayList<>();

        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement statement = conn.prepareStatement(command);
            if (idLancamento != null) statement.setInt(indexidLancamento, idLancamento);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                movimentacoes.add(new Movimentacao(rs.getInt("IdLancamento"),
                        rs.getDate("DataLancamento"),
                        rs.getDate("DataDevolucao"),
                        rs.getString("Nome"),
                        rs.getString("Marca"),
                        rs.getString("Modelo"),
                        rs.getString("Status")));
            }
        } catch (SQLException e) {
            System.out.println("Erro Movimentação DAO: " + e.getMessage());
        }
        return movimentacoes;
    }
}
