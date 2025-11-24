package br.trabalho3.sistema.exceptions;

/**
 * Exceção customizada lançada quando dados fornecidos pelo usuário
 * não passam nas validações.
 *
 * Tratamento de Exceções Customizadas
 * Exemplos de situações que lançam esta exceção:
 * - CPF inválido (não passa no algoritmo de validação)
 * - Email em formato incorreto
 * - Campos obrigatórios vazios
 * - Valores numéricos negativos onde deveriam ser positivos
 *
 */
public class DadosInvalidosException extends Exception {

    /**
     * Construtor que recebe uma mensagem de erro personalizada.
     *
     * @param mensagem Descrição do erro (ex: "CPF inválido", "Email em formato incorreto")
     */
    public DadosInvalidosException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor que recebe mensagem e a causa raiz da exceção.
     *
     * @param mensagem Descrição do erro
     * @param causa Exceção original que causou este erro
     */
    public DadosInvalidosException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
