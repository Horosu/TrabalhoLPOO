package br.trabalho3.sistema.model;

/**
 * Classe que representa pagamento em dinheiro.
 */
public class PagamentoDinheiro extends FormaPagamento {

    /**
     * Construtor padrão.
     */
    public PagamentoDinheiro() {
    }

    /**
     * Valida o pagamento em dinheiro.
     * Para dinheiro, a validação é sempre verdadeira pois basta selecionar o tipo.
     *
     * @return true sempre
     */
    @Override
    public boolean validarPagamento() {
        return true;
    }

    /**
     * Retorna o tipo da forma de pagamento.
     *
     * @return String "DINHEIRO"
     */
    @Override
    public String getTipo() {
        return "DINHEIRO";
    }

    /**
     * Retorna os detalhes do pagamento em dinheiro.
     *
     * @return String indicando que é dinheiro
     */
    @Override
    public String getDetalhes() {
        return "Pagamento em dinheiro";
    }

    /**
     * Converte para formato CSV.
     *
     * @return String no formato: tipo
     */
    public String toCSV() {
        return getTipo();
    }

    /**
     * Cria um objeto PagamentoDinheiro a partir de uma string de detalhes.
     *
     * @param detalhes String com o tipo de pagamento
     * @return Objeto PagamentoDinheiro
     */
    public static PagamentoDinheiro fromDetalhes(String detalhes) {
        return new PagamentoDinheiro();
    }

    @Override
    public String toString() {
        return "PagamentoDinheiro";
    }
}
