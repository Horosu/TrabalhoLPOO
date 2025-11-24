package br.trabalho3.sistema.exceptions;

/**
 * Exceção customizada lançada quando um usuário (aluno ou instrutor)
 * não é encontrado no sistema.
 *
 * Tratamento de Exceções Customizadas
 * Esta exceção herda de Exception, permitindo criar erros específicos
 * do domínio da aplicação (academia).
 *
 */
public class UsuarioNaoEncontradoException extends Exception {

    /**
     * Construtor que recebe uma mensagem de erro personalizada.
     *
     * @param mensagem Descrição do erro (ex: "Aluno com CPF 123.456.789-00 não encontrado")
     */
    public UsuarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor que recebe mensagem e a causa raiz da exceção.
     * Útil para encadear exceções (exception chaining).
     *
     * @param mensagem Descrição do erro
     * @param causa Exceção original que causou este erro
     */
    public UsuarioNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
