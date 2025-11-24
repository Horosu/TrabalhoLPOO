package br.trabalho3.sistema.model;

/**
 * Classe que representa um plano estudante de academia.
 * *
 * O Plano Estudante oferece 30% de desconto para estudantes com
 * comprovação de matrícula em instituição de ensino.
 *
 */
public class PlanoEstudante extends Plano {

    /**
     * Multiplicador aplicado ao preço base para planos estudante.
     * Valor: 0.7 (representa 30% de desconto, ou seja, paga-se 70% do valor)
     */
    private static final double MULTIPLICADOR_ESTUDANTE = 0.7;

    /**
     * Percentual de desconto oferecido.
     * Valor: 30 (representa 30% de desconto)
     */
    private static final double DESCONTO_PERCENTUAL = 30.0;

    /**
     * Construtor padrão (vazio).
     */
    public PlanoEstudante() {
        super();
    }

    /**
     * Construtor completo.
     *
     * @param id Identificador único do plano
     * @param nome Nome do plano (ex: "Mensal Estudante")
     * @param precoBase Preço base do plano (antes do desconto)
     * @param duracao Duração em dias
     */
    public PlanoEstudante(String id, String nome, double precoBase, int duracao) {
        super(id, nome, precoBase, duracao);
    }

    /**
     * Calcula o preço final do plano estudante.
     *
     * POLIMORFISMO
     * Implementa o método abstrato da classe Plano.
     * Para o plano estudante, o preço final é o preço base
     * multiplicado por 0.7 (30% de desconto).
     *
     * Fórmula: PreçoFinal = PreçoBase × 0.7
     *
     * Exemplo: Se preço base = R$ 100,00
     *         Preço final = R$ 100,00 × 0.7 = R$ 70,00
     *         (Economizou R$ 30,00 - 30% de desconto)
     *
     * @return Preço final (preço base × 0.7)
     */
    @Override
    public double calcularPrecoFinal() {
        return getPrecoBase() * MULTIPLICADOR_ESTUDANTE;
    }

    /**
     * Retorna o tipo do plano.
     *
     * @return String "ESTUDANTE"
     */
    @Override
    public String getTipo() {
        return "ESTUDANTE";
    }

    /**
     * Calcula o valor economizado com o desconto.
     *
     * @return Valor do desconto em reais
     */
    public double calcularValorDesconto() {
        return getPrecoBase() - calcularPrecoFinal();
    }

    /**
     * Obtém o percentual de desconto oferecido.
     *
     * @return Percentual de desconto (30.0)
     */
    public static double getDescontoPercentual() {
        return DESCONTO_PERCENTUAL;
    }

    /**
     * Obtém o multiplicador usado para calcular o preço final.
     *
     * @return Valor do multiplicador (0.7)
     */
    public static double getMultiplicador() {
        return MULTIPLICADOR_ESTUDANTE;
    }

    /**
     * Converte os dados do plano para formato CSV.
     *
     * @return String no formato: id,nome,precoBase,duracao,tipo
     */
    public String toCSV() {
        return String.format("%s,%s,%.2f,%d,%s",
            getId(), getNome(), getPrecoBase(), getDuracao(), getTipo());
    }

    /**
     * Cria um objeto PlanoEstudante a partir de uma linha CSV.
     *
     * @param linhaCsv Linha no formato: id,nome,precoBase,duracao,tipo
     * @return Objeto PlanoEstudante com os dados preenchidos
     */
    public static PlanoEstudante fromCSV(String linhaCsv) {
        String[] campos = linhaCsv.split(",");

        if (campos.length != 5) {
            throw new IllegalArgumentException("Linha CSV inválida para PlanoEstudante: " + linhaCsv);
        }

        return new PlanoEstudante(
            campos[0].trim(),                    // id
            campos[1].trim(),                    // nome
            Double.parseDouble(campos[2].trim()), // precoBase
            Integer.parseInt(campos[3].trim())    // duracao
            // campos[4] é o tipo, que já sabemos que é ESTUDANTE
        );
    }
}
