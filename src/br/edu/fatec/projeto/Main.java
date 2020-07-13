package br.edu.fatec.projeto;

import br.edu.fatec.projeto.dao.DevolucaoDAO;
import br.edu.fatec.projeto.dto.Devolucao;
import br.edu.fatec.projeto.ui.LancamentoUI;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        ///Dispositivo
        /*DispositivoDAO dpDAO = new DispositivoDAO();
        List<Dispositivo> dp = dpDAO.getDispositivo("2649RT");

        for (Dispositivo dispositivo: dp) {
            System.out.printf("%s - %s\n", dispositivo.getMarca().getNome(), dispositivo.getModelo());
        }*/

        ///Lançamento
        /*LancamentoDAO ltDAO = new LancamentoDAO();
        List<Lancamento> lt = ltDAO.getLancamentos();

        for (Lancamento lancamento: lt) {
            System.out.printf("%s - %s - %s\n", lancamento.getFuncionario().getNome(), lancamento.getDispositivo().getModelo(), lancamento.getObservacao());
        }*/

        ///Devolução
        /*DevolucaoDAO dvDAO = new DevolucaoDAO();
        List<Devolucao> dv = dvDAO.getDevolucoes();

        for (Devolucao devolucao: dv) {
            System.out.printf("%s - %s %s - %s\n", devolucao.getFuncionario().getNome(), devolucao.getDispositivo().getMarca().getNome(), devolucao.getDispositivo().getModelo(), devolucao.getObservacao());
        }*/


    }
}
