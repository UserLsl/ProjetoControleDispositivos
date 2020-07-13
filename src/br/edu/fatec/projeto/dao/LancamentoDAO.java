package br.edu.fatec.projeto.dao;

import br.edu.fatec.projeto.dto.Dispositivo;
import br.edu.fatec.projeto.dto.Funcionario;
import br.edu.fatec.projeto.dto.Lancamento;
import br.edu.fatec.projeto.dto.Marca;
import br.edu.fatec.projeto.exception.PersistenciaException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LancamentoDAO {

    public List<Lancamento> getLancamento(int codLancamento) {
        return selectStatement("SELECT IdLancamento, Funcionario.IdFuncionario as IdFuncionario, Funcionario.Nome as NomeFuncionario, Dispositivo.IdDispositivo as IdDispositivo, Modelo, Marca.IdMarca as IdMarca, Marca.Nome as NomeMarca, Processador, MemoriaRam, Armazenamento, ServiceTag, TamanhoTela, Dispositivo.Data, Login, Senha, DataLancamento, Observacao FROM Lancamento INNER JOIN Funcionario ON Lancamento.IdFuncionario = Funcionario.IdFuncionario INNER JOIN Dispositivo ON Lancamento.IdDispositivo = Dispositivo.IdDispositivo INNER JOIN Marca ON Dispositivo.IdMarca = Marca.IdMarca", 1, codLancamento);
    }

    public List<Lancamento> getLancamentos() {
        return selectStatement("SELECT IdLancamento, Funcionario.IdFuncionario as IdFuncionario, Funcionario.Nome as NomeFuncionario, Dispositivo.IdDispositivo as IdDispositivo, Modelo, Marca.IdMarca as IdMarca, Marca.Nome as NomeMarca, Processador, MemoriaRam, Armazenamento, ServiceTag, TamanhoTela, Dispositivo.Data, Login, Senha, DataLancamento, Observacao FROM Lancamento INNER JOIN Funcionario ON Lancamento.IdFuncionario = Funcionario.IdFuncionario INNER JOIN Dispositivo ON Lancamento.IdDispositivo = Dispositivo.IdDispositivo INNER JOIN Marca ON Dispositivo.IdMarca = Marca.IdMarca", null, null);
    }

    public List<Lancamento> getMovimentosFuncionario(int codFuncionario) {
        return selectStatement("SELECT IdLancamento, Funcionario.IdFuncionario as IdFuncionario, Funcionario.Nome as NomeFuncionario, Dispositivo.IdDispositivo as IdDispositivo, Modelo, Marca.IdMarca as IdMarca, Marca.Nome as NomeMarca, Processador, MemoriaRam, Armazenamento, ServiceTag, TamanhoTela, Dispositivo.Data, Login, Senha, DataLancamento, Observacao FROM Lancamento INNER JOIN Funcionario ON Lancamento.IdFuncionario = Funcionario.IdFuncionario INNER JOIN Dispositivo ON Lancamento.IdDispositivo = Dispositivo.IdDispositivo INNER JOIN Marca ON Dispositivo.IdMarca = Marca.IdMarca WHERE Funcionario.IdFuncionario = ?", 1, codFuncionario);
    }

    public List<Lancamento> getMovimentosDispositivo(int codDispositivo) {
        return selectStatement("SELECT IdLancamento, Funcionario.IdFuncionario as IdFuncionario, Funcionario.Nome as NomeFuncionario, Dispositivo.IdDispositivo as IdDispositivo, Modelo, Marca.IdMarca as IdMarca, Marca.Nome as NomeMarca, Processador, MemoriaRam, Armazenamento, ServiceTag, TamanhoTela, Dispositivo.Data, Login, Senha, DataLancamento, Observacao FROM Lancamento INNER JOIN Funcionario ON Lancamento.IdFuncionario = Funcionario.IdFuncionario INNER JOIN Dispositivo ON Lancamento.IdDispositivo = Dispositivo.IdDispositivo INNER JOIN Marca ON Dispositivo.IdMarca = Marca.IdMarca WHERE Dispositivo.IdDispositivo = ?", 1, codDispositivo);
    }


    public List<Lancamento> selectStatement(String command, Integer index, Integer codigo) {
        List<Lancamento> lancamentos = new ArrayList<>();

        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement statement = conn.prepareStatement(command);
            if (codigo != null) statement.setInt(index, codigo);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                lancamentos.add(new Lancamento(rs.getInt("idLancamento"),
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
                        rs.getString("Login"),
                        rs.getString("Senha"),
                        rs.getDate("DataLancamento"),
                        rs.getString("Observacao")));
            }
        } catch (SQLException e) {
            System.out.println("Erro Lancamento DAO: " + e.getMessage());
        }
        return lancamentos;
    }

    public void inserir(Lancamento obj) throws PersistenciaException {
        String sql = "INSERT INTO Lancamento " +
                "VALUES (?, ?, ?, ?, GETDATE(), ?, ?, null)";
        try {
            PreparedStatement insert = Conexao.getConexao().prepareStatement(sql);
            insert.setInt(1, obj.getFuncionario().getIdFuncionario());
            insert.setInt(2, obj.getDispositivo().getIdDispositivos());
            insert.setString(3, obj.getLogin());
            insert.setString(4, obj.getSenha());
            insert.setString(5, obj.getObservacao());
            insert.setString(6, "P");

            if(insert.executeUpdate() == 0) {
                throw new PersistenciaException("Lançamento não inserido");
            }
        } catch (SQLException | PersistenciaException throwables) {
            throw new PersistenciaException("Erro crítico ao salvar!");
        }
    }
}
