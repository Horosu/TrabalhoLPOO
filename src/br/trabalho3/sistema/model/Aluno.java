package br.trabalho3.sistema.model;

/**
 * Classe que representa um aluno da academia.
 * Um aluno é uma pessoa que possui uma matrícula na academia
 * e pode se inscrever em planos de treinamento.
 */
public class Aluno extends Pessoa {

    /**
     * Identificador único da matrícula do aluno.
     * Formato sugerido: MAT001, MAT002, etc.
     */
    private String matriculaId;

    /**
     * Construtor padrão (vazio).
     * Necessário para operações de persistência e reflexão.
     */
    public Aluno() {
        super(); // Chama o construtor da classe Pessoa
    }

    /**
     * Construtor completo com todos os atributos.
     *
     * @param cpf CPF do aluno (herdado de Pessoa)
     * @param nome Nome completo do aluno (herdado de Pessoa)
     * @param telefone Telefone de contato (herdado de Pessoa)
     * @param email E-mail do aluno (herdado de Pessoa)
     * @param matriculaId Identificador da matrícula (específico de Aluno)
     */
    public Aluno(String cpf, String nome, String telefone, String email, String matriculaId) {
        // Chama o construtor da superclasse (Pessoa) para inicializar
        // os atributos herdados (cpf, nome, telefone, email)
        super(cpf, nome, telefone, email);

        // Inicializa o atributo específico da classe Aluno
        this.matriculaId = matriculaId;
    }

    /**
     * Implementação do método abstrato getTipo() da classe Pessoa.
     * Permite identificar o tipo da pessoa em tempo de execução.
     *
     * @return String "ALUNO" identificando o tipo desta pessoa
     */
    @Override
    public String getTipo() {
        return "ALUNO";
    }

    // ========== GETTERS E SETTERS ==========

    /**
     * Obtém o ID da matrícula do aluno.
     *
     * @return Identificador da matrícula
     */
    public String getMatriculaId() {
        return matriculaId;
    }

    /**
     * Define o ID da matrícula do aluno.
     *
     * @param matriculaId Novo identificador de matrícula
     */
    public void setMatriculaId(String matriculaId) {
        this.matriculaId = matriculaId;
    }

    /**
     * Retorna uma representação em string do aluno.
     * Sobrescreve o método toString() da classe Pessoa para incluir
     * informações específicas do aluno (matriculaId).
     *
     * @return String com todos os dados do aluno
     */
    @Override
    public String toString() {
        return String.format("Aluno[cpf=%s, nome=%s, telefone=%s, email=%s, matriculaId=%s]",
            getCpf(), getNome(), getTelefone(), getEmail(), matriculaId);
    }

    /**
     * Converte os dados do aluno para formato CSV.
     * Útil para persistência em arquivo.
     *
     * @return String no formato CSV: cpf,nome,telefone,email,matriculaId
     */
    public String toCSV() {
        return String.format("%s,%s,%s,%s,%s",
            getCpf(), getNome(), getTelefone(), getEmail(), matriculaId);
    }

    /**
     * Cria um objeto Aluno a partir de uma linha CSV.
     * Método estático usado para ler dados do arquivo.
     *
     * @param linhaCsv Linha no formato: cpf,nome,telefone,email,matriculaId
     * @return Objeto Aluno com os dados preenchidos
     */
    public static Aluno fromCSV(String linhaCsv) {
        String[] campos = linhaCsv.split(",");

        // Verifica se tem o número correto de campos
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
}
