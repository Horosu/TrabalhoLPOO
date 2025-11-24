package br.trabalho3.sistema.ui;

import br.trabalho3.sistema.model.*;
import br.trabalho3.sistema.persistence.MatriculaRepository;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

public class TelaMatricula extends JDialog {

    private Academia academia;
    private MatriculaRepository matriculaRepo;
    private JComboBox<String> cmbAluno, cmbPlano;
    private JTextField txtDataInicio, txtDataFim;
    private JLabel lblValorMensal;
    private JButton btnConfirmar, btnFechar;

    public TelaMatricula(Frame parent, Academia academia, MatriculaRepository matriculaRepo) {
        super(parent, "Realizar Matrícula", true);
        this.academia = academia;
        this.matriculaRepo = matriculaRepo;

        setSize(500, 400);
        setLocationRelativeTo(parent);

        inicializarComponentes();
        montarLayout();
        configurarEventos();
    }

    private void inicializarComponentes() {
        cmbAluno = new JComboBox<>();
        cmbPlano = new JComboBox<>();
        txtDataInicio = new JTextField(10);
        txtDataFim = new JTextField(10);
        lblValorMensal = new JLabel("Valor Mensal: R$ 0,00");
        btnConfirmar = new JButton("Confirmar Matrícula");
        btnFechar = new JButton("Fechar");

        // Popula comboboxes
        for (Aluno a : academia.listarAlunos()) {
            cmbAluno.addItem(a.getCpf() + " - " + a.getNome());
        }

        for (Plano p : academia.listarPlanos()) {
            cmbPlano.addItem(p.getId() + " - " + p.getNome() + " (" + p.getTipo() + ")");
        }

        // Data padrão: hoje
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtDataInicio.setText(sdf.format(new Date()));

        // Data fim: 1 ano depois
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        txtDataFim.setText(sdf.format(cal.getTime()));
    }

    private void montarLayout() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int linha = 0;
        gbc.gridx = 0; gbc.gridy = linha;
        painel.add(new JLabel("Aluno:"), gbc);
        gbc.gridx = 1;
        painel.add(cmbAluno, gbc);

        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        painel.add(new JLabel("Plano:"), gbc);
        gbc.gridx = 1;
        painel.add(cmbPlano, gbc);

        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        painel.add(new JLabel("Data Início:"), gbc);
        gbc.gridx = 1;
        painel.add(txtDataInicio, gbc);

        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        painel.add(new JLabel("Data Fim:"), gbc);
        gbc.gridx = 1;
        painel.add(txtDataFim, gbc);

        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        gbc.gridwidth = 2;
        painel.add(lblValorMensal, gbc);

        linha++;
        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.add(btnConfirmar);
        painelBotoes.add(btnFechar);
        gbc.gridy = linha;
        painel.add(painelBotoes, gbc);

        add(painel);
    }

    private void configurarEventos() {
        cmbPlano.addActionListener(e -> calcularValorMensal());
        btnConfirmar.addActionListener(e -> realizarMatricula());
        btnFechar.addActionListener(e -> dispose());
    }

    private void calcularValorMensal() {
        try {
            String planoSel = (String) cmbPlano.getSelectedItem();
            String idPlano = planoSel.split(" - ")[0];
            Plano plano = academia.buscarPlanoPorId(idPlano);

            // POLIMORFISMO: calcularPrecoFinal() é implementado de forma diferente
            double valorMensal = plano.calcularPrecoFinal();
            lblValorMensal.setText(String.format("Valor Mensal: R$ %.2f", valorMensal));

        } catch (Exception ex) {
            lblValorMensal.setText("Valor Mensal: R$ 0,00");
        }
    }

    private void realizarMatricula() {
        try {
            String alunoSel = (String) cmbAluno.getSelectedItem();
            String cpfAluno = alunoSel.split(" - ")[0];
            Aluno aluno = academia.buscarAlunoPorCpf(cpfAluno);

            String planoSel = (String) cmbPlano.getSelectedItem();
            String idPlano = planoSel.split(" - ")[0];
            Plano plano = academia.buscarPlanoPorId(idPlano);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dataInicio = sdf.parse(txtDataInicio.getText());
            Date dataFim = sdf.parse(txtDataFim.getText());

            Matricula matricula = academia.realizarMatricula(aluno, plano, dataInicio, dataFim);
            matriculaRepo.adicionar(matricula);

            JOptionPane.showMessageDialog(this,
                String.format("Matrícula realizada com sucesso!\\nID: %s\\nValor Mensal: R$ %.2f",
                    matricula.getId(), matricula.getValorMensal()));

            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
