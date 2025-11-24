package br.trabalho3.sistema.ui;

import br.trabalho3.sistema.model.Academia;
import br.trabalho3.sistema.model.Aluno;
import br.trabalho3.sistema.model.Matricula;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaListarAlunos extends JDialog {

    private Academia academia;
    private JTable tabelaAlunos;
    private DefaultTableModel modeloTabela;
    private JButton btnFechar, btnAtualizar;

    public TelaListarAlunos(Frame parent, Academia academia) {
        super(parent, "Lista de Alunos", true);
        this.academia = academia;

        setSize(900, 500);
        setLocationRelativeTo(parent);

        inicializarComponentes();
        montarLayout();
        configurarEventos();
        carregarAlunos();
    }

    private void inicializarComponentes() {
        String[] colunas = {"CPF", "Nome", "Telefone", "Email", "Plano", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaAlunos = new JTable(modeloTabela);
        tabelaAlunos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaAlunos.getTableHeader().setReorderingAllowed(false);

        btnFechar = new JButton("Fechar");
        btnAtualizar = new JButton("Atualizar");
    }

    private void montarLayout() {
        setLayout(new BorderLayout(10, 10));

        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelTopo.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        JLabel lblTitulo = new JLabel("Alunos Cadastrados");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        painelTopo.add(lblTitulo);
        add(painelTopo, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(tabelaAlunos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnFechar);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void configurarEventos() {
        btnFechar.addActionListener(e -> dispose());
        btnAtualizar.addActionListener(e -> carregarAlunos());
    }

    private void carregarAlunos() {
        modeloTabela.setRowCount(0);

        List<Aluno> alunos = academia.listarAlunos();

        for (Aluno aluno : alunos) {
            String plano = "-";
            String status = "Sem matrícula";

            // Busca matrícula ativa do aluno
            for (Matricula m : academia.listarMatriculas()) {
                if (m.getAluno().getCpf().equals(aluno.getCpf())) {
                    if (m.getPlano() != null) {
                        plano = m.getPlano().getNome();
                    }
                    status = m.getStatus().toString();
                    break;
                }
            }

            modeloTabela.addRow(new Object[]{
                aluno.getCpf(),
                aluno.getNome(),
                aluno.getTelefone(),
                aluno.getEmail(),
                plano,
                status
            });
        }
    }
}
