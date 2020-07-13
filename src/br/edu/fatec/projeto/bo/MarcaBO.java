package br.edu.fatec.projeto.bo;

import br.edu.fatec.projeto.dao.MarcaDAO;
import br.edu.fatec.projeto.dto.Marca;
import br.edu.fatec.projeto.exception.BusinessException;

import java.util.List;

public class MarcaBO {

    public String[] listarTodos() {
        MarcaDAO dpDAO = new MarcaDAO();
        List<Marca> marcas = dpDAO.getMarcas();
        String[] retorno = new String[marcas.size()];
        for (int i = 0; i < marcas.size(); i++) {
            retorno[i] = marcas.get(i).getIdMarca() + " - " + marcas.get(i).getNome();
        }
        return retorno;
    }
}
