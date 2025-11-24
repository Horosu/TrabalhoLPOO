package br.trabalho3.sistema.ui;

import br.trabalho3.sistema.model.*;
import br.trabalho3.sistema.persistence.AlunoRepository;
import br.trabalho3.sistema.utils.*;
import br.trabalho3.sistema.exceptions.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Tela para cadastro, edição e visualização de alunos.
 * Permite adicionar, editar, excluir e listar alunos do sistema.
 */
public class TelaCadastroAluno extends JDialog {

    private static final long serialVersionUID = 1L;

    private Academia academia;
    private AlunoRepository alunoRepo;

    // Componentes de formulário
    private JTextField txtCpf;
    private JTextField txtNome;
    private JTextField txtTelefone;
    private JTextField txtEmail;
    private JTextField txtMatriculaId;

    // Botões
    private JButton btnSalvar;
    private JButton btnLimpar;
    private JButton btnFechar;

    // Tabela de alunos
    private JTable tabelaAlunos;
    private DefaultTableModel modeloTabela;

    /**
     * Construtor da tela de cadastro de alunos.
     *
     * @param parent Janela pai (TelaPrincipal)
     * @param academia Instância do sistema
     * @param alunoRepo Repositório de alunos
     */
    public TelaCadastroAluno(Frame parent, Academia academia, AlunoRepository alunoRepo) {
        super(parent, "Cadastro de Alunos", true); // true = modal
        this.academia = academia;
        this.alunoRepo = alunoRepo;

        configurarJanela();
        inicializarComponentes();
        montarLayout();
        configurarEventos();
        atualizarTabela();
    }

    private void configurarJanela() {
        setSize(800, 600);
        setLocationRelativeTo(getParent());
        setResizable(false);
    }

    private void inicializarComponentes() {
        // Campos de texto
        txtCpf = new JTextField(15);
        txtNome = new JTextField(30);
        txtTelefone = new JTextField(15);
        txtEmail = new JTextField(25);
        txtMatriculaId = new JTextField(10);

        // Botões
        btnSalvar = new JButton("Salvar");
        btnLimpar = new JButton("Limpar");
        btnFechar = new JButton("Fechar");

        // Configura aparência dos botões
        btnSalvar.setBackground(new Color(40, 167, 69));
        btnSalvar.setForeground(Color.WHITE);

        btnLimpar.setBackground(new Color(255, 193, 7));

        btnFechar.setBackground(new Color(108, 117, 125));
        btnFechar.setForeground(Color.WHITE);

        // Tabela
        String[] colunas = {"CPF", "Nome", "Telefone", "Email", "Matrícula ID"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabela não editável
            }
        };
        tabelaAlunos = new JTable(modeloTabela);
        tabelaAlunos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void montarLayout() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ===== PAINEL DE FORMULÁRIO (TOPO) =====
        JPanel painelFormulario = new JPanel(new GridBagLayout());
        painelFormulario.setBorder(BorderFactory.createTitledBorder("Dados do Aluno"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Linha 1: CPF
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelFormulario.add(new JLabel("CPF:"), gbc);

        gbc.gridx = 1;
        painelFormulario.add(txtCpf, gbc);

        // Linha 2: Nome
        gbc.gridx = 0;
        gbc.gridy = 1;
        painelFormulario.add(new JLabel("Nome:"), gbc);

        gbc.gridx = 1;
        painelFormulario.add(txtNome, gbc);

        // Linha 3: Telefone
        gbc.gridx = 0;
        gbc.gridy = 2;
        painelFormulario.add(new JLabel("Telefone:"), gbc);

        gbc.gridx = 1;
        painelFormulario.add(txtTelefone, gbc);

        // Linha 4: Email
        gbc.gridx = 0;
        gbc.gridy = 3;
        painelFormulario.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        painelFormulario.add(txtEmail, gbc);

        // Linha 5: Matrícula ID
        gbc.gridx = 0;
        gbc.gridy = 4;
        painelFormulario.add(new JLabel("Matrícula ID:"), gbc);

        gbc.gridx = 1;
        painelFormulario.add(txtMatriculaId, gbc);

        // ===== PAINEL DE BOTÕES =====
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnFechar);

        // ===== PAINEL SUPERIOR (FORMULÁRIO + BOTÕES) =====
        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.add(painelFormulario, BorderLayout.CENTER);
        painelSuperior.add(painelBotoes, BorderLayout.SOUTH);

        // ===== PAINEL DE TABELA (CENTRO) =====
        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBorder(BorderFactory.createTitledBorder("Alunos Cadastrados"));
        JScrollPane scrollPane = new JScrollPane(tabelaAlunos);
        painelTabela.add(scrollPane, BorderLayout.CENTER);

        // Adiciona ao painel principal
        painelPrincipal.add(painelSuperior, BorderLayout.NORTH);
        painelPrincipal.add(painelTabela, BorderLayout.CENTER);

        add(painelPrincipal);
    }

