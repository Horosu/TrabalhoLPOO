package br.trabalho3.sistema.exceptions;

/**
 * Exceção customizada lançada quando há tentativa de criar ou modificar
 * uma matrícula com dados inválidos.
 *
 * Tratamento de Exceções Customizadas
 * Exemplos de situações que lançam esta exceção:
 * - Aluno já possui matrícula ativa
 * - Data de início posterior à data de fim
 * - Plano não disponível
 * - Aluno inexistente
 *
 */
public class MatriculaInvalidaException extends Exception {

    /**
     * Construtor que recebe uma mensagem de erro personalizada.
     *
     * @param mensagem Descrição do erro (ex: "Aluno já possui matrícula ativa")
     */
    public MatriculaInvalidaException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor que recebe mensagem e a causa raiz da exceção.
     *
     * @param mensagem Descrição do erro
     * @param causa Exceção original que causou este erro
     */
    public MatriculaInvalidaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
