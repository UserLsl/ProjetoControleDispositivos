package br.edu.fatec.projeto.dao;

import br.edu.fatec.projeto.dto.Departamento;
import br.edu.fatec.projeto.dto.Marca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MarcaDAO {

    public List<Marca> getMarcas() {
        return selectStatement("SELECT IdMarca, Nome FROM Marca");
    }

    public List<Marca> selectStatement(String command) {
        List<Marca> marcas = new ArrayList<>();

        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement statement = conn.prepareStatement(command);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                marcas.add(new Marca(rs.getInt("IdMarca"), rs.getString("Nome")));
            }
        } catch (SQLException e) {
            System.out.println("Erro Marca DAO: " + e.getMessage());
        }
        return marcas;
    }
}
