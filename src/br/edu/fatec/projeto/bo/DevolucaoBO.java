package br.edu.fatec.projeto.bo;

import br.edu.fatec.projeto.dao.DevolucaoDAO;
import br.edu.fatec.projeto.dto.Devolucao;
import br.edu.fatec.projeto.exception.BusinessException;
import br.edu.fatec.projeto.exception.PersistenciaException;

public class DevolucaoBO {

    public void salvar(Devolucao dv) throws BusinessException, PersistenciaException {
        String erro = "";

        if(dv.getFuncionario() == null)
            erro += "Um funcionário deve ser selecionado";
        if(dv.getDispositivo() == null)
            erro += "Um dispositivo deve ser selecionado!";
        if(!erro.equals(""))
            throw new BusinessException("Falha na validação do dados: \n" + erro);

        try {
            DevolucaoDAO dvDAO = new DevolucaoDAO();
            dvDAO.inserir(dv);
        }catch (PersistenciaException e) {
            throw new PersistenciaException("Erro ao salvar o lançamento de devolução. (" + e.getMessage() + ").", e);
        }
    }
}
