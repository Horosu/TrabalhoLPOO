package br.trabalho3.sistema.model;

/**
 * Classe abstrata que representa uma pessoa genérica no sistema.
 * Define os atributos e comportamentos comuns a todas as pessoas
 * do sistema (alunos e instrutores), evitando duplicação de código.
 */
public abstract class Pessoa {

    /**
     * CPF da pessoa (Cadastro de Pessoa Física).
     * Formato: XXX.XXX.XXX-XX
     * Este é o identificador único da pessoa no sistema.
     */
    private String cpf;

    /**
     * Nome completo da pessoa.
     */
    private String nome;

    /**
     * Telefone de contato da pessoa.
     * Pode conter DDD e ser fixo ou celular.
     */
    private String telefone;

    /**
     * Endereço de e-mail da pessoa.
     * Usado para comunicação e identificação secundária.
     */
    private String email;

    /**
     * Construtor padrão (vazio).
     * Necessário para algumas operações de persistência.
     */
    public Pessoa() {
    }

    /**
     * Construtor completo com todos os atributos.
     *
     * @param cpf CPF da pessoa (identificador único)
     * @param nome Nome completo da pessoa
     * @param telefone Telefone de contato
     * @param email Endereço de e-mail
     */
    public Pessoa(String cpf, String nome, String telefone, String email) {
        this.cpf = cpf;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    /**
     * Método abstrato que retorna o tipo da pessoa.
     * Cada subclasse (Aluno, Instrutor) implementa este método
     * retornando seu tipo específico.
     *
     * @return String representando o tipo ("ALUNO" ou "INSTRUTOR")
     */
    public abstract String getTipo();

    // ========== GETTERS E SETTERS ==========

    /**
     * Obtém o CPF da pessoa.
     *
     * @return CPF da pessoa
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Define o CPF da pessoa.
     *
     * @param cpf Novo CPF
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Obtém o nome da pessoa.
     *
     * @return Nome completo
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome da pessoa.
     *
     * @param nome Novo nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém o telefone da pessoa.
     *
     * @return Telefone de contato
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Define o telefone da pessoa.
     *
     * @param telefone Novo telefone
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Obtém o e-mail da pessoa.
     *
     * @return Endereço de e-mail
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o e-mail da pessoa.
     *
     * @param email Novo e-mail
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna uma representação em string da pessoa.
     * Útil para debug e exibição em logs.
     *
     * @return String com os dados da pessoa
     */
    @Override
    public String toString() {
        return String.format("Pessoa[tipo=%s, cpf=%s, nome=%s, telefone=%s, email=%s]",
            getTipo(), cpf, nome, telefone, email);
    }

    /**
     * Verifica se duas pessoas são iguais (baseado no CPF).
     * Duas pessoas são consideradas iguais se tiverem o mesmo CPF.
     *
     * @param obj Objeto a ser comparado
     * @return true se as pessoas têm o mesmo CPF, false caso contrário
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Pessoa)) {
            return false;
        }
        Pessoa outraPessoa = (Pessoa) obj;
        return this.cpf != null && this.cpf.equals(outraPessoa.cpf);
    }

    /**
     * Retorna um código hash baseado no CPF.
     * Necessário quando sobrescrevemos equals().
     *
     * @return Código hash
     */
    @Override
    public int hashCode() {
        return cpf != null ? cpf.hashCode() : 0;
    }
}
