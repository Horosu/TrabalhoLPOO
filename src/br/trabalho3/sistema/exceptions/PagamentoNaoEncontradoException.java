package br.trabalho3.sistema.exceptions;

/**
 * Exceção customizada lançada quando um pagamento não é encontrado no sistema.
 *
 * Tratamento de Exceções Customizadas
 * Esta exceção é lançada principalmente em operações de busca,
 * como ao procurar um pagamento por ID que não existe.
 *
 */
public class PagamentoNaoEncontradoException extends Exception {

    /**
     * Construtor que recebe uma mensagem de erro personalizada.
     *
     * @param mensagem Descrição do erro (ex: "Pagamento com ID PAG001 não encontrado")
     */
    public PagamentoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor que recebe mensagem e a causa raiz da exceção.
     *
     * @param mensagem Descrição do erro
     * @param causa Exceção original que causou este erro
     */
    public PagamentoNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
