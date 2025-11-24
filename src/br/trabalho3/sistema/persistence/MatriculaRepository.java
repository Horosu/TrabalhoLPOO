package br.trabalho3.sistema.persistence;

import br.trabalho3.sistema.model.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Repositório para persistência de matrículas em arquivo CSV.
 * *
 * Arquivo: dados/matriculas.csv
 * Formato: id,cpfAluno,idPlano,dataInicio,dataFim,status,valorMensal
 *
 * IMPORTANTE: Este repositório precisa de referências aos repositórios
 * de Aluno e Plano para reconstruir os objetos compostos.
 *
 */
public class MatriculaRepository extends CSVRepository<Matricula> {

    /**
     * Nome padrão do arquivo CSV de matrículas.
     */
    private static final String ARQUIVO_PADRAO = "dados/matriculas.csv";

    /**
     * Cabeçalho do arquivo CSV.
     */
    private static final String CABECALHO = "id,cpfAluno,idPlano,dataInicio,dataFim,status,valorMensal";

    /**
     * Formato de data usado no CSV.
     */
    private static final SimpleDateFormat FORMATO_DATA = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Repositório de alunos (necessário para reconstruir matrículas).
     */
    private AlunoRepository alunoRepository;

    /**
     * Repositório de planos (necessário para reconstruir matrículas).
     */
    private PlanoRepository planoRepository;

    /**
     * Construtor que usa o caminho padrão e recebe os repositórios necessários.
     *
     * @param alunoRepository Repositório de alunos
     * @param planoRepository Repositório de planos
     */
    public MatriculaRepository(AlunoRepository alunoRepository, PlanoRepository planoRepository) {
        super(ARQUIVO_PADRAO, CABECALHO);
        this.alunoRepository = alunoRepository;
        this.planoRepository = planoRepository;
    }

    /**
     * Converte uma linha CSV em um objeto Matricula.
     *
     * Reconstrói a matrícula buscando o Aluno e Plano nos repositórios.
     *
     * @param linhaCsv Linha no formato: id,cpfAluno,idPlano,dataInicio,dataFim,status,valorMensal
     * @return Objeto Matricula preenchido
     */
    @Override
    protected Matricula fromCSV(String linhaCsv) {
        String[] campos = linhaCsv.split(",");

        if (campos.length != 7) {
            throw new IllegalArgumentException("Linha CSV inválida para Matricula: " + linhaCsv);
        }

        try {
            String id = campos[0].trim();
            String cpfAluno = campos[1].trim();
            String idPlano = campos[2].trim();
            Date dataInicio = FORMATO_DATA.parse(campos[3].trim());
            Date dataFim = FORMATO_DATA.parse(campos[4].trim());
            StatusMatricula status = StatusMatricula.valueOf(campos[5].trim());
            double valorMensal = Double.parseDouble(campos[6].trim());

            // Busca o aluno e o plano nos repositórios
            Aluno aluno = alunoRepository.buscarPorId(cpfAluno);
            Plano plano = planoRepository.buscarPorId(idPlano);

            if (aluno == null) {
                throw new IllegalArgumentException("Aluno com CPF " + cpfAluno + " não encontrado");
            }

            if (plano == null) {
                throw new IllegalArgumentException("Plano com ID " + idPlano + " não encontrado");
            }

            return new Matricula(id, aluno, plano, dataInicio, dataFim, status, valorMensal);

        } catch (ParseException e) {
            throw new IllegalArgumentException("Erro ao converter data na linha: " + linhaCsv, e);
        }
    }

    /**
     * Converte um objeto Matricula em uma linha CSV.
     *
     * @param matricula Matrícula a ser convertida
     * @return String no formato CSV
     */
    @Override
    protected String toCSV(Matricula matricula) {
        return String.format("%s,%s,%s,%s,%s,%s,%.2f",
            matricula.getId(),
            matricula.getAluno().getCpf(),
            matricula.getPlano().getId(),
            FORMATO_DATA.format(matricula.getDataInicio()),
            FORMATO_DATA.format(matricula.getDataFim()),
            matricula.getStatus().name(),
            matricula.getValorMensal());
    }

    /**
     * Retorna o identificador único da matrícula (ID).
     *
     * @param matricula Matrícula
     * @return ID da matrícula
     */
    @Override
    protected String getId(Matricula matricula) {
        return matricula.getId();
    }
}
