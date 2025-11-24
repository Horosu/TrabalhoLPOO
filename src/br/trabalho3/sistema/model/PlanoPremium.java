package br.trabalho3.sistema.model;

/**
 * Classe que representa um plano premium de academia.
 * *
 * O Plano Premium oferece benefícios adicionais (acesso a áreas especiais,
 * horários estendidos, etc.) e custa 50% a mais que o preço base.
 *
 */
public class PlanoPremium extends Plano {

    /**
     * Multiplicador aplicado ao preço base para planos premium.
     * Valor: 1.5 (representa 50% de acréscimo)
     */
    private static final double MULTIPLICADOR_PREMIUM = 1.5;

    /**
     * Construtor padrão (vazio).
     */
    public PlanoPremium() {
        super();
    }

    /**
     * Construtor completo.
     *
     * @param id Identificador único do plano
     * @param nome Nome do plano (ex: "Mensal Premium")
     * @param precoBase Preço base do plano (antes do acréscimo)
     * @param duracao Duração em dias
     */
    public PlanoPremium(String id, String nome, double precoBase, int duracao) {
        super(id, nome, precoBase, duracao);
    }

    /**
     * Calcula o preço final do plano premium.
     *
     * POLIMORFISMO
     * Implementa o método abstrato da classe Plano.
     * Para o plano premium, o preço final é o preço base
     * multiplicado por 1.5 (50% mais caro).
     *
     * Fórmula: PreçoFinal = PreçoBase × 1.5
     *
     * Exemplo: Se preço base = R$ 100,00
     *         Preço final = R$ 100,00 × 1.5 = R$ 150,00
     *
     * @return Preço final (preço base × 1.5)
     */
    @Override
    public double calcularPrecoFinal() {
        return getPrecoBase() * MULTIPLICADOR_PREMIUM;
    }

    /**
     * Retorna o tipo do plano.
     *
     * @return String "PREMIUM"
     */
    @Override
    public String getTipo() {
        return "PREMIUM";
    }

    /**
     * Obtém o multiplicador usado para calcular o preço final.
     * Método útil para exibir informações sobre o plano.
     *
     * @return Valor do multiplicador (1.5)
     */
    public static double getMultiplicador() {
        return MULTIPLICADOR_PREMIUM;
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
     * Cria um objeto PlanoPremium a partir de uma linha CSV.
     *
     * @param linhaCsv Linha no formato: id,nome,precoBase,duracao,tipo
     * @return Objeto PlanoPremium com os dados preenchidos
     */
    public static PlanoPremium fromCSV(String linhaCsv) {
        String[] campos = linhaCsv.split(",");

        if (campos.length != 5) {
            throw new IllegalArgumentException("Linha CSV inválida para PlanoPremium: " + linhaCsv);
        }

        return new PlanoPremium(
            campos[0].trim(),                    // id
            campos[1].trim(),                    // nome
            Double.parseDouble(campos[2].trim()), // precoBase
            Integer.parseInt(campos[3].trim())    // duracao
            // campos[4] é o tipo, que já sabemos que é PREMIUM
        );
    }
}
