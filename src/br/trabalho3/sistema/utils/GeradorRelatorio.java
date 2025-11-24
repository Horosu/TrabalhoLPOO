package br.trabalho3.sistema.utils;

import br.trabalho3.sistema.model.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Classe utilitária para geração de relatórios e exportação de dados.
 *
 * Funcionalidades:
 * - Gerar relatórios de alunos (ativos, inativos, por plano)
 * - Gerar relatórios financeiros (receitas, inadimplência)
 * - Exportar dados para CSV
 * - Formatar dados para impressão
 *
 */
public class GeradorRelatorio {

    /**
     * Formato de data padrão usado nos relatórios: dd/MM/yyyy
     */
    private static final SimpleDateFormat FORMATO_DATA = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Construtor privado para evitar instanciação.
     * Esta classe contém apenas métodos estáticos.
     */
    private GeradorRelatorio() {
        // Construtor privado - classe utilitária não deve ser instanciada
    }

    /**
     * Filtra alunos por status de matrícula.
     *
     * @param alunos Lista de todos os alunos
     * @param matriculas Lista de todas as matrículas
     * @param statusFiltro Status desejado (ATIVA, SUSPENSA, CANCELADA, VENCIDA)
     * @return Lista de alunos que possuem matrícula com o status especificado
     */
    public static List<Aluno> filtrarAlunosPorStatus(
            List<Aluno> alunos,
            List<Matricula> matriculas,
            StatusMatricula statusFiltro) {

        List<Aluno> alunosFiltrados = new ArrayList<>();

        // Para cada aluno, verifica se possui matrícula com o status desejado
        for (Aluno aluno : alunos) {
            for (Matricula matricula : matriculas) {
                if (matricula.getAluno().getCpf().equals(aluno.getCpf()) &&
                    matricula.getStatus() == statusFiltro) {
                    alunosFiltrados.add(aluno);
                    break; // Encontrou uma matrícula, não precisa continuar
                }
            }
        }

        return alunosFiltrados;
    }

    /**
     * Calcula o total de receitas confirmadas em um período.
     *
     * @param pagamentos Lista de pagamentos
     * @param dataInicio Data inicial do período (pode ser null para sem limite)
     * @param dataFim Data final do período (pode ser null para sem limite)
     * @return Valor total das receitas confirmadas no período
     */
    public static double calcularReceitasPeriodo(
            List<Pagamento> pagamentos,
            Date dataInicio,
            Date dataFim) {

        double totalReceitas = 0.0;

        for (Pagamento pagamento : pagamentos) {
            // Considera apenas pagamentos confirmados
            if (pagamento.getStatus() == StatusPagamento.CONFIRMADO) {
                Date dataPagamento = pagamento.getDataPagamento();

                // Verifica se está dentro do período
                boolean dentroDoPeriodo = true;

                if (dataInicio != null && dataPagamento.before(dataInicio)) {
                    dentroDoPeriodo = false;
                }

                if (dataFim != null && dataPagamento.after(dataFim)) {
                    dentroDoPeriodo = false;
                }

                if (dentroDoPeriodo) {
                    totalReceitas += pagamento.getValor();
                }
            }
        }

        return totalReceitas;
    }

    /**
     * Filtra pagamentos por status.
     *
     * @param pagamentos Lista de todos os pagamentos
     * @param status Status desejado (PENDENTE, CONFIRMADO, ESTORNADO)
     * @return Lista de pagamentos com o status especificado
     */
    public static List<Pagamento> filtrarPagamentosPorStatus(
            List<Pagamento> pagamentos,
            StatusPagamento status) {

        List<Pagamento> pagamentosFiltrados = new ArrayList<>();

        for (Pagamento pagamento : pagamentos) {
            if (pagamento.getStatus() == status) {
                pagamentosFiltrados.add(pagamento);
            }
        }

        return pagamentosFiltrados;
    }

    /**
     * Gera estatísticas de planos (quantidade de matrículas por tipo de plano).
     *
     * @param matriculas Lista de matrículas
     * @return Mapa com nome do plano e quantidade de matrículas ativas
     */
    public static Map<String, Integer> gerarEstatisticasPlanos(List<Matricula> matriculas) {
        Map<String, Integer> estatisticas = new HashMap<>();

        for (Matricula matricula : matriculas) {
            // Considera apenas matrículas ativas
            if (matricula.getStatus() == StatusMatricula.ATIVA) {
                String nomePlano = matricula.getPlano().getNome();

                // Incrementa contador do plano
                estatisticas.put(nomePlano, estatisticas.getOrDefault(nomePlano, 0) + 1);
            }
        }

        return estatisticas;
    }

