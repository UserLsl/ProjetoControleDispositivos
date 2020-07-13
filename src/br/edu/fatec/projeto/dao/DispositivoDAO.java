package br.edu.fatec.projeto.dao;

import br.edu.fatec.projeto.dto.Dispositivo;
import br.edu.fatec.projeto.dto.Funcionario;
import br.edu.fatec.projeto.dto.Marca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DispositivoDAO {

    public List<Dispositivo> getDispositivo(String modelo) {
        return selectStatement("SELECT IdDispositivo, Modelo, Marca.IdMarca as IdMarca, Marca.Nome as Nome, Processador, MemoriaRam, Armazenamento, ServiceTag, TamanhoTela, Dispositivo.Data FROM Dispositivo INNER JOIN Marca ON Dispositivo.IdMarca = Marca.IdMarca WHERE Modelo LIKE ?", null, null, 1, modelo);
    }

    public List<Dispositivo> getDispositivo(int codigoDispositivo) {
        return selectStatement("SELECT IdDispositivo, Modelo, Marca.IdMarca as IdMarca, Marca.Nome as Nome, Processador, MemoriaRam, Armazenamento, ServiceTag, TamanhoTela, Dispositivo.Data FROM Dispositivo INNER JOIN Marca ON Dispositivo.IdMarca = Marca.IdMarca WHERE IdDispositivo = ?", 1, codigoDispositivo, null, null);
    }

    public List<Dispositivo> getDispositivos() {
        return selectStatement("SELECT IdDispositivo, Modelo, Marca.IdMarca as IdMarca, Marca.Nome as Nome, Processador, MemoriaRam, Armazenamento, ServiceTag, TamanhoTela, Dispositivo.Data FROM Dispositivo INNER JOIN Marca ON Dispositivo.IdMarca = Marca.IdMarca", null, null, null, null);
    }

    public List<Dispositivo> getDispositivosPendentes() {
        return selectStatement("SELECT Dispositivo.IdDispositivo, Modelo, Marca.IdMarca as IdMarca, Marca.Nome as Nome, Processador, MemoriaRam, Armazenamento, ServiceTag, TamanhoTela, Dispositivo.Data FROM Dispositivo INNER JOIN Marca ON Dispositivo.IdMarca = Marca.IdMarca INNER JOIN Lancamento on Lancamento.IdDispositivo = Dispositivo.IdDispositivo WHERE Lancamento.status = 'P'", null, null, null, null);
    }

    public List<Dispositivo> getDispositivosPendentes(int codigoFuncionario) {
        return selectStatement("SELECT Dispositivo.IdDispositivo, Modelo, Marca.IdMarca as IdMarca, Marca.Nome as Nome, Processador, MemoriaRam, Armazenamento, ServiceTag, TamanhoTela, Dispositivo.Data FROM Dispositivo INNER JOIN Marca ON Dispositivo.IdMarca = Marca.IdMarca INNER JOIN Lancamento on Lancamento.IdDispositivo = Dispositivo.IdDispositivo WHERE IdFuncionario = ? AND Lancamento.status = 'P'", 1, codigoFuncionario, null, null);
    }

    public List<Dispositivo> getDispositivosDisponiveis() {
        return selectStatement("SELECT IdDispositivo, Modelo, Marca.IdMarca as IdMarca, Marca.Nome as Nome, Processador, MemoriaRam, Armazenamento, ServiceTag, TamanhoTela, Dispositivo.Data FROM Dispositivo INNER JOIN Marca ON Dispositivo.IdMarca = Marca.IdMarca WHERE Dispositivo.Status = 'D'", null, null, null, null);
    }

    public List<Dispositivo> selectStatement(String command, Integer indexCodigo, Integer idCodigo, Integer indexModelo, String modelo) {
        List<Dispositivo> dispositivos = new ArrayList<>();

        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement statement = conn.prepareStatement(command);
            if (idCodigo != null) statement.setInt(indexCodigo, idCodigo);
            if (indexModelo != null) statement.setString(indexModelo, "%" + modelo + "%");
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                dispositivos.add(new Dispositivo(rs.getInt("IdDispositivo"),
                        rs.getString("Modelo"),
                        new Marca(rs.getInt("IdMarca"), rs.getString("Nome")),
                        rs.getString("Processador"),
                        rs.getString("MemoriaRam"),
                        rs.getString("Armazenamento"),
                        rs.getString("ServiceTag"),
                        rs.getString("TamanhoTela"),
                        rs.getDate("Data")));
            }
        } catch (SQLException e) {
            System.out.println("Erro Dispositivo DAO: " + e.getMessage());
        }
        return dispositivos;
    }

    public int setDispositivo(Dispositivo dispositivo) {
        int codDispositivo = dispositivo.getIdDispositivos();
        String query = "SELECT * FROM Dispositivo WHERE IdDispositivo = ?";

        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, codDispositivo);

            if(!statement.executeQuery().next()) {
                query = "INSERT INTO Dispositivo (Modelo, IdMarca, Processador, ServiceTag, Data, status) " +
                        "VALUES (?, ?, ?, ?, GETDATE(), 'D')";
                PreparedStatement statementInsert = conn.prepareStatement(query);
                statementInsert.setString(1, dispositivo.getModelo());
                statementInsert.setInt(2, dispositivo.getMarca().getIdMarca());
                statementInsert.setString(3, dispositivo.getProcessador());
                statementInsert.setString(4, dispositivo.getServiceTag());
                return statementInsert.executeUpdate();
            } else {
                query = "UPDATE Dispositivo SET Modelo = ?, IdMarca = ?, Processador = ?, ServiceTag = ?, Data = GETDATE() WHERE IdDispositivo = ?";
                PreparedStatement statementUpdate = conn.prepareStatement(query);
                statementUpdate.setString(1, dispositivo.getModelo());
                statementUpdate.setInt(2, dispositivo.getMarca().getIdMarca());
                statementUpdate.setString(3, dispositivo.getProcessador());
                statementUpdate.setString(4, dispositivo.getServiceTag());
                statementUpdate.setInt(5, dispositivo.getIdDispositivos());
                return statementUpdate.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int delDispositivo(int codDispositivo) { //Retorna número de linhas alteradas. "-1" deu erro
        String query = "DELETE FROM Dispositivo WHERE IdDispositivo = ?";
        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, codDispositivo);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

}