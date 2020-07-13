package br.edu.fatec.projeto.ui;

import br.edu.fatec.projeto.bo.DispositivoBO;
import br.edu.fatec.projeto.bo.FuncionarioBO;
import br.edu.fatec.projeto.bo.LancamentoBO;
import br.edu.fatec.projeto.dto.Dispositivo;
import br.edu.fatec.projeto.dto.Funcionario;
import br.edu.fatec.projeto.dto.Lancamento;
import br.edu.fatec.projeto.exception.BusinessException;
import br.edu.fatec.projeto.exception.PersistenciaException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LancamentoUI extends JFrame{
    private JComboBox cmbDispositivo;
    private JComboBox cmdFuncionario;
    private JTextField txtLogin;
    private JTextArea txaObservacao;
    private JButton btnSalvar;
    private JPanel pnlLancamento;
    private JTextField txtSenha;
    private JButton btnVoltar;
    private JButton btnLimpar;
    private JPanel pnlTela;
    private JLabel lblDispositivo;
    private JLabel lblFuncionario;
    private JLabel lblLogin;
    private JLabel lblSenha;
    private JLabel lblObservacao;

    private ComboBoxModel cmdFuncionarioModel;
    private ComboBoxModel cmbDispositivoModel;

    private void initialize() {
        this.setTitle("Movimentações");
        this.setContentPane(pnlLancamento);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(400, 300);
        this.setVisible(false);
    }

    public LancamentoUI() {

        initialize();
        carregarDispDisponiveis();

        //Salvar
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Lancamento lt = new Lancamento();

                try {
                    String[] vetFuncionario = cmdFuncionario.getSelectedItem().toString().split(" - ");
                    lt.setFuncionario(new Funcionario(Integer.parseInt(vetFuncionario[0]), vetFuncionario[1], null));
                    String[] vetDispositivo = cmbDispositivo.getSelectedItem().toString().split(" - ");
                    lt.setDispositivo(new Dispositivo(Integer.parseInt(vetDispositivo[0]), null, null, null, null, null, null, null, null));
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(btnSalvar, "Erro ao buscar o dispositivo ou/e funcionario (" + e2 + ")", "Salvar", JOptionPane.ERROR_MESSAGE);
                }

                lt.setLogin(txtLogin.getText());
                lt.setSenha(txtSenha.getText());
                lt.setObservacao(txaObservacao.getText());

                try {
                    LancamentoBO ltBO = new LancamentoBO();
                    ltBO.salvar(lt);
                } catch (BusinessException businessException) {
                    JOptionPane.showMessageDialog(btnSalvar, businessException, "Salvar", JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (PersistenciaException persistenciaException) {
                    JOptionPane.showMessageDialog(btnSalvar, persistenciaException, "Salvar", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                limparTela();
                carregarDispDisponiveis();
                JOptionPane.showMessageDialog(pnlLancamento, "Lançamento efetuado com sucesso!", "Salvar", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        cmbDispositivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Controla campos
                if (cmbDispositivo.getSelectedItem().toString().equals("")) {
                    limparTela();
                    return;
                } else {
                    lblLogin.setEnabled(true);
                    txtLogin.setEnabled(true);
                    lblSenha.setEnabled(true);
                    txtSenha.setEnabled(true);
                    lblObservacao.setEnabled(true);
                    cmdFuncionario.setEnabled(true);
                    lblFuncionario.setEnabled(true);
                    txaObservacao.setEnabled(true);
                    lblObservacao.setEnabled(true);
                }

                //Carregar Funcionários
                try {
                    FuncionarioBO fcBO = new FuncionarioBO();
                    cmdFuncionarioModel = new DefaultComboBoxModel(fcBO.listarTodos());
                    cmdFuncionario.setModel(cmdFuncionarioModel);
                }catch (BusinessException e2) {
                    JOptionPane.showMessageDialog(null, "Erro ao carregar os funcionários!");
                }
            }
        });

        btnLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparTela();
            }
        });
    }

    //Limpar Tela
    private void limparTela() {
        lblLogin.setEnabled(false);
        txtLogin.setText("");
        txtLogin.setEnabled(false);
        lblSenha.setEnabled(false);
        txtSenha.setText("");
        txtSenha.setEnabled(false);
        lblObservacao.setEnabled(false);
        txaObservacao.setText("");
        cmdFuncionario.setEnabled(false);
        lblFuncionario.setEnabled(false);
        txaObservacao.setEnabled(false);
        lblObservacao.setEnabled(false);
        cmbDispositivo.setSelectedIndex(0);
    }

    //Carregar Dispositivos "D" - Disponiveis
    public void carregarDispDisponiveis() {
        try {
            DispositivoBO dpBO = new DispositivoBO();
            cmbDispositivoModel = new DefaultComboBoxModel(dpBO.listarDisponiveis());
            cmbDispositivo.setModel(cmbDispositivoModel);
        }catch (BusinessException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar os dispositivos!");
        }
    }

    public JButton getBtnVoltar() {
        return btnVoltar;
    }

    public JButton getBtnSalvar() {
        return btnSalvar;
    }
}
