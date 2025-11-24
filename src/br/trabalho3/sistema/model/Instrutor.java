package br.trabalho3.sistema.model;

/**
 * Classe que representa um instrutor da academia.
 * *
 * Um instrutor é uma pessoa que trabalha na academia orientando
 * os alunos em seus treinos. Possui especialidade e registro CREF.
 *
 */
public class Instrutor extends Pessoa {

    /**
     * Especialidade do instrutor.
     * Exemplos: Musculação, Pilates, Yoga, CrossFit, Natação, etc.
     */
    private String especialidade;

    /**
     * Número de registro no CREF (Conselho Regional de Educação Física).
     * É obrigatório para profissionais de educação física no Brasil.
     * Formato típico: CREF 123456-G/SP
     */
    private String cref;

    /**
     * Construtor padrão (vazio).
     * Necessário para operações de persistência e reflexão.
     */
    public Instrutor() {
        super(); // Chama o construtor da classe Pessoa
    }

    /**
     * Construtor completo com todos os atributos.
     *
     * @param cpf CPF do instrutor (herdado de Pessoa)
     * @param nome Nome completo do instrutor (herdado de Pessoa)
     * @param telefone Telefone de contato (herdado de Pessoa)
     * @param email E-mail do instrutor (herdado de Pessoa)
     * @param especialidade Área de especialização (específico de Instrutor)
     * @param cref Registro no CREF (específico de Instrutor)
     */
    public Instrutor(String cpf, String nome, String telefone, String email,
                     String especialidade, String cref) {
        // Chama o construtor da superclasse (Pessoa) para inicializar
        // os atributos herdados (cpf, nome, telefone, email)
        super(cpf, nome, telefone, email);

        // Inicializa os atributos específicos da classe Instrutor
        this.especialidade = especialidade;
        this.cref = cref;
    }

    /**
     * Implementação do método abstrato getTipo() da classe Pessoa.
     *
     * POLIMORFISMO
     * Este método é declarado como abstrato na classe Pessoa e
     * DEVE ser implementado por todas as subclasses.
     * Permite identificar o tipo da pessoa em tempo de execução.
     *
     * @return String "INSTRUTOR" identificando o tipo desta pessoa
     */
    @Override
    public String getTipo() {
        return "INSTRUTOR";
    }

    // ========== GETTERS E SETTERS ==========

    /**
     * Obtém a especialidade do instrutor.
     *
     * @return Especialidade (ex: Musculação, Pilates, etc.)
     */
    public String getEspecialidade() {
        return especialidade;
    }

    /**
     * Define a especialidade do instrutor.
     *
     * @param especialidade Nova especialidade
     */
    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    /**
     * Obtém o registro CREF do instrutor.
     *
     * @return Número do CREF
     */
    public String getCref() {
        return cref;
    }

    /**
     * Define o registro CREF do instrutor.
     *
     * @param cref Novo número de CREF
     */
    public void setCref(String cref) {
        this.cref = cref;
    }

    /**
     * Retorna uma representação em string do instrutor.
     * Sobrescreve o método toString() da classe Pessoa para incluir
     * informações específicas do instrutor (especialidade e CREF).
     *
     * @return String com todos os dados do instrutor
     */
    @Override
    public String toString() {
        return String.format("Instrutor[cpf=%s, nome=%s, telefone=%s, email=%s, especialidade=%s, cref=%s]",
            getCpf(), getNome(), getTelefone(), getEmail(), especialidade, cref);
    }

    /**
     * Converte os dados do instrutor para formato CSV.
     * Útil para persistência em arquivo.
     *
     * @return String no formato CSV: cpf,nome,telefone,email,especialidade,cref
     */
    public String toCSV() {
        return String.format("%s,%s,%s,%s,%s,%s",
            getCpf(), getNome(), getTelefone(), getEmail(), especialidade, cref);
    }

    /**
     * Cria um objeto Instrutor a partir de uma linha CSV.
     * Método estático usado para ler dados do arquivo.
     *
     * @param linhaCsv Linha no formato: cpf,nome,telefone,email,especialidade,cref
     * @return Objeto Instrutor com os dados preenchidos
     */
    public static Instrutor fromCSV(String linhaCsv) {
        String[] campos = linhaCsv.split(",");

        // Verifica se tem o número correto de campos
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
}
