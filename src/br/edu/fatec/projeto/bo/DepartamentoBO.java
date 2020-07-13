package br.edu.fatec.projeto.bo;

import br.edu.fatec.projeto.dao.DepartamentoDAO;
import br.edu.fatec.projeto.dto.Departamento;
import br.edu.fatec.projeto.exception.BusinessException;

import java.util.List;

public class DepartamentoBO {

    public String[] listarTodos() throws BusinessException {
        DepartamentoDAO dpDAO = new DepartamentoDAO();
        List<Departamento> departamentos = dpDAO.getDepartamentos();
        String[] retorno = new String[departamentos.size()];
        for (int i = 0; i < departamentos.size(); i++) {
            retorno[i] = departamentos.get(i).getIdDepartamento() + " - " + departamentos.get(i).getNome();
        }
        return retorno;
    }
}
