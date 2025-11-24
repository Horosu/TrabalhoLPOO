package br.trabalho3.sistema.ui;

import br.trabalho3.sistema.model.*;
import br.trabalho3.sistema.persistence.PlanoRepository;
import br.trabalho3.sistema.exceptions.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Tela para cadastro de planos.
 *
 * DEMONSTRAÇÃO DE POLIMORFISMO:
 * - Ao selecionar o tipo de plano (Comum, Premium, Estudante), o sistema
 *   cria a instância correta e exibe o preço final calculado
 * - Cada tipo de plano calcula o preço final de forma diferente
 */
public class TelaCadastroPlano extends JDialog {

    private Academia academia;
    private PlanoRepository planoRepo;

    private JTextField txtId, txtNome, txtPrecoBase, txtDuracao;
    private JComboBox<String> cmbTipo;
    private JLabel lblPrecoFinal;
    private JButton btnSalvar, btnLimpar, btnFechar;
    private JTable tabelaPlanos;
    private DefaultTableModel modeloTabela;

    public TelaCadastroPlano(Frame parent, Academia academia, PlanoRepository planoRepo) {
        super(parent, "Cadastro de Planos", true);
        this.academia = academia;
        this.planoRepo = planoRepo;

        setSize(700, 550);
        setLocationRelativeTo(parent);

        inicializarComponentes();
        montarLayout();
        configurarEventos();
        atualizarTabela();
    }

    private void inicializarComponentes() {
        txtId = new JTextField(10);
        txtNome = new JTextField(25);
        txtPrecoBase = new JTextField(10);
        txtDuracao = new JTextField(10);

        // ComboBox com tipos de plano
        cmbTipo = new JComboBox<>(new String[]{"COMUM", "PREMIUM", "ESTUDANTE"});

        lblPrecoFinal = new JLabel("Preço Final: R$ 0,00");
        lblPrecoFinal.setFont(new Font("Arial", Font.BOLD, 14));
        lblPrecoFinal.setForeground(new Color(0, 128, 0));

        btnSalvar = new JButton("Salvar");
        btnLimpar = new JButton("Limpar");
        btnFechar = new JButton("Fechar");

        String[] colunas = {"ID", "Nome", "Preço Base", "Preço Final", "Duração", "Tipo"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaPlanos = new JTable(modeloTabela);
    }

    private void montarLayout() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBorder(BorderFactory.createTitledBorder("Dados do Plano"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int linha = 0;
        gbc.gridx = 0; gbc.gridy = linha;
        painelForm.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        painelForm.add(txtId, gbc);

        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        painelForm.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        painelForm.add(txtNome, gbc);

        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        painelForm.add(new JLabel("Preço Base (R$):"), gbc);
        gbc.gridx = 1;
        painelForm.add(txtPrecoBase, gbc);

        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        painelForm.add(new JLabel("Duração (dias):"), gbc);
        gbc.gridx = 1;
        painelForm.add(txtDuracao, gbc);

        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        painelForm.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        painelForm.add(cmbTipo, gbc);

        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        gbc.gridwidth = 2;
        painelForm.add(lblPrecoFinal, gbc);

        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnFechar);

        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.add(painelForm, BorderLayout.CENTER);
        painelSuperior.add(painelBotoes, BorderLayout.SOUTH);

        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.add(new JScrollPane(tabelaPlanos), BorderLayout.CENTER);

        painelPrincipal.add(painelSuperior, BorderLayout.NORTH);
        painelPrincipal.add(painelTabela, BorderLayout.CENTER);

        add(painelPrincipal);
    }

    private void configurarEventos() {
        btnSalvar.addActionListener(e -> salvarPlano());
        btnLimpar.addActionListener(e -> limparCampos());
        btnFechar.addActionListener(e -> dispose());

        // Atualiza preço final ao mudar tipo ou preço base
        // DEMONSTRAÇÃO DE POLIMORFISMO
        txtPrecoBase.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                calcularPrecoFinal();
            }
        });

        cmbTipo.addActionListener(e -> calcularPrecoFinal());
    }

    /**
     * Calcula e exibe o preço final baseado no tipo de plano selecionado.
     *
     * DEMONSTRAÇÃO DE POLIMORFISMO:
     * - Cria temporariamente a instância correta do plano
     * - Chama o método calcularPrecoFinal() que é implementado
     *   de forma diferente em cada subclasse
     */
    private void calcularPrecoFinal() {
        try {
            double precoBase = Double.parseDouble(txtPrecoBase.getText().trim());
            String tipo = (String) cmbTipo.getSelectedItem();

            Plano plano;

            // POLIMORFISMO: Cria a instância correta baseado no tipo
            switch (tipo) {
                case "COMUM":
                    plano = new PlanoComum("TEMP", "Temp", precoBase, 30);
                    break;
                case "PREMIUM":
                    plano = new PlanoPremium("TEMP", "Temp", precoBase, 30);
                    break;
                case "ESTUDANTE":
                    plano = new PlanoEstudante("TEMP", "Temp", precoBase, 30);
                    break;
                default:
                    return;
            }

            // Chama o método polimórfico que calcula o preço final
            double precoFinal = plano.calcularPrecoFinal();

            lblPrecoFinal.setText(String.format("Preço Final: R$ %.2f", precoFinal));

        } catch (NumberFormatException e) {
            lblPrecoFinal.setText("Preço Final: R$ 0,00");
        }
    }

    private void salvarPlano() {
        try {
            String id = txtId.getText().trim();
            String nome = txtNome.getText().trim();
            double precoBase = Double.parseDouble(txtPrecoBase.getText().trim());
            int duracao = Integer.parseInt(txtDuracao.getText().trim());
            String tipo = (String) cmbTipo.getSelectedItem();

            Plano plano;

            // POLIMORFISMO: Cria a instância correta
            switch (tipo) {
                case "COMUM":
                    plano = new PlanoComum(id, nome, precoBase, duracao);
                    break;
                case "PREMIUM":
                    plano = new PlanoPremium(id, nome, precoBase, duracao);
                    break;
                case "ESTUDANTE":
                    plano = new PlanoEstudante(id, nome, precoBase, duracao);
                    break;
                default:
                    throw new DadosInvalidosException("Tipo de plano inválido!");
            }

            academia.adicionarPlano(plano);
            planoRepo.adicionar(plano);

            JOptionPane.showMessageDialog(this, "Plano cadastrado com sucesso!");

            limparCampos();
            atualizarTabela();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Plano p : academia.listarPlanos()) {
            modeloTabela.addRow(new Object[]{
                p.getId(),
                p.getNome(),
                String.format("R$ %.2f", p.getPrecoBase()),
                String.format("R$ %.2f", p.calcularPrecoFinal()), // POLIMORFISMO
                p.getDuracao() + " dias",
                p.getTipo()
            });
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtPrecoBase.setText("");
        txtDuracao.setText("");
        cmbTipo.setSelectedIndex(0);
        lblPrecoFinal.setText("Preço Final: R$ 0,00");
    }
}
