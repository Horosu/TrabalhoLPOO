package br.trabalho3.sistema.ui;

import br.trabalho3.sistema.model.*;
import br.trabalho3.sistema.persistence.PagamentoRepository;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class TelaPagamento extends JDialog {

    private Academia academia;
    private PagamentoRepository pagamentoRepo;
    private JComboBox<String> cmbMatricula, cmbFormaPagamento, cmbTipoCartao;
    private JTextField txtValor;
    private JButton btnConfirmar, btnFechar;
    private JLabel lblTotalPago, lblValorRestante;
    private JTable tabelaPagamentos;
    private DefaultTableModel modeloTabela;

    public TelaPagamento(Frame parent, Academia academia, PagamentoRepository pagamentoRepo) {
        super(parent, "Registrar Pagamento", true);
        this.academia = academia;
        this.pagamentoRepo = pagamentoRepo;

        setSize(700, 550);
        setLocationRelativeTo(parent);

        inicializarComponentes();
        montarLayout();
        configurarEventos();
    }

    private void inicializarComponentes() {
        cmbMatricula = new JComboBox<>();
        cmbFormaPagamento = new JComboBox<>(new String[]{"PIX", "CARTAO", "DINHEIRO"});
        cmbTipoCartao = new JComboBox<>(new String[]{"CREDITO", "DEBITO"});
        txtValor = new JTextField(10);
        btnConfirmar = new JButton("Confirmar Pagamento");
        btnFechar = new JButton("Fechar");
        lblTotalPago = new JLabel("Total Pago: R$ 0,00");
        lblValorRestante = new JLabel("Valor Restante: R$ 0,00");

        // Popula matrículas ativas
        for (Matricula m : academia.listarMatriculas()) {
            if (m.estaAtiva()) {
                cmbMatricula.addItem(m.getId() + " - " + m.getAluno().getNome() +
                    " (R$ " + String.format("%.2f", m.getValorMensal()) + ")");
            }
        }

        // Configura tabela de pagamentos anteriores
        String[] colunas = {"Data", "Forma", "Valor"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaPagamentos = new JTable(modeloTabela);
    }

    private void montarLayout() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int linha = 0;
        gbc.gridx = 0; gbc.gridy = linha;
        painel.add(new JLabel("Matrícula:"), gbc);
        gbc.gridx = 1;
        painel.add(cmbMatricula, gbc);

        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        painel.add(new JLabel("Valor (R$):"), gbc);
        gbc.gridx = 1;
        painel.add(txtValor, gbc);

        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        painel.add(new JLabel("Forma Pagamento:"), gbc);
        gbc.gridx = 1;
        painel.add(cmbFormaPagamento, gbc);

        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        painel.add(new JLabel("Tipo Cartão:"), gbc);
        gbc.gridx = 1;
        painel.add(cmbTipoCartao, gbc);
        cmbTipoCartao.setVisible(false);

        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        gbc.gridwidth = 2;
        JPanel painelInfo = new JPanel(new GridLayout(2, 1, 5, 5));
        painelInfo.setBorder(BorderFactory.createTitledBorder("Informações da Matrícula"));
        painelInfo.add(lblTotalPago);
        painelInfo.add(lblValorRestante);
        painel.add(painelInfo, gbc);

        linha++;
        gbc.gridy = linha;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBorder(BorderFactory.createTitledBorder("Pagamentos Anteriores"));
        painelTabela.add(new JScrollPane(tabelaPagamentos), BorderLayout.CENTER);
        painel.add(painelTabela, gbc);

        linha++;
        gbc.gridy = linha;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0.0;
        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.add(btnConfirmar);
        painelBotoes.add(btnFechar);
        painel.add(painelBotoes, gbc);

        add(painel);
    }

    private void configurarEventos() {
        cmbFormaPagamento.addActionListener(e -> {
            String forma = (String) cmbFormaPagamento.getSelectedItem();
            cmbTipoCartao.setVisible("CARTAO".equals(forma));
        });

        cmbMatricula.addActionListener(e -> atualizarInformacoesMatricula());

        btnConfirmar.addActionListener(e -> registrarPagamento());
        btnFechar.addActionListener(e -> dispose());

        // Atualiza informações ao abrir
        if (cmbMatricula.getItemCount() > 0) {
            atualizarInformacoesMatricula();
        }
    }

    private void atualizarInformacoesMatricula() {
        try {
            String matriculaSel = (String) cmbMatricula.getSelectedItem();
            if (matriculaSel == null) return;

            String idMatricula = matriculaSel.split(" - ")[0];
            Matricula matricula = academia.buscarMatriculaPorId(idMatricula);

            // Busca pagamentos anteriores
            List<Pagamento> pagamentos = academia.listarPagamentosPorMatricula(idMatricula);

            // Calcula total pago
            double totalPago = 0.0;
            for (Pagamento p : pagamentos) {
                totalPago += p.getValor();
            }

            double valorRestante = matricula.getValorMensal() - totalPago;
            if (valorRestante < 0) valorRestante = 0;

            lblTotalPago.setText(String.format("Total Pago: R$ %.2f", totalPago));
            lblValorRestante.setText(String.format("Valor Restante: R$ %.2f", valorRestante));

            // Atualiza tabela
            modeloTabela.setRowCount(0);
            for (Pagamento p : pagamentos) {
                modeloTabela.addRow(new Object[] {
                    p.getDataPagamento(),
                    p.getFormaPagamento().getDetalhes(),
                    String.format("R$ %.2f", p.getValor())
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar informações: " + ex.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarPagamento() {
        try {
            String matriculaSel = (String) cmbMatricula.getSelectedItem();
            String idMatricula = matriculaSel.split(" - ")[0];
            Matricula matricula = academia.buscarMatriculaPorId(idMatricula);

            double valor = Double.parseDouble(txtValor.getText().trim());
            String forma = (String) cmbFormaPagamento.getSelectedItem();

            FormaPagamento formaPagamento;

            // Cria a forma de pagamento correta
            if ("PIX".equals(forma)) {
                formaPagamento = new PagamentoPix();
            } else if ("CARTAO".equals(forma)) {
                String tipoCartao = (String) cmbTipoCartao.getSelectedItem();
                formaPagamento = new PagamentoCartao(tipoCartao);
            } else {
                formaPagamento = new PagamentoDinheiro();
            }

            Pagamento pagamento = academia.registrarPagamento(matricula, formaPagamento, valor, new Date());
            pagamentoRepo.adicionar(pagamento);

            JOptionPane.showMessageDialog(this, "Pagamento registrado com sucesso!\nID: " + pagamento.getId());

            // Atualiza as informações
            atualizarInformacoesMatricula();
            txtValor.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
