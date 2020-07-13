package br.edu.fatec.projeto.ui;

import br.edu.fatec.projeto.bo.DevolucaoBO;
import br.edu.fatec.projeto.bo.DispositivoBO;
import br.edu.fatec.projeto.bo.FuncionarioBO;
import br.edu.fatec.projeto.dto.Devolucao;
import br.edu.fatec.projeto.dto.Dispositivo;
import br.edu.fatec.projeto.dto.Funcionario;
import br.edu.fatec.projeto.exception.BusinessException;
import br.edu.fatec.projeto.exception.PersistenciaException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DevolucaoUI extends JFrame {
    private JPanel pnlDevolucao;
    private JPanel pnlTela;
    private JComboBox cmbDispositivo;
    private JComboBox cmdFuncionario;
    private JButton btnSalvar;
    private JButton btnVoltar;
    private JButton btnLimpar;
    private JTextArea txaObservacao;
    private JLabel lblDispositivo;
    private JLabel lblObservacao;

    private ComboBoxModel cmdFuncionarioModel;
    private ComboBoxModel cmbDispositivoModel;

    private void initialize() {
        this.setTitle("Movimentações");
        this.setContentPane(pnlDevolucao);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(400, 300);
        this.setVisible(false);
    }

    public DevolucaoUI () {

        initialize();
        carregarFuncPendentes();

        cmdFuncionario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Controla campos
                if (cmdFuncionario.getSelectedItem().toString().equals("")) {
                    limparTela();
                    return;
                } else {
                    cmbDispositivo.setEnabled(true);
                    lblDispositivo.setEnabled(true);
                    txaObservacao.setEnabled(true);
                    lblObservacao.setEnabled(true);
                }

                //Carregar Dispositivos do funcionário selecionado
                try {
                    DispositivoBO dpBO = new DispositivoBO();

                    String[] vetFuncionario = cmdFuncionario.getSelectedItem().toString().split(" - ");
                    cmbDispositivoModel = new DefaultComboBoxModel(dpBO.listarPendentes(Integer.parseInt(vetFuncionario[0])));

                    cmbDispositivo.setModel(cmbDispositivoModel);
                }catch (BusinessException e2) {
                    JOptionPane.showMessageDialog(null, "Erro ao carregar os dispositivos!");
                }
            }
        });

        btnLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparTela();
            }
        });

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Devolucao dv = new Devolucao();

                String[] vetFuncionario = cmdFuncionario.getSelectedItem().toString().split(" - ");
                dv.setFuncionario(new Funcionario(Integer.parseInt(vetFuncionario[0]), vetFuncionario[1], null));

                String[] vetDispositivo = cmbDispositivo.getSelectedItem().toString().split(" - ");
                dv.setDispositivo(new Dispositivo(Integer.parseInt(vetDispositivo[0]), null, null, null, null, null, null, null, null));

                dv.setObservacao(txaObservacao.getText());

                try {
                    DevolucaoBO dvBO = new DevolucaoBO();
                    dvBO.salvar(dv);
                } catch (BusinessException businessException) {
                    JOptionPane.showMessageDialog(btnSalvar, businessException, "Salvar", JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (PersistenciaException persistenciaException) {
                    JOptionPane.showMessageDialog(btnSalvar, persistenciaException, "Salvar", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(pnlDevolucao, "Devolução efetuada com sucesso!", "Salvar", JOptionPane.INFORMATION_MESSAGE);
                limparTela();
                carregarFuncPendentes();
            }
        });
    }

    //Carregar Funcionários que tem devoluções pendentes
    public void carregarFuncPendentes () {
        try {
            FuncionarioBO fcBO = new FuncionarioBO();
            cmdFuncionarioModel = new DefaultComboBoxModel(fcBO.listarPendentes());
            cmdFuncionario.setModel(cmdFuncionarioModel);
        }catch (BusinessException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar os funcionários pendentes!");
        }
    }

    //Limpar Tela
    private void limparTela () {
        cmbDispositivo.setEnabled(false);
        lblDispositivo.setEnabled(false);
        txaObservacao.setEnabled(false);
        lblObservacao.setEnabled(false);
        txaObservacao.setText("");
        cmdFuncionario.setSelectedIndex(0);
    }

    public JButton getBtnVoltar() {
        return btnVoltar;
    }

    public JButton getBtnSalvar() {
        return btnSalvar;
    }
}
