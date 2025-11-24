package br.trabalho3.sistema.ui;

import br.trabalho3.sistema.model.Academia;
import br.trabalho3.sistema.persistence.*;
import javax.swing.*;
import java.awt.*;

/**
 * Tela principal do sistema de academia.
 * Esta é a tela inicial que apresenta o menu principal com todas
 * as funcionalidades do sistema.
 */
public class TelaPrincipal extends JFrame {

    /**
     * Instância da Academia (Singleton).
     * Central de todas as operações do sistema.
     */
    private Academia academia;

    /**
     * Repositórios para persistência de dados.
     * Estes são usados para salvar e carregar dados dos arquivos CSV.
     */
    private AlunoRepository alunoRepo;
    private InstrutorRepository instrutorRepo;
    private PlanoRepository planoRepo;
    private MatriculaRepository matriculaRepo;
    private PagamentoRepository pagamentoRepo;

    // Componentes da interface gráfica
    private JLabel lblTitulo;
    private JButton btnCadastrarAluno;
    private JButton btnCadastrarInstrutor;
    private JButton btnCadastrarPlano;
    private JButton btnRealizarMatricula;
    private JButton btnRegistrarPagamento;
    private JButton btnRelatorios;
    private JButton btnListarAlunos;
    private JButton btnSair;

    /**
     * Construtor da tela principal.
     * Inicializa todos os componentes e configura a interface.
     */
    public TelaPrincipal() {
        // Obtém a instância única da Academia (padrão Singleton)
        this.academia = Academia.getInstance();

        // Inicializa os repositórios
        inicializarRepositorios();

        // Carrega dados dos arquivos CSV
        carregarDadosIniciais();

        // Configura a janela
        configurarJanela();

        // Inicializa os componentes da interface
        inicializarComponentes();

        // Configura o layout e adiciona componentes
        montarLayout();

        // Configura eventos dos botões
        configurarEventos();
    }

    /**
     * Inicializa os repositórios de dados.
     * Os repositórios são responsáveis por salvar e carregar
     * dados dos arquivos CSV.
     */
    private void inicializarRepositorios() {
        // Ordem é importante: repositórios sem dependências primeiro
        alunoRepo = new AlunoRepository();
        instrutorRepo = new InstrutorRepository();
        planoRepo = new PlanoRepository();

        // MatriculaRepo precisa de AlunoRepo e PlanoRepo
        matriculaRepo = new MatriculaRepository(alunoRepo, planoRepo);

        // PagamentoRepo precisa de MatriculaRepo
        pagamentoRepo = new PagamentoRepository(matriculaRepo);
    }

    /**
     * Carrega dados iniciais dos arquivos CSV para a memória.
     * Este método é chamado ao iniciar o sistema.
     */
    private void carregarDadosIniciais() {
        try {
            // Carrega alunos
            academia.listarAlunos().clear();
            alunoRepo.buscarTodos().forEach(aluno -> {
                try {
                    academia.adicionarAluno(aluno);
                } catch (Exception e) {
                    // Ignora duplicatas que já podem estar na memória
                }
            });

            // Carrega instrutores
            academia.listarInstrutores().clear();
            instrutorRepo.buscarTodos().forEach(instrutor -> {
                try {
                    academia.adicionarInstrutor(instrutor);
                } catch (Exception e) {
                    // Ignora duplicatas
                }
            });

            // Carrega planos
            academia.listarPlanos().clear();
            planoRepo.buscarTodos().forEach(plano -> {
                try {
                    academia.adicionarPlano(plano);
                } catch (Exception e) {
                    // Ignora duplicatas
                }
            });

            System.out.println("Dados carregados com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao carregar dados iniciais: " + e.getMessage());
        }
    }

    /**
     * Configura as propriedades básicas da janela.
     */
    private void configurarJanela() {
        setTitle("Sistema de Academia");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza na tela
        setResizable(false); // Impede redimensionamento
    }

    /**
     * Inicializa todos os componentes da interface gráfica.
     */
    private void inicializarComponentes() {
        // Título
        lblTitulo = new JLabel("Sistema de Gerenciamento de Academia");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setForeground(new Color(33, 37, 41)); // Cor escura para contraste

        // Botões do menu
        btnCadastrarAluno = new JButton("Cadastrar Aluno");
        btnCadastrarInstrutor = new JButton("Cadastrar Instrutor");
        btnCadastrarPlano = new JButton("Cadastrar Plano");
        btnRealizarMatricula = new JButton("Realizar Matrícula");
        btnRegistrarPagamento = new JButton("Registrar Pagamento");
        btnRelatorios = new JButton("Relatórios");
        btnListarAlunos = new JButton("Listar Alunos");
        btnSair = new JButton("Sair");

        // Configura aparência dos botões
        configurarBotao(btnCadastrarAluno);
        configurarBotao(btnCadastrarInstrutor);
        configurarBotao(btnCadastrarPlano);
        configurarBotao(btnRealizarMatricula);
        configurarBotao(btnRegistrarPagamento);
        configurarBotao(btnRelatorios);
        configurarBotao(btnListarAlunos);
        configurarBotao(btnSair);

        // Botão Sair com cor diferente (vermelho)
        btnSair.setBackground(new Color(220, 53, 69)); // Vermelho
        btnSair.setForeground(Color.WHITE);
        btnSair.setOpaque(true); // Garante renderização da cor
    }

