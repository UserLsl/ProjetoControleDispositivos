package br.edu.fatec.projeto.bo;

import br.edu.fatec.projeto.dao.DispositivoDAO;
import br.edu.fatec.projeto.dao.FuncionarioDAO;
import br.edu.fatec.projeto.dao.LancamentoDAO;
import br.edu.fatec.projeto.dto.Dispositivo;
import br.edu.fatec.projeto.dto.Funcionario;
import br.edu.fatec.projeto.dto.Lancamento;
import br.edu.fatec.projeto.exception.BusinessException;

import java.util.List;

public class DispositivoBO {

    public int salvar(Dispositivo dispositivo) throws Exception {
        String erro = "";

        if(dispositivo.getModelo() == null || dispositivo.getModelo().equals(""))
            erro += "O modelo deve ser informado\n";
        if(dispositivo.getMarca() == null)
            erro += "A marca deve ser informada\n";
        if(!erro.equals("")) throw new Exception(erro);

        DispositivoDAO dpDAO = new DispositivoDAO();
        return dpDAO.setDispositivo(dispositivo);
    }

    public int excluir(Dispositivo dispositivo) throws Exception {
        String erro = "";

        LancamentoDAO ltDAO = new LancamentoDAO();
        List<Lancamento> ltList = ltDAO.getMovimentosDispositivo(dispositivo.getIdDispositivos());

        if(ltList.size() > 0) erro = "Dispositivo já referenciado a movimentos, por isso não pode ser apagado!\n";
        if(dispositivo.getIdDispositivos() <= 0) erro = "Código do Dispositivo Inválido\n";
        if(!erro.equals("")) throw new Exception(erro);

        DispositivoDAO dpDAO = new DispositivoDAO();
        return dpDAO.delDispositivo(dispositivo.getIdDispositivos());
    }

    public Dispositivo buscar(int codigo) throws Exception {

        String erro = "";
        Dispositivo dp = null;

        if(codigo < 0) erro = "Código inválido";
        if(!erro.equals("")) throw new Exception(erro);

        DispositivoDAO dpDAO = new DispositivoDAO();

        if (!dpDAO.getDispositivo(codigo).isEmpty()) {
            dp = dpDAO.getDispositivo(codigo).get(0);
        }
        return dp;
    }

    public String[] listarTodos() throws BusinessException {
        DispositivoDAO dpDAO = new DispositivoDAO();
        List<Dispositivo> dispositivos = dpDAO.getDispositivos();
        String[] retorno = new String[dispositivos.size()];
        for (int i = 0; i < dispositivos.size(); i++) {
            retorno[i] = dispositivos.get(i).getIdDispositivos() + " - " + dispositivos.get(i).getMarca().getNome() + " " + dispositivos.get(i).getModelo();
        }
        return retorno;
    }

    public String[] listarPendentes(int idFuncionario) throws BusinessException {
        DispositivoDAO dpDAO = new DispositivoDAO();
        List<Dispositivo> dispositivos = dpDAO.getDispositivosPendentes(idFuncionario);
        String[] retorno = new String[dispositivos.size()];
        for (int i = 0; i < dispositivos.size(); i++) {
            retorno[i] = dispositivos.get(i).getIdDispositivos() + " - " + dispositivos.get(i).getMarca().getNome() + " " + dispositivos.get(i).getModelo();
        }
        return retorno;
    }

    public String[] listarDisponiveis() throws BusinessException {
        DispositivoDAO dpDAO = new DispositivoDAO();
        List<Dispositivo> dispositivos = dpDAO.getDispositivosDisponiveis();
        String[] retorno = new String[dispositivos.size() + 1];
        retorno[0] = "";
        for (int i = 1; i < dispositivos.size() + 1; i++) {
            retorno[i] = dispositivos.get(i - 1).getIdDispositivos() + " - " + dispositivos.get(i - 1).getMarca().getNome() + " " + dispositivos.get(i - 1).getModelo();
        }
        return retorno;
    }
}
