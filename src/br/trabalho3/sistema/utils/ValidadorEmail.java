package br.trabalho3.sistema.utils;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Classe utilitária para validação de endereços de e-mail.
 *
 * Não precisa ser instanciada, todos os métodos são estáticos.
 *
 * A validação é feita usando expressão regular (regex) que verifica:
 * - Parte local antes do @ (letras, números, pontos, hífens, underscores)
 * - Símbolo @ obrigatório
 * - Domínio após o @ (letras, números, hífens)
 * - Ponto e extensão (mínimo 2 caracteres)
 *
 * Exemplos válidos: usuario@exemplo.com, nome.sobrenome@empresa.com.br
 * Exemplos inválidos: @exemplo.com, usuario@, usuario.exemplo.com
 *
 */
public class ValidadorEmail {

    /**
     * Padrão regex para validação de e-mail.
     *
     * Explicação da expressão regular:
     * ^                  - Início da string
     * [A-Za-z0-9+_.-]+   - Parte local: letras, números, +, _, ., -
     * @                  - Símbolo @ obrigatório
     * [A-Za-z0-9.-]+     - Domínio: letras, números, ., -
     * \.                 - Ponto literal antes da extensão
     * [A-Za-z]{2,}       - Extensão com no mínimo 2 letras (com, br, net, etc)
     * $                  - Fim da string
     */
    private static final String EMAIL_REGEX =
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    /**
     * Pattern compilado para melhor performance.
     * Compilar uma vez e reusar é mais eficiente do que compilar a cada validação.
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * Construtor privado para evitar instanciação.
     * Esta classe contém apenas métodos estáticos.
     */
    private ValidadorEmail() {
        // Construtor privado - classe utilitária não deve ser instanciada
    }

    /**
     * Valida um endereço de e-mail usando expressão regular.
     *
     * @param email E-mail a ser validado
     * @return true se o e-mail é válido, false caso contrário
     */
    public static boolean validarEmail(String email) {
        // Verifica se o email é nulo ou vazio
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        // Remove espaços em branco nas extremidades
        email = email.trim();

        // Verifica comprimento mínimo razoável (ex: a@b.co = 6 caracteres)
        if (email.length() < 6) {
            return false;
        }

        // Verifica comprimento máximo (padrão RFC é 254 caracteres)
        if (email.length() > 254) {
            return false;
        }

        try {
            // Cria um matcher com o padrão compilado
            Matcher matcher = EMAIL_PATTERN.matcher(email);

            // Retorna true se o email corresponde ao padrão
            return matcher.matches();

        } catch (Exception e) {
            // Se ocorrer qualquer erro, considera inválido
            return false;
        }
    }

    /**
     * Normaliza um e-mail removendo espaços e convertendo para minúsculas.
     * Útil para armazenamento padronizado.
     *
     * @param email E-mail a ser normalizado
     * @return E-mail normalizado (minúsculas, sem espaços)
     */
    public static String normalizarEmail(String email) {
        if (email == null) {
            return "";
        }

        // Remove espaços e converte para minúsculas
        return email.trim().toLowerCase();
    }
}
