package br.edu.fatec.projeto.dao;

import br.edu.fatec.projeto.dto.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDAO {

    public List<Departamento> getDepartamentos() {
        return selectStatement("SELECT IdDepartamento, Nome FROM Departamento");
    }

    public List<Departamento> selectStatement(String command) {
        List<Departamento> departamentos = new ArrayList<>();

        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement statement = conn.prepareStatement(command);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                departamentos.add(new Departamento(rs.getInt("IdDepartamento"), rs.getString("Nome")));
            }
        } catch (SQLException e) {
            System.out.println("Erro Departamento DAO: " + e.getMessage());
        }
        return departamentos;
    }
}
