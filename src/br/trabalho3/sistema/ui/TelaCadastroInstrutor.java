package br.trabalho3.sistema.ui;

import br.trabalho3.sistema.model.*;
import br.trabalho3.sistema.persistence.InstrutorRepository;
import br.trabalho3.sistema.utils.*;
import br.trabalho3.sistema.exceptions.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Tela para cadastro de instrutores.
 * Similar à TelaCadastroAluno, com campos adicionais: especialidade e CREF.
 */
public class TelaCadastroInstrutor extends JDialog {

    private Academia academia;
    private InstrutorRepository instrutorRepo;

    private JTextField txtCpf, txtNome, txtTelefone, txtEmail, txtEspecialidade, txtCref;
    private JButton btnSalvar, btnLimpar, btnFechar;
    private JTable tabelaInstrutores;
    private DefaultTableModel modeloTabela;

    public TelaCadastroInstrutor(Frame parent, Academia academia, InstrutorRepository instrutorRepo) {
        super(parent, "Cadastro de Instrutores", true);
        this.academia = academia;
        this.instrutorRepo = instrutorRepo;

        setSize(850, 600);
        setLocationRelativeTo(parent);

        inicializarComponentes();
        montarLayout();
        configurarEventos();
        atualizarTabela();
    }

    private void inicializarComponentes() {
        txtCpf = new JTextField(15);
        txtNome = new JTextField(30);
        txtTelefone = new JTextField(15);
        txtEmail = new JTextField(25);
        txtEspecialidade = new JTextField(20);
        txtCref = new JTextField(15);

        btnSalvar = new JButton("Salvar");
        btnLimpar = new JButton("Limpar");
        btnFechar = new JButton("Fechar");

        String[] colunas = {"CPF", "Nome", "Telefone", "Email", "Especialidade", "CREF"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaInstrutores = new JTable(modeloTabela);
    }

    private void montarLayout() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBorder(BorderFactory.createTitledBorder("Dados do Instrutor"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int linha = 0;
        gbc.gridx = 0; gbc.gridy = linha;
        painelForm.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        painelForm.add(txtCpf, gbc);

        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        painelForm.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        painelForm.add(txtNome, gbc);

        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        painelForm.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        painelForm.add(txtTelefone, gbc);

        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        painelForm.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        painelForm.add(txtEmail, gbc);

        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        painelForm.add(new JLabel("Especialidade:"), gbc);
        gbc.gridx = 1;
        painelForm.add(txtEspecialidade, gbc);

        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        painelForm.add(new JLabel("CREF:"), gbc);
        gbc.gridx = 1;
        painelForm.add(txtCref, gbc);

        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnFechar);

        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.add(painelForm, BorderLayout.CENTER);
        painelSuperior.add(painelBotoes, BorderLayout.SOUTH);

        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.add(new JScrollPane(tabelaInstrutores), BorderLayout.CENTER);

        painelPrincipal.add(painelSuperior, BorderLayout.NORTH);
        painelPrincipal.add(painelTabela, BorderLayout.CENTER);

        add(painelPrincipal);
    }

    private void configurarEventos() {
        btnSalvar.addActionListener(e -> salvarInstrutor());
        btnLimpar.addActionListener(e -> limparCampos());
        btnFechar.addActionListener(e -> dispose());
    }

    private void salvarInstrutor() {
        try {
            String cpf = ValidadorCPF.formatarCPF(txtCpf.getText().trim());
            String nome = txtNome.getText().trim();
            String telefone = txtTelefone.getText().trim();
            String email = ValidadorEmail.normalizarEmail(txtEmail.getText().trim());
            String especialidade = txtEspecialidade.getText().trim();
            String cref = txtCref.getText().trim();

            if (!ValidadorCPF.validarCPF(cpf)) {
                throw new DadosInvalidosException("CPF inválido!");
            }

            if (!ValidadorEmail.validarEmail(email)) {
                throw new DadosInvalidosException("Email inválido!");
            }

            Instrutor instrutor = new Instrutor(cpf, nome, telefone, email, especialidade, cref);

            try {
                academia.buscarInstrutorPorCpf(cpf);
                academia.atualizarInstrutor(instrutor);
                instrutorRepo.atualizar(instrutor);
                JOptionPane.showMessageDialog(this, "Instrutor atualizado!");
            } catch (UsuarioNaoEncontradoException ex) {
                academia.adicionarInstrutor(instrutor);
                instrutorRepo.adicionar(instrutor);
                JOptionPane.showMessageDialog(this, "Instrutor cadastrado!");
            }

            limparCampos();
            atualizarTabela();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Instrutor i : academia.listarInstrutores()) {
            modeloTabela.addRow(new Object[]{i.getCpf(), i.getNome(), i.getTelefone(),
                i.getEmail(), i.getEspecialidade(), i.getCref()});
        }
    }

    private void limparCampos() {
        txtCpf.setText("");
        txtNome.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtEspecialidade.setText("");
        txtCref.setText("");
    }
}
