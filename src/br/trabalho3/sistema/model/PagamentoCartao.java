package br.trabalho3.sistema.model;

/**
 * Classe que representa pagamento via cartão de crédito ou débito.
 */
public class PagamentoCartao extends FormaPagamento {

    /**
     * Tipo do cartão: CREDITO ou DEBITO.
     */
    private String tipoCartao;

    /**
     * Construtor padrão.
     */
    public PagamentoCartao() {
    }

    /**
     * Construtor com tipo de cartão.
     *
     * @param tipoCartao Tipo do cartão (CREDITO ou DEBITO)
     */
    public PagamentoCartao(String tipoCartao) {
        this.tipoCartao = tipoCartao;
    }

    /**
     * Valida o pagamento com cartão.
     * Verifica se o tipo de cartão foi selecionado.
     *
     * @return true se o tipo foi selecionado, false caso contrário
     */
    @Override
    public boolean validarPagamento() {
        return tipoCartao != null && !tipoCartao.trim().isEmpty();
    }

    /**
     * Retorna o tipo da forma de pagamento.
     *
     * @return String "CARTAO"
     */
    @Override
    public String getTipo() {
        return "CARTAO";
    }

    /**
     * Retorna os detalhes do pagamento com cartão.
     *
     * @return String indicando se é crédito ou débito
     */
    @Override
    public String getDetalhes() {
        return "Cartão de " + (tipoCartao != null ? tipoCartao : "");
    }

    /**
     * Obtém o tipo do cartão.
     *
     * @return Tipo do cartão (CREDITO ou DEBITO)
     */
    public String getTipoCartao() {
        return tipoCartao;
    }

    /**
     * Define o tipo do cartão.
     *
     * @param tipoCartao Novo tipo (CREDITO ou DEBITO)
     */
    public void setTipoCartao(String tipoCartao) {
        this.tipoCartao = tipoCartao;
    }

    /**
     * Converte para formato CSV.
     *
     * @return String no formato: tipo:tipoCartao
     */
    public String toCSV() {
        return String.format("%s:%s", getTipo(), tipoCartao);
    }

    /**
     * Cria um objeto PagamentoCartao a partir de uma string de detalhes.
     *
     * @param detalhes String no formato "tipo:tipoCartao"
     * @return Objeto PagamentoCartao
     */
    public static PagamentoCartao fromDetalhes(String detalhes) {
        String[] partes = detalhes.split(":", 2);
        if (partes.length == 2) {
            return new PagamentoCartao(partes[1].trim());
        }
        return new PagamentoCartao("");
    }

    @Override
    public String toString() {
        return String.format("PagamentoCartao[tipo=%s]", tipoCartao);
    }
}
