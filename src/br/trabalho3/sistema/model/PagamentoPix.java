package br.trabalho3.sistema.model;

/**
 * Classe que representa pagamento via PIX.
 * PIX é um sistema de pagamento instantâneo brasileiro.
 */
public class PagamentoPix extends FormaPagamento {

    /**
     * Construtor padrão.
     */
    public PagamentoPix() {
    }

    /**
     * Valida o pagamento PIX.
     * Para PIX, a validação é sempre verdadeira pois basta selecionar o tipo.
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
     * @return String "PIX"
     */
    @Override
    public String getTipo() {
        return "PIX";
    }

    /**
     * Retorna os detalhes do pagamento PIX.
     *
     * @return String indicando que é PIX
     */
    @Override
    public String getDetalhes() {
        return "Pagamento via PIX";
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
     * Cria um objeto PagamentoPix a partir de uma string de detalhes.
     *
     * @param detalhes String com o tipo de pagamento
     * @return Objeto PagamentoPix
     */
    public static PagamentoPix fromDetalhes(String detalhes) {
        return new PagamentoPix();
    }

    @Override
    public String toString() {
        return "PagamentoPix";
    }
}
