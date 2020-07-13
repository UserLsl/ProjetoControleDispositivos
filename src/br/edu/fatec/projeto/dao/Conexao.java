package br.edu.fatec.projeto.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Conexao {
    private static Connection conexao = null;

    public static Connection getConexao() {
        if(conexao!=null) {
            return conexao;
        }
        try {
            ResourceBundle bundle;
            bundle = ResourceBundle.getBundle("br.edu.fatec.projeto/conf");

            Class.forName(bundle.getString("DRIVER"));
            conexao = DriverManager.getConnection(bundle.getString("URL"), bundle.getString("USER"), bundle.getString("PASSWORD"));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
        return conexao;
    }
}