    /**
     * Exporta uma lista de dados para arquivo CSV.
     *
     * @param dados Linhas de dados (cada linha é uma lista de campos)
     * @param nomeArquivo Nome do arquivo CSV a ser criado
     * @param cabecalho Cabeçalho do arquivo (nomes das colunas)
     * @return true se exportou com sucesso, false em caso de erro
     */
    public static boolean exportarCSV(
            List<List<String>> dados,
            String nomeArquivo,
            List<String> cabecalho) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            // Escreve o cabeçalho
            if (cabecalho != null && !cabecalho.isEmpty()) {
                writer.write(String.join(",", cabecalho));
                writer.newLine();
            }

            // Escreve cada linha de dados
            for (List<String> linha : dados) {
                // Trata campos que contêm vírgula (coloca entre aspas)
                List<String> camposTratados = new ArrayList<>();
                for (String campo : linha) {
                    if (campo.contains(",")) {
                        camposTratados.add("\"" + campo + "\"");
                    } else {
                        camposTratados.add(campo);
                    }
                }
                writer.write(String.join(",", camposTratados));
                writer.newLine();
            }

            return true;

        } catch (IOException e) {
            System.err.println("Erro ao exportar CSV: " + e.getMessage());
            return false;
        }
    }

    /**
     * Formata um relatório de alunos para exibição ou impressão.
     *
     * @param alunos Lista de alunos
     * @return String formatada com os dados dos alunos
     */
    public static String formatarRelatorioAlunos(List<Aluno> alunos) {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("RELATÓRIO DE ALUNOS\n");
        relatorio.append("=".repeat(80)).append("\n\n");

        if (alunos.isEmpty()) {
            relatorio.append("Nenhum aluno encontrado.\n");
        } else {
            relatorio.append(String.format("Total de alunos: %d\n\n", alunos.size()));
            relatorio.append(String.format("%-15s %-30s %-15s %-30s\n",
                "CPF", "Nome", "Telefone", "Email"));
            relatorio.append("-".repeat(80)).append("\n");

            for (Aluno aluno : alunos) {
                relatorio.append(String.format("%-15s %-30s %-15s %-30s\n",
                    aluno.getCpf(),
                    aluno.getNome(),
                    aluno.getTelefone(),
                    aluno.getEmail()));
            }
        }

        relatorio.append("\n").append("=".repeat(80)).append("\n");
        return relatorio.toString();
    }

    /**
     * Formata um relatório financeiro para exibição ou impressão.
     *
     * @param pagamentos Lista de pagamentos
     * @return String formatada com os dados financeiros
     */
    public static String formatarRelatorioFinanceiro(List<Pagamento> pagamentos) {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("RELATÓRIO FINANCEIRO\n");
        relatorio.append("=".repeat(80)).append("\n\n");

        double totalConfirmado = 0.0;
        double totalPendente = 0.0;
        double totalEstornado = 0.0;

        for (Pagamento pagamento : pagamentos) {
            switch (pagamento.getStatus()) {
                case CONFIRMADO:
                    totalConfirmado += pagamento.getValor();
                    break;
                case PENDENTE:
                    totalPendente += pagamento.getValor();
                    break;
                case ESTORNADO:
                    totalEstornado += pagamento.getValor();
                    break;
            }
        }

        relatorio.append(String.format("Total de Pagamentos: %d\n\n", pagamentos.size()));
        relatorio.append(String.format("Receitas Confirmadas: R$ %.2f\n", totalConfirmado));
        relatorio.append(String.format("Pagamentos Pendentes: R$ %.2f\n", totalPendente));
        relatorio.append(String.format("Valores Estornados:   R$ %.2f\n", totalEstornado));
        relatorio.append("\n").append("=".repeat(80)).append("\n");

        return relatorio.toString();
    }

    /**
     * Converte uma data para string no formato dd/MM/yyyy.
     *
     * @param data Data a ser formatada
     * @return String formatada ou string vazia se data for null
     */
    public static String formatarData(Date data) {
        if (data == null) {
            return "";
        }
        return FORMATO_DATA.format(data);
    }
}
