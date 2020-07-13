package br.edu.fatec.projeto.ui;

import br.edu.fatec.projeto.bo.MovimentacaoBO;
import br.edu.fatec.projeto.dto.Movimentacao;
import br.edu.fatec.projeto.exception.BusinessException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PrincipalUI extends JFrame {
    private JPanel pnlTela;
    private JPanel pnlPrincipal;
    private JPanel pnlLista;

    private JMenuBar mnuBarra;
    private JMenu mnuCadastros;
    private JMenu mnuMovimentacoes;
    private JMenuItem mnuLancamento;
    private JMenuItem mnuDevolucao;
    private JMenuItem mnuFuncionario;
    private JMenuItem mnuDispositivo;

    private DevolucaoUI telaDevolucao;
    private LancamentoUI telaLancamento;
    private FuncionarioUI telaFuncionario;
    private DispositivoUI telaDispositivo;

    private JList lstMovimentos;
    private JTextField txtDataRetirada;
    private JTextField txtStatus;
    private JTextField txtNome;
    private JTextField txtMarca;
    private JTextField txtModelo;
    private JLabel lblStatus;
    private JLabel lblDataRetirada;
    private JLabel lblDataDevolucao;
    private JLabel lblNome;
    private JLabel lblMarca;
    private JLabel lblModelo;
    private JTextField txtDataDevolucao;
    private ListModel lstLancamentoModel;


    public static void main(String[] args) throws BusinessException {
        new PrincipalUI();
    }

    private void initialize() {
        this.setTitle("Menu Principal - Controle de Dispositivos");
        this.setContentPane(pnlPrincipal);
        this.setJMenuBar(getMnuBarra());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(750,400);
        this.setVisible(true);
        this.setEnabled(true);

        telaDevolucao = new DevolucaoUI();
        telaLancamento = new LancamentoUI();
        telaFuncionario = new FuncionarioUI();
        telaDispositivo = new DispositivoUI();
    }

    public PrincipalUI() throws HeadlessException, BusinessException {
        initialize();

        carregaLancamentos();

        //Eventos Devolução
        mnuDevolucao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                controlaTelaPrincipal();
                telaDevolucao.setVisible(true);
                telaDevolucao.carregarFuncPendentes();
            }
        });
        telaDevolucao.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                controlaTelaPrincipal();
                telaDevolucao.setVisible(false);
                try {
                    carregaLancamentos();
                } catch (BusinessException businessException) {
                    JOptionPane.showMessageDialog(pnlPrincipal, "Erro ao carregar os movimentos!" + businessException.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        telaDevolucao.getBtnVoltar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlaTelaPrincipal();
                telaDevolucao.setVisible(false);
                try {
                    carregaLancamentos();
                } catch (BusinessException businessException) {
                    JOptionPane.showMessageDialog(pnlPrincipal, "Erro ao carregar os movimentos!" + businessException.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Eventos Lanc. Retirada
        mnuLancamento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                controlaTelaPrincipal();
                telaLancamento.setVisible(true);
                telaLancamento.carregarDispDisponiveis();
            }
        });
        telaLancamento.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                controlaTelaPrincipal();
                telaLancamento.setVisible(false);
                telaLancamento.dispose();
                try {
                    carregaLancamentos();
                } catch (BusinessException businessException) {
                    JOptionPane.showMessageDialog(pnlPrincipal, "Erro ao carregar os movimentos!" + businessException.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        telaLancamento.getBtnVoltar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlaTelaPrincipal();
                telaLancamento.setVisible(false);
                telaLancamento.dispose();
                try {
                    carregaLancamentos();
                } catch (BusinessException businessException) {
                    JOptionPane.showMessageDialog(pnlPrincipal, "Erro ao carregar os movimentos!" + businessException.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        //Eventos Funcionario
        mnuFuncionario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                controlaTelaPrincipal();
                telaFuncionario.setVisible(true);
            }
        });
        telaFuncionario.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                controlaTelaPrincipal();
                telaFuncionario.setVisible(false);
            }
        });
        telaFuncionario.getBtnVoltar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlaTelaPrincipal();
                telaFuncionario.setVisible(false);
            }
        });

        //Eventos Dispositivos
        mnuDispositivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                controlaTelaPrincipal();
                telaDispositivo.setVisible(true);
            }
        });
        telaDispositivo.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                controlaTelaPrincipal();
                telaDispositivo.setVisible(false);
            }
        });
        telaDispositivo.getBtnVoltar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlaTelaPrincipal();
                telaDispositivo.setVisible(false);
            }
        });

        //Popula campos do movimento selecionado na lista de consulta de movimentações
        lstMovimentos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                limpaCampos();
                String[] movimento = lstMovimentos.getSelectedValue().toString().split(" - ");

                try {
                    preencheMovimento(Integer.parseInt(movimento[0]));
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(lstMovimentos, "Erro ao preencher campos do movimento:" + exception.getMessage() + "!", "Erro Preencher", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }


    public void controlaTelaPrincipal() {
        if (this.isEnabled()) {
            this.setEnabled(false);
        } else {
            this.setEnabled(true);
        }
    }

    private JMenuBar getMnuBarra() {
        if (mnuBarra == null){
            mnuBarra = new JMenuBar();
            mnuBarra.add(getMnuCadastros());
            mnuBarra.add(getMnuMovimentacoes());
        }
        return mnuBarra;
    }

    private JMenu getMnuCadastros() {
        if (mnuCadastros == null){
            mnuCadastros = new JMenu();
            mnuCadastros.setText("Cadastros");
            mnuCadastros.add(getMnuFuncionario());
            mnuCadastros.add(getMnuDispositivo());
        }
        return mnuCadastros;
    }

    private JMenu getMnuMovimentacoes() {
        if (mnuMovimentacoes == null){
            mnuMovimentacoes = new JMenu();
            mnuMovimentacoes.setText("Movimentações");
            mnuMovimentacoes.add(getMnuLancamento());
            mnuMovimentacoes.add(getMnuDevolucao());
        }
        return mnuMovimentacoes;
    }

    private JMenuItem getMnuLancamento() {
        if (mnuLancamento == null){
            mnuLancamento = new JMenuItem();
            mnuLancamento.setText("Lançar Retirada");
        }
        return mnuLancamento;
    }

    private JMenuItem getMnuDevolucao() {
        if (mnuDevolucao == null){
            mnuDevolucao = new JMenuItem();
            mnuDevolucao.setText("Lançar Devolução");
        }
        return mnuDevolucao;
    }

    private JMenuItem getMnuFuncionario() {
        if (mnuFuncionario == null){
            mnuFuncionario = new JMenuItem();
            mnuFuncionario.setText("Cadastrar Funcionário");
        }
        return mnuFuncionario;
    }

    private JMenuItem getMnuDispositivo() {
        if (mnuDispositivo == null){
            mnuDispositivo = new JMenuItem();
            mnuDispositivo.setText("Cadastrar Dispositivo");
        }
        return mnuDispositivo;
    }

    private void carregaLancamentos() throws BusinessException {
        String[] listaCliente;
        MovimentacaoBO movimentacaoBO = new MovimentacaoBO();
        listaCliente = movimentacaoBO.listar();
        lstLancamentoModel = new DefaultComboBoxModel(listaCliente);
        lstMovimentos.setModel(lstLancamentoModel);
        lstMovimentos.setVisible(true);
    }

    private void preencheMovimento(int codigo) throws Exception {
        MovimentacaoBO movimentacaoBO = new MovimentacaoBO();
        Movimentacao mv = movimentacaoBO.buscar(codigo);

        txtStatus.setText((mv.getStatus().equals("P")) ? "Pendente" : "Devolvido");
        txtDataRetirada.setText(mv.getDataLancamento().toString());
        txtDataDevolucao.setText((mv.getDataDevolucao() == null) ? "Nenhuma" : mv.getDataDevolucao().toString());
        txtNome.setText(mv.getNome());
        txtMarca.setText(mv.getMarca());
        txtModelo.setText(mv.getModelo());
    }

    private void limpaCampos() {
        txtStatus.setText("");
        txtDataRetirada.setText("");
        txtDataDevolucao.setText("");
        txtNome.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
    }
}