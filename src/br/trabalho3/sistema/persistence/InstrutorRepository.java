package br.trabalho3.sistema.persistence;

import br.trabalho3.sistema.model.Instrutor;

/**
 * Repositório para persistência de instrutores em arquivo CSV.
 * *
 * Arquivo: dados/instrutores.csv
 * Formato: cpf,nome,telefone,email,especialidade,cref
 *
 */
public class InstrutorRepository extends CSVRepository<Instrutor> {

    /**
     * Nome padrão do arquivo CSV de instrutores.
     */
    private static final String ARQUIVO_PADRAO = "dados/instrutores.csv";

    /**
     * Cabeçalho do arquivo CSV.
     */
    private static final String CABECALHO = "cpf,nome,telefone,email,especialidade,cref";

    /**
     * Construtor padrão que usa o caminho padrão do arquivo.
     */
    public InstrutorRepository() {
        super(ARQUIVO_PADRAO, CABECALHO);
    }

    /**
     * Construtor que permite especificar o caminho do arquivo.
     *
     * @param caminhoArquivo Caminho customizado do arquivo
     */
    public InstrutorRepository(String caminhoArquivo) {
        super(caminhoArquivo, CABECALHO);
    }

    /**
     * Converte uma linha CSV em um objeto Instrutor.
     *
     * Implementa o método abstrato da classe base.
     *
     * @param linhaCsv Linha no formato: cpf,nome,telefone,email,especialidade,cref
     * @return Objeto Instrutor preenchido
     */
    @Override
    protected Instrutor fromCSV(String linhaCsv) {
        String[] campos = linhaCsv.split(",");

        if (campos.length != 6) {
            throw new IllegalArgumentException("Linha CSV inválida para Instrutor: " + linhaCsv);
        }

        return new Instrutor(
            campos[0].trim(), // cpf
            campos[1].trim(), // nome
            campos[2].trim(), // telefone
            campos[3].trim(), // email
            campos[4].trim(), // especialidade
            campos[5].trim()  // cref
        );
    }

    /**
     * Converte um objeto Instrutor em uma linha CSV.
     *
     * Implementa o método abstrato da classe base.
     *
     * @param instrutor Instrutor a ser convertido
     * @return String no formato CSV
     */
    @Override
    protected String toCSV(Instrutor instrutor) {
        return String.format("%s,%s,%s,%s,%s,%s",
            instrutor.getCpf(),
            instrutor.getNome(),
            instrutor.getTelefone(),
            instrutor.getEmail(),
            instrutor.getEspecialidade(),
            instrutor.getCref());
    }

    /**
     * Retorna o identificador único do instrutor (CPF).
     *
     * @param instrutor Instrutor
     * @return CPF do instrutor
     */
    @Override
    protected String getId(Instrutor instrutor) {
        return instrutor.getCpf();
    }

    /**
     * Busca um instrutor pelo CPF.
     * Método conveniente que usa buscarPorId().
     *
     * @param cpf CPF do instrutor
     * @return Instrutor encontrado ou null
     */
    public Instrutor buscarPorCpf(String cpf) {
        return buscarPorId(cpf);
    }
}
