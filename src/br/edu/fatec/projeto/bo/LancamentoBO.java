package br.edu.fatec.projeto.bo;

import br.edu.fatec.projeto.dao.LancamentoDAO;
import br.edu.fatec.projeto.dto.Lancamento;
import br.edu.fatec.projeto.exception.BusinessException;
import br.edu.fatec.projeto.exception.PersistenciaException;

public class LancamentoBO {

    public Lancamento buscar(int codigo) throws Exception {

        String erro = "";
        Lancamento lt = null;

        if(codigo < 0) erro = "Código inválido";
        if(!erro.equals("")) throw new Exception(erro);

        LancamentoDAO ltDAO = new LancamentoDAO();

        if (!ltDAO.getLancamento(codigo).isEmpty()) {
            lt = ltDAO.getLancamento(codigo).get(0);
        }
        return lt;
    }

    public void salvar(Lancamento lt) throws BusinessException, PersistenciaException {
        String erro = "";

        if(lt.getFuncionario() == null)
            erro += "Um funcionário deve ser selecionado. ";
        if(lt.getDispositivo() == null)
            erro += "Um dispositivo deve ser selecionado. ";
        if(!erro.equals(""))
            throw new BusinessException("Falha na validação do dados: \n" + erro);

        try {
            LancamentoDAO ltDAO = new LancamentoDAO();
            ltDAO.inserir(lt);
        }catch (PersistenciaException e) {
            throw new PersistenciaException("Erro ao salvar o lançamento. (" + e.getMessage() + ").", e);
        }
    }

}
