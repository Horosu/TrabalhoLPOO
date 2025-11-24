package br.trabalho3.sistema.persistence;

import br.trabalho3.sistema.model.Aluno;

/**
 * Repositório para persistência de alunos em arquivo CSV.
 * *
 * Arquivo: dados/alunos.csv
 * Formato: cpf,nome,telefone,email,matriculaId
 *
 */
public class AlunoRepository extends CSVRepository<Aluno> {

    /**
     * Nome padrão do arquivo CSV de alunos.
     */
    private static final String ARQUIVO_PADRAO = "dados/alunos.csv";

    /**
     * Cabeçalho do arquivo CSV.
     */
    private static final String CABECALHO = "cpf,nome,telefone,email,matriculaId";

    /**
     * Construtor padrão que usa o caminho padrão do arquivo.
     */
    public AlunoRepository() {
        super(ARQUIVO_PADRAO, CABECALHO);
    }

    /**
     * Construtor que permite especificar o caminho do arquivo.
     *
     * @param caminhoArquivo Caminho customizado do arquivo
     */
    public AlunoRepository(String caminhoArquivo) {
        super(caminhoArquivo, CABECALHO);
    }

    /**
     * Converte uma linha CSV em um objeto Aluno.
     *
     * Implementa o método abstrato da classe base.
     *
     * @param linhaCsv Linha no formato: cpf,nome,telefone,email,matriculaId
     * @return Objeto Aluno preenchido
     */
    @Override
    protected Aluno fromCSV(String linhaCsv) {
        String[] campos = linhaCsv.split(",");

        if (campos.length != 5) {
            throw new IllegalArgumentException("Linha CSV inválida para Aluno: " + linhaCsv);
        }

        return new Aluno(
            campos[0].trim(), // cpf
            campos[1].trim(), // nome
            campos[2].trim(), // telefone
            campos[3].trim(), // email
            campos[4].trim()  // matriculaId
        );
    }

    /**
     * Converte um objeto Aluno em uma linha CSV.
     *
     * Implementa o método abstrato da classe base.
     *
     * @param aluno Aluno a ser convertido
     * @return String no formato CSV
     */
    @Override
    protected String toCSV(Aluno aluno) {
        return String.format("%s,%s,%s,%s,%s",
            aluno.getCpf(),
            aluno.getNome(),
            aluno.getTelefone(),
            aluno.getEmail(),
            aluno.getMatriculaId());
    }

    /**
     * Retorna o identificador único do aluno (CPF).
     *
     * @param aluno Aluno
     * @return CPF do aluno
     */
    @Override
    protected String getId(Aluno aluno) {
        return aluno.getCpf();
    }

    /**
     * Busca um aluno pelo CPF.
     * Método conveniente que usa buscarPorId().
     *
     * @param cpf CPF do aluno
     * @return Aluno encontrado ou null
     */
    public Aluno buscarPorCpf(String cpf) {
        return buscarPorId(cpf);
    }
}
