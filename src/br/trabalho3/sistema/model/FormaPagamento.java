package br.trabalho3.sistema.model;

/**
 * Classe abstrata que representa uma forma de pagamento.
 * *
 * Cada forma de pagamento tem suas próprias regras de validação:
 * - PIX: valida chave PIX
 * - Cartão: valida número, CVV e bandeira
 * - Dinheiro: valida valor recebido e troco
 *
 */
public abstract class FormaPagamento {

    /**
     * Método abstrato que valida os dados da forma de pagamento.
     *
     * POLIMORFISMO
     * Cada tipo de pagamento implementa sua própria lógica de validação:
     * - PagamentoPix: verifica se a chave PIX está preenchida
     * - PagamentoCartao: verifica número do cartão, CVV e bandeira
     * - PagamentoDinheiro: verifica se o valor recebido é suficiente
     *
     * @return true se os dados são válidos, false caso contrário
     */
    public abstract boolean validarPagamento();

    /**
     * Método abstrato que retorna o tipo da forma de pagamento.
     *
     * @return String representando o tipo ("PIX", "CARTAO" ou "DINHEIRO")
     */
    public abstract String getTipo();

    /**
     * Método abstrato que retorna os detalhes do pagamento em formato string.
     * Usado para exibição e persistência.
     *
     * @return String com os detalhes específicos da forma de pagamento
     */
    public abstract String getDetalhes();

    /**
     * Método abstrato que converte a forma de pagamento para formato CSV.
     * Usado para persistência em arquivo.
     *
     * @return String no formato CSV com os dados da forma de pagamento
     */
    public abstract String toCSV();

    /**
     * Retorna uma representação em string da forma de pagamento.
     *
     * @return String com tipo e detalhes do pagamento
     */
    @Override
    public String toString() {
        return String.format("FormaPagamento[tipo=%s, detalhes=%s]",
            getTipo(), getDetalhes());
    }
}
