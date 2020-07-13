package br.edu.fatec.projeto.bo;


import br.edu.fatec.projeto.dao.LancamentoDAO;
import br.edu.fatec.projeto.dao.MovimentacaoDAO;
import br.edu.fatec.projeto.dto.Lancamento;
import br.edu.fatec.projeto.dto.Movimentacao;
import br.edu.fatec.projeto.exception.BusinessException;
import br.edu.fatec.projeto.exception.PersistenciaException;

import java.util.List;

public class MovimentacaoBO {

    public String[] listar() throws BusinessException {
        MovimentacaoDAO movimentacaoDAO = new MovimentacaoDAO();
        List<Movimentacao> movimentacoes = movimentacaoDAO.getMovimentacoes();
        String[] retorno = new String[movimentacoes.size()];
        for (int i = 0; i < movimentacoes.size(); i++) {
            retorno[i] = movimentacoes.get(i).getIdLancamento() + " - " + movimentacoes.get(i).getDataLancamento() + " - " + movimentacoes.get(i).getNome() + ": " + movimentacoes.get(i).getMarca() + " " + movimentacoes.get(i).getModelo();
        }
        return retorno;
    }

    public Movimentacao buscar(int codigo) throws Exception {
        String erro = "";
        Movimentacao mv = null;
        MovimentacaoDAO mvDAO = new MovimentacaoDAO();

        if(codigo < 0) erro = "Código inválido";
        if(!erro.equals("")) throw new Exception(erro);

        if (!mvDAO.getMovimentacao(codigo).isEmpty()) {
            mv = mvDAO.getMovimentacao(codigo).get(0);
        }
        return mv;
    }
}