    private void configurarEventos() {
        // Botão Salvar
        btnSalvar.addActionListener(e -> salvarAluno());

        // Botão Limpar
        btnLimpar.addActionListener(e -> limparCampos());

        // Botão Fechar
        btnFechar.addActionListener(e -> dispose());

        // Duplo clique na tabela carrega dados no formulário
        tabelaAlunos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    carregarAlunoSelecionado();
                }
            }
        });
    }

    /**
     * Salva um novo aluno ou atualiza um existente.
     *
     * Validação e tratamento de exceções:
     * - Valida CPF usando ValidadorCPF
     * - Valida Email usando ValidadorEmail
     * - Lança exceções customizadas se dados inválidos
     */
    private void salvarAluno() {
        try {
            // Obtém dados dos campos
            String cpf = txtCpf.getText().trim();
            String nome = txtNome.getText().trim();
            String telefone = txtTelefone.getText().trim();
            String email = txtEmail.getText().trim();
            String matriculaId = txtMatriculaId.getText().trim();

            // Validações básicas
            if (cpf.isEmpty() || nome.isEmpty() || telefone.isEmpty() ||
                email.isEmpty() || matriculaId.isEmpty()) {
                throw new DadosInvalidosException("Todos os campos são obrigatórios!");
            }

            // VALIDAÇÃO DE CPF usando classe utilitária
            if (!ValidadorCPF.validarCPF(cpf)) {
                throw new DadosInvalidosException("CPF inválido! Verifique e tente novamente.");
            }

            // VALIDAÇÃO DE EMAIL usando classe utilitária
            if (!ValidadorEmail.validarEmail(email)) {
                throw new DadosInvalidosException("Email inválido! Verifique e tente novamente.");
            }

            // Formata CPF para padrão XXX.XXX.XXX-XX
            cpf = ValidadorCPF.formatarCPF(cpf);

            // Normaliza email (minúsculas)
            email = ValidadorEmail.normalizarEmail(email);

            // Cria o aluno
            Aluno aluno = new Aluno(cpf, nome, telefone, email, matriculaId);

            // Verifica se é atualização ou novo cadastro
            try {
                // Tenta buscar aluno existente - se não lançar exceção, significa que existe
                academia.buscarAlunoPorCpf(cpf);
                // Se chegou aqui, o aluno existe - atualizar
                academia.atualizarAluno(aluno);
                alunoRepo.atualizar(aluno);

                JOptionPane.showMessageDialog(this,
                    "Aluno atualizado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

            } catch (UsuarioNaoEncontradoException ex) {
                // Aluno não existe - adicionar novo
                academia.adicionarAluno(aluno);
                alunoRepo.adicionar(aluno);

                JOptionPane.showMessageDialog(this,
                    "Aluno cadastrado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
            }

            // Limpa campos e atualiza tabela
            limparCampos();
            atualizarTabela();

        } catch (DadosInvalidosException ex) {
            // TRATAMENTO DE EXCEÇÃO CUSTOMIZADA
            JOptionPane.showMessageDialog(this,
                ex.getMessage(),
                "Erro de Validação",
                JOptionPane.ERROR_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao salvar aluno: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Carrega os dados do aluno selecionado na tabela para o formulário.
     */
    private void carregarAlunoSelecionado() {
        int linhaSelecionada = tabelaAlunos.getSelectedRow();

        if (linhaSelecionada >= 0) {
            txtCpf.setText(modeloTabela.getValueAt(linhaSelecionada, 0).toString());
            txtNome.setText(modeloTabela.getValueAt(linhaSelecionada, 1).toString());
            txtTelefone.setText(modeloTabela.getValueAt(linhaSelecionada, 2).toString());
            txtEmail.setText(modeloTabela.getValueAt(linhaSelecionada, 3).toString());
            txtMatriculaId.setText(modeloTabela.getValueAt(linhaSelecionada, 4).toString());
        }
    }

    /**
     * Atualiza a tabela com todos os alunos cadastrados.
     */
    private void atualizarTabela() {
        // Limpa a tabela
        modeloTabela.setRowCount(0);

        // Adiciona todos os alunos
        for (Aluno aluno : academia.listarAlunos()) {
            Object[] linha = {
                aluno.getCpf(),
                aluno.getNome(),
                aluno.getTelefone(),
                aluno.getEmail(),
                aluno.getMatriculaId()
            };
            modeloTabela.addRow(linha);
        }
    }

    /**
     * Limpa todos os campos do formulário.
     */
    private void limparCampos() {
        txtCpf.setText("");
        txtNome.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtMatriculaId.setText("");
        txtCpf.requestFocus(); // Foco no primeiro campo
    }
}
