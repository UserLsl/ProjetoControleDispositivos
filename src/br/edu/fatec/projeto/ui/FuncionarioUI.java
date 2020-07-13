package br.edu.fatec.projeto.ui;

import br.edu.fatec.projeto.bo.DepartamentoBO;
import br.edu.fatec.projeto.bo.DispositivoBO;
import br.edu.fatec.projeto.bo.FuncionarioBO;
import br.edu.fatec.projeto.dto.Departamento;
import br.edu.fatec.projeto.dto.Funcionario;
import br.edu.fatec.projeto.exception.BusinessException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FuncionarioUI extends JFrame {
    private JPanel pnlTela;
    private JLabel lblCodigo;
    private JLabel lblDepartamento;
    private JTextField txtNome;
    private JLabel lblLogin;
    private JButton btnSalvar;
    private JButton btnVoltar;
    private JButton btnExcluir;
    private JButton btnPesquisar;
    private JTextField txtCodigo;
    private JPanel pnlFuncionarios;

    private JComboBox cmdDepartamento;
    private ComboBoxModel cmbDepartamentoModel;

    private void initialize() {
        this.setTitle("Cadastros");
        this.setContentPane(pnlFuncionarios);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(400, 300);
        this.setVisible(false);

        carregaDepartamentos();
    }

    public FuncionarioUI() {
        initialize();

        btnPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FuncionarioBO fnBO = new FuncionarioBO();
                Funcionario fn;
                try {
                    fn = fnBO.buscar(Integer.parseInt(txtCodigo.getText()));
                    if(fn!=null) {
                        setData(fn);
                    } else {
                        JOptionPane.showMessageDialog(txtCodigo, "Vendedor não encontrado!", "Pesquisa de Vendedor", JOptionPane.WARNING_MESSAGE);
                        limparTela();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FuncionarioBO fnBO = new FuncionarioBO();
                try {
                    if(fnBO.salvar(getData()) == 1) {
                        JOptionPane.showMessageDialog(btnSalvar, "Funcionário Salvo com sucesso!", "Salvar Funcionário", JOptionPane.INFORMATION_MESSAGE);
                        limparTela();
                    } else {
                        JOptionPane.showMessageDialog(btnSalvar, "O Funcionário não foi salvo! ", "Salvar Funcionário", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(btnSalvar, "Erro ao Salvar: " + e.getMessage(), "Salvar Funcionário", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(btnExcluir, "Deseja realmente excluir este Funcionário?");
                if(res == JOptionPane.YES_OPTION) {
                    FuncionarioBO fnBO = new FuncionarioBO();
                    try {
                        if(fnBO.excluir(getData()) == 1) {
                            JOptionPane.showMessageDialog(btnSalvar, "Funcionário Excluído com sucesso!", "Excluir Funcionário", JOptionPane.INFORMATION_MESSAGE);
                            limparTela();
                        } else {
                            JOptionPane.showMessageDialog(btnSalvar, "Falha ao excluir o Funcionário! ", "Excluir Funcionário", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception e2) {
                        JOptionPane.showMessageDialog(btnSalvar, "Erro ao Excluir: " + e2.getMessage(), "Excluir Funcionário", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    public void setData(Funcionario data) {
        txtCodigo.setText(String.valueOf(data.getIdFuncionario()));
        txtNome.setText(data.getNome());

        for (int i = 0; i < cmdDepartamento.getItemCount(); i++) {
            if (data.getDepartamento().getNome().equals(cmdDepartamento.getItemAt(i).toString().split(" - ")[1])) {
                cmdDepartamento.setSelectedIndex(i);
                break;
            }
        }
    }

    public Funcionario getData() {
        String[] vetDepto = cmdDepartamento.getSelectedItem().toString().split(" - ");

        Funcionario data = new Funcionario(
            (txtCodigo.getText().equals("")) ? 0 : Integer.parseInt(txtCodigo.getText()),
            txtNome.getText(),
            new Departamento(Integer.parseInt(vetDepto[0]), vetDepto[1]));

        return data;
    }

    private void carregaDepartamentos() {
        try {
            DepartamentoBO dpBO = new DepartamentoBO();
            cmbDepartamentoModel = new DefaultComboBoxModel(dpBO.listarTodos());
            cmdDepartamento.setModel(cmbDepartamentoModel);
        }catch (BusinessException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar os departamentos!");
        }
    }

    public JButton getBtnVoltar() {
        return btnVoltar;
    }

    public void limparTela() {
        txtCodigo.setText("");
        txtNome.setText("");
    }


}
