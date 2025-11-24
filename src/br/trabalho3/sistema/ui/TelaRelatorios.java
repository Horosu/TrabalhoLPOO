package br.trabalho3.sistema.ui;

import br.trabalho3.sistema.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaRelatorios extends JDialog {

    private Academia academia;
    private JTabbedPane abas;

    public TelaRelatorios(Frame parent, Academia academia) {
        super(parent, "Relatórios", true);
        this.academia = academia;

        setSize(800, 600);
        setLocationRelativeTo(parent);

        inicializarComponentes();
        montarLayout();
    }

    private void inicializarComponentes() {
        abas = new JTabbedPane();

        // Aba 1: Alunos
        abas.addTab("Alunos", criarPainelAlunos());

        // Aba 2: Matrículas
        abas.addTab("Matrículas", criarPainelMatriculas());

        // Aba 3: Financeiro
        abas.addTab("Financeiro", criarPainelFinanceiro());
    }

    private JPanel criarPainelAlunos() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] colunas = {"CPF", "Nome", "Telefone", "Email"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

        for (Aluno a : academia.listarAlunos()) {
            modelo.addRow(new Object[]{a.getCpf(), a.getNome(), a.getTelefone(), a.getEmail()});
        }

        JTable tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);

        JLabel lblTotal = new JLabel("Total de Alunos: " + academia.listarAlunos().size());
        lblTotal.setFont(new Font("Arial", Font.BOLD, 14));

        painel.add(lblTotal, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarPainelMatriculas() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] colunas = {"ID", "Aluno", "Plano", "Valor", "Status"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

        int ativas = 0;
        for (Matricula m : academia.listarMatriculas()) {
            modelo.addRow(new Object[]{
                m.getId(),
                m.getAluno().getNome(),
                m.getPlano().getNome(),
                String.format("R$ %.2f", m.getValorMensal()),
                m.getStatus()
            });

            if (m.estaAtiva()) ativas++;
        }

        JTable tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);

        JLabel lblTotal = new JLabel(String.format("Total: %d | Ativas: %d",
            academia.listarMatriculas().size(), ativas));
        lblTotal.setFont(new Font("Arial", Font.BOLD, 14));

        painel.add(lblTotal, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarPainelFinanceiro() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] colunas = {"ID", "Matrícula", "Valor", "Forma", "Status"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

        double totalConfirmado = 0;
        double totalPendente = 0;

        for (Pagamento p : academia.listarPagamentos()) {
            modelo.addRow(new Object[]{
                p.getId(),
                p.getMatricula().getId(),
                String.format("R$ %.2f", p.getValor()),
                p.getFormaPagamento().getTipo(),
                p.getStatus()
            });

            if (p.getStatus() == StatusPagamento.CONFIRMADO) {
                totalConfirmado += p.getValor();
            } else if (p.getStatus() == StatusPagamento.PENDENTE) {
                totalPendente += p.getValor();
            }
        }

        JTable tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);

        JPanel painelInfo = new JPanel(new GridLayout(3, 1));
        painelInfo.add(new JLabel("Total de Pagamentos: " + academia.listarPagamentos().size()));
        painelInfo.add(new JLabel(String.format("Receitas Confirmadas: R$ %.2f", totalConfirmado)));
        painelInfo.add(new JLabel(String.format("Pendentes: R$ %.2f", totalPendente)));

        painel.add(painelInfo, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);

        return painel;
    }

    private void montarLayout() {
        add(abas);
    }
}
