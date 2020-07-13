package br.edu.fatec.projeto.ui;

import br.edu.fatec.projeto.bo.DispositivoBO;
import br.edu.fatec.projeto.bo.FuncionarioBO;
import br.edu.fatec.projeto.bo.MarcaBO;
import br.edu.fatec.projeto.dto.Dispositivo;
import br.edu.fatec.projeto.dto.Funcionario;
import br.edu.fatec.projeto.dto.Marca;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DispositivoUI extends JFrame {
    private JPanel pnlTela;
    private JLabel lblCodigo;
    private JTextField txtModelo;
    private JLabel lblLogin;
    private JButton btnPesquisar;
    private JButton btnVoltar;
    private JButton btnExcluir;
    private JTextField txtCodigo;
    private JButton btnSalvar;
    private JLabel lblFuncionario;
    private JTextField txtProcessador;
    private JLabel lblProcessador;
    private JTextField txtSerial;
    private JLabel lblSerial;
    private JPanel pnlDispositivos;

    private JComboBox cmdMarca;
    private ComboBoxModel cmbMarcaModel;

    private void initialize() {
        this.setTitle("Cadastros");
        this.setContentPane(pnlDispositivos);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(400, 300);
        this.setVisible(false);
    }

    public DispositivoUI() {
        initialize();
        carregarMarcas();

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DispositivoBO dpBO = new DispositivoBO();
                try {
                    if(dpBO.salvar(getData()) == 1) {
                        JOptionPane.showMessageDialog(btnSalvar, "Dispositivo Salvo com sucesso!", "Salvar Dispositivo", JOptionPane.INFORMATION_MESSAGE);
                        limparTela();
                    } else {
                        JOptionPane.showMessageDialog(btnSalvar, "O Dispositivo não foi salvo! ", "Salvar Dispositivo", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(btnSalvar, "Erro ao Salvar: " + e.getMessage(), "Salvar Dispositivo", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DispositivoBO dpBO = new DispositivoBO();
                Dispositivo dp;
                try {
                    dp = dpBO.buscar(Integer.parseInt(txtCodigo.getText()));
                    if(dp!=null) {
                        setData(dp);
                    } else {
                        JOptionPane.showMessageDialog(txtCodigo, "Dispositivo não encontrado!", "Pesquisa de Dispositivo", JOptionPane.WARNING_MESSAGE);
                        limparTela();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(btnExcluir, "Deseja realmente excluir este Dispositivo?");
                if(res == JOptionPane.YES_OPTION) {
                    DispositivoBO dpBO = new DispositivoBO();
                    try {
                        if(dpBO.excluir(getData()) == 1) {
                            JOptionPane.showMessageDialog(btnSalvar, "Dispositivo Excluído com sucesso!", "Excluir Dispositivo", JOptionPane.INFORMATION_MESSAGE);
                            limparTela();
                        } else {
                            JOptionPane.showMessageDialog(btnSalvar, "Falha ao excluir o Dispositivo! ", "Excluir Dispositivo", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception e2) {
                        JOptionPane.showMessageDialog(btnSalvar, "Erro ao Excluir: " + e2.getMessage(), "Excluir Dispositivo", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        txtCodigo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                super.keyReleased(keyEvent);
                if(txtCodigo.getText().equals("")){
                    limparTela();
                }
            }
        });

    }

    public void setData(Dispositivo data) {
        txtCodigo.setText(String.valueOf(data.getIdDispositivos()));
        txtModelo.setText(data.getModelo());
        txtProcessador.setText(data.getProcessador());
        txtSerial.setText(data.getServiceTag());

        for (int i = 0; i < cmdMarca.getItemCount(); i++) {
            if (data.getMarca().getNome().equals(cmdMarca.getItemAt(i).toString().split(" - ")[1])) {
                cmdMarca.setSelectedIndex(i);
                break;
            }
        }
    }

    public Dispositivo getData() {
        String[] vetMarca = cmdMarca.getSelectedItem().toString().split(" - ");

        Dispositivo data = new Dispositivo(
            (txtCodigo.getText().equals("")) ? 0 : Integer.parseInt(txtCodigo.getText()),
            txtModelo.getText(),
            new Marca(Integer.parseInt(vetMarca[0]), vetMarca[1]),
                txtProcessador.getText(),
                txtSerial.getText());

        return data;
    }

    public void limparTela() {
        txtCodigo.setText("");
        txtModelo.setText("");
        txtProcessador.setText("");
        txtSerial.setText("");
    }

    public void carregarMarcas() {
        MarcaBO mcBO = new MarcaBO();
        cmbMarcaModel = new DefaultComboBoxModel(mcBO.listarTodos());
        cmdMarca.setModel(cmbMarcaModel);
    }

    public JButton getBtnVoltar() {
        return btnVoltar;
    }
}
