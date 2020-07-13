package br.edu.fatec.projeto.bo;

import br.edu.fatec.projeto.dao.FuncionarioDAO;
import br.edu.fatec.projeto.dao.LancamentoDAO;
import br.edu.fatec.projeto.dto.Funcionario;
import br.edu.fatec.projeto.dto.Lancamento;
import br.edu.fatec.projeto.exception.BusinessException;

import java.util.List;

public class FuncionarioBO {

    public int salvar(Funcionario funcionario) throws Exception {
        String erro = "";

        if(funcionario.getNome() == null || funcionario.getNome().equals(""))
            erro += "O nome deve ser informado\n";
        if(!erro.equals("")) throw new Exception(erro);

        FuncionarioDAO fnDAO = new FuncionarioDAO();
        return fnDAO.setFuncionario(funcionario);
    }

    public int excluir(Funcionario funcionario) throws Exception {
        String erro = "";

        LancamentoDAO ltDAO = new LancamentoDAO();
        List<Lancamento> ltList = ltDAO.getMovimentosFuncionario(funcionario.getIdFuncionario());

        if(ltList.size() > 0) erro = "Funcionário já referenciado a movimentos, por isso não pode ser apagado!\n";
        if(funcionario.getIdFuncionario() <= 0) erro = "Código do Funcionário Inválido\n";
        if(!erro.equals("")) throw new Exception(erro);

        FuncionarioDAO fnDAO = new FuncionarioDAO();
        return fnDAO.delFuncionario(funcionario.getIdFuncionario());
    }

    public Funcionario buscar(int codigo) throws Exception {
        String erro = "";
        Funcionario fn = null;
        FuncionarioDAO fnDAO = new FuncionarioDAO();

        if(codigo < 0) erro = "Código inválido";
        if(!erro.equals("")) throw new Exception(erro);

        if (!fnDAO.getFuncionario(codigo).isEmpty()) {
            fn = fnDAO.getFuncionario(codigo).get(0);
        }
        return fn;
    }

    public String[] listarTodos() throws BusinessException {
        FuncionarioDAO fcDAO = new FuncionarioDAO();
        List<Funcionario> funcionarios = fcDAO.getFuncionarios();
        String[] retorno = new String[funcionarios.size()];
        for (int i = 0; i < funcionarios.size(); i++) {
            retorno[i] = funcionarios.get(i).getIdFuncionario() + " - " + funcionarios.get(i).getNome();
        }
        return retorno;
    }

    public String[] listarPendentes() throws BusinessException {
        FuncionarioDAO fcDAO = new FuncionarioDAO();
        List<Funcionario> funcionarios = fcDAO.getFuncionariosPendentes();
        String[] retorno = new String[funcionarios.size() + 1];
        retorno[0] = "";
        for (int i = 1; i < funcionarios.size() + 1; i++) {
            retorno[i] = funcionarios.get(i - 1).getIdFuncionario() + " - " + funcionarios.get(i - 1).getNome();
        }
        return retorno;
    }

}