    /**
     * Configura a aparência padrão de um botão.
     *
     * @param botao Botão a ser configurado
     */
    private void configurarBotao(JButton botao) {
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setPreferredSize(new Dimension(250, 40));
        botao.setBackground(new Color(0, 123, 255)); // Azul
        botao.setForeground(Color.WHITE);
        botao.setOpaque(true); // Garante que a cor de fundo seja renderizada
        botao.setBorderPainted(false); // Remove borda padrão
        botao.setFocusPainted(false); // Remove borda de foco
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cursor de mão ao passar por cima
    }

    /**
     * Monta o layout da tela organizando os componentes.
     */
    private void montarLayout() {
        // Painel principal com layout BorderLayout
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelPrincipal.setBackground(Color.WHITE);

        // Adiciona título no topo
        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // Painel central com os botões em grade
        JPanel painelBotoes = new JPanel(new GridLayout(8, 1, 10, 10));
        painelBotoes.setBackground(Color.WHITE);
        painelBotoes.add(btnCadastrarAluno);
        painelBotoes.add(btnCadastrarInstrutor);
        painelBotoes.add(btnCadastrarPlano);
        painelBotoes.add(btnRealizarMatricula);
        painelBotoes.add(btnRegistrarPagamento);
        painelBotoes.add(btnRelatorios);
        painelBotoes.add(btnListarAlunos);
        painelBotoes.add(btnSair);

        painelPrincipal.add(painelBotoes, BorderLayout.CENTER);

        // Adiciona painel principal à janela
        add(painelPrincipal);
    }

    /**
     * Configura os eventos (listeners) dos botões.
     * CONCEITO: Event Handling em Java Swing
     */
    private void configurarEventos() {
        // Cadastrar Aluno
        btnCadastrarAluno.addActionListener(e -> abrirTelaCadastroAluno());

        // Cadastrar Instrutor
        btnCadastrarInstrutor.addActionListener(e -> abrirTelaCadastroInstrutor());

        // Cadastrar Plano
        btnCadastrarPlano.addActionListener(e -> abrirTelaCadastroPlano());

        // Realizar Matrícula
        btnRealizarMatricula.addActionListener(e -> abrirTelaMatricula());

        // Registrar Pagamento
        btnRegistrarPagamento.addActionListener(e -> abrirTelaPagamento());

        // Relatórios
        btnRelatorios.addActionListener(e -> abrirTelaRelatorios());

        // Listar Alunos
        btnListarAlunos.addActionListener(e -> listarAlunos());

        // Sair
        btnSair.addActionListener(e -> sairDoSistema());
    }

    // ========== MÉTODOS DE ABERTURA DE TELAS ==========

    private void abrirTelaCadastroAluno() {
        new TelaCadastroAluno(this, academia, alunoRepo).setVisible(true);
    }

    private void abrirTelaCadastroInstrutor() {
        new TelaCadastroInstrutor(this, academia, instrutorRepo).setVisible(true);
    }

    private void abrirTelaCadastroPlano() {
        new TelaCadastroPlano(this, academia, planoRepo).setVisible(true);
    }

    private void abrirTelaMatricula() {
        new TelaMatricula(this, academia, matriculaRepo).setVisible(true);
    }

    private void abrirTelaPagamento() {
        new TelaPagamento(this, academia, pagamentoRepo).setVisible(true);
    }

    private void abrirTelaRelatorios() {
        new TelaRelatorios(this, academia).setVisible(true);
    }

    private void listarAlunos() {
        new TelaListarAlunos(this, academia).setVisible(true);
    }

    /**
     * Salva dados e fecha o sistema.
     */
    private void sairDoSistema() {
        int opcao = JOptionPane.showConfirmDialog(this,
            "Deseja realmente sair do sistema?",
            "Confirmar Saída",
            JOptionPane.YES_NO_OPTION);

        if (opcao == JOptionPane.YES_OPTION) {
            // Salva todos os dados antes de sair
            salvarTodosDados();

            JOptionPane.showMessageDialog(this,
                "Dados salvos com sucesso!\nAté logo!",
                "Sistema Encerrado",
                JOptionPane.INFORMATION_MESSAGE);

            System.exit(0);
        }
    }

    /**
     * Salva todos os dados nos arquivos CSV.
     */
    private void salvarTodosDados() {
        try {
            alunoRepo.salvarTodos(academia.listarAlunos());
            instrutorRepo.salvarTodos(academia.listarInstrutores());
            planoRepo.salvarTodos(academia.listarPlanos());
            matriculaRepo.salvarTodos(academia.listarMatriculas());
            pagamentoRepo.salvarTodos(academia.listarPagamentos());
        } catch (Exception e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    /**
     * Método main para iniciar o sistema.
     * Define o Look and Feel e cria a tela principal.
     */
    public static void main(String[] args) {
        // Executa na thread de eventos do Swing
        SwingUtilities.invokeLater(() -> {
            try {
                // Define o Look and Feel do sistema operacional
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Se falhar, usa o padrão
            }

            // Cria e exibe a tela principal
            TelaPrincipal tela = new TelaPrincipal();
            tela.setVisible(true);
        });
    }
}
