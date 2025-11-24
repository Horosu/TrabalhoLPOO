package br.trabalho3.sistema.model;

/**
 * Classe que representa um plano comum de academia.
 * *
 * O Plano Comum é o plano básico da academia, sem acréscimos ou descontos.
 * O preço final é igual ao preço base.
 *
 */
public class PlanoComum extends Plano {

    /**
     * Construtor padrão (vazio).
     */
    public PlanoComum() {
        super();
    }

    /**
     * Construtor completo.
     *
     * @param id Identificador único do plano
     * @param nome Nome do plano (ex: "Mensal Comum")
     * @param precoBase Preço base do plano
     * @param duracao Duração em dias
     */
    public PlanoComum(String id, String nome, double precoBase, int duracao) {
        super(id, nome, precoBase, duracao);
    }

    /**
     * Calcula o preço final do plano comum.
     *
     * POLIMORFISMO
     * Implementa o método abstrato da classe Plano.
     * Para o plano comum, o preço final é igual ao preço base,
     * ou seja, não há acréscimo nem desconto.
     *
     * Fórmula: PreçoFinal = PreçoBase
     *
     * @return Preço final (igual ao preço base)
     */
    @Override
    public double calcularPrecoFinal() {
        return getPrecoBase();
    }

    /**
     * Retorna o tipo do plano.
     *
     * @return String "COMUM"
     */
    @Override
    public String getTipo() {
        return "COMUM";
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
     * Cria um objeto PlanoComum a partir de uma linha CSV.
     *
     * @param linhaCsv Linha no formato: id,nome,precoBase,duracao,tipo
     * @return Objeto PlanoComum com os dados preenchidos
     */
    public static PlanoComum fromCSV(String linhaCsv) {
        String[] campos = linhaCsv.split(",");

        if (campos.length != 5) {
            throw new IllegalArgumentException("Linha CSV inválida para PlanoComum: " + linhaCsv);
        }

        return new PlanoComum(
            campos[0].trim(),                    // id
            campos[1].trim(),                    // nome
            Double.parseDouble(campos[2].trim()), // precoBase
            Integer.parseInt(campos[3].trim())    // duracao
            // campos[4] é o tipo, que já sabemos que é COMUM
        );
    }
}
