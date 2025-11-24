package br.trabalho3.sistema.model;

/**
 * Classe abstrata que representa um plano de academia.
 * *
 * Esta classe define os atributos e comportamentos comuns a todos os planos,
 * mas cada tipo de plano calcula seu preço final de forma diferente.
 *
 */
public abstract class Plano {

    /**
     * Identificador único do plano.
     * Formato sugerido: P001, P002, etc.
     */
    private String id;

    /**
     * Nome descritivo do plano.
     * Exemplos: "Mensal Comum", "Trimestral Premium", etc.
     */
    private String nome;

    /**
     * Preço base do plano (antes de aplicar multiplicadores).
     * Este valor é usado como base para calcular o preço final.
     */
    private double precoBase;

    /**
     * Duração do plano em dias.
     * Exemplos: 30 (mensal), 90 (trimestral), 365 (anual)
     */
    private int duracao;

    /**
     * Construtor padrão (vazio).
     * Necessário para operações de persistência.
     */
    public Plano() {
    }

    /**
     * Construtor completo com todos os atributos.
     *
     * @param id Identificador único do plano
     * @param nome Nome descritivo do plano
     * @param precoBase Preço base (antes de multiplicadores)
     * @param duracao Duração em dias
     */
    public Plano(String id, String nome, double precoBase, int duracao) {
        this.id = id;
        this.nome = nome;
        this.precoBase = precoBase;
        this.duracao = duracao;
    }

    /**
     * Método abstrato que calcula o preço final do plano.
     *
     * POLIMORFISMO
     * Cada tipo de plano implementa este método de forma diferente:
     * - PlanoComum: retorna precoBase (sem alteração)
     * - PlanoPremium: retorna precoBase * 1.5 (50% mais caro)
     * - PlanoEstudante: retorna precoBase * 0.7 (30% de desconto)
     *
     * Este é um exemplo clássico de polimorfismo: o mesmo método
     * tem comportamentos diferentes dependendo da subclasse.
     *
     * @return Preço final calculado conforme o tipo de plano
     */
    public abstract double calcularPrecoFinal();

    /**
     * Retorna o tipo do plano.
     * Este método também pode ser abstrato, sendo implementado
     * por cada subclasse para retornar seu tipo específico.
     *
     * @return String representando o tipo do plano
     */
    public abstract String getTipo();

    // ========== GETTERS E SETTERS ==========

    /**
     * Obtém o ID do plano.
     *
     * @return Identificador do plano
     */
    public String getId() {
        return id;
    }

    /**
     * Define o ID do plano.
     *
     * @param id Novo identificador
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtém o nome do plano.
     *
     * @return Nome descritivo
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do plano.
     *
     * @param nome Novo nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém o preço base do plano.
     *
     * @return Preço base (antes de multiplicadores)
     */
    public double getPrecoBase() {
        return precoBase;
    }

    /**
     * Define o preço base do plano.
     *
     * @param precoBase Novo preço base
     */
    public void setPrecoBase(double precoBase) {
        this.precoBase = precoBase;
    }

    /**
     * Obtém a duração do plano em dias.
     *
     * @return Duração em dias
     */
    public int getDuracao() {
        return duracao;
    }

    /**
     * Define a duração do plano.
     *
     * @param duracao Nova duração em dias
     */
    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    /**
     * Retorna uma representação em string do plano.
     * Útil para debug e exibição em logs.
     *
     * @return String com os dados do plano
     */
    @Override
    public String toString() {
        return String.format("Plano[tipo=%s, id=%s, nome=%s, precoBase=R$%.2f, precoFinal=R$%.2f, duracao=%d dias]",
            getTipo(), id, nome, precoBase, calcularPrecoFinal(), duracao);
    }

    /**
     * Verifica se dois planos são iguais (baseado no ID).
     *
     * @param obj Objeto a ser comparado
     * @return true se os planos têm o mesmo ID, false caso contrário
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Plano)) {
            return false;
        }
        Plano outroPlano = (Plano) obj;
        return this.id != null && this.id.equals(outroPlano.id);
    }

    /**
     * Retorna um código hash baseado no ID.
     *
     * @return Código hash
     */
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
