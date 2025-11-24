package br.trabalho3.sistema.utils;

/**
 * Classe utilitária para validação de CPF (Cadastro de Pessoa Física).
 *
 * Classe utilitária com métodos estáticos
 * Não precisa ser instanciada, todos os métodos são estáticos.
 *
 * O algoritmo de validação do CPF:
 * 1. Remove caracteres não numéricos (pontos, traços)
 * 2. Verifica se tem 11 dígitos
 * 3. Rejeita CPFs com todos os dígitos iguais (ex: 111.111.111-11)
 * 4. Calcula o primeiro dígito verificador
 * 5. Calcula o segundo dígito verificador
 * 6. Compara os dígitos calculados com os informados
 *
 */
public class ValidadorCPF {

    /**
     * Construtor privado para evitar instanciação.
     * Esta classe contém apenas métodos estáticos.
     */
    private ValidadorCPF() {
        // Construtor privado - classe utilitária não deve ser instanciada
    }

    /**
     * Valida um CPF usando o algoritmo oficial de validação.
     *
     * @param cpf CPF a ser validado (pode conter pontos e traços ou apenas números)
     * @return true se o CPF é válido, false caso contrário
     */
    public static boolean validarCPF(String cpf) {
        // Verifica se o CPF é nulo ou vazio
        if (cpf == null || cpf.trim().isEmpty()) {
            return false;
        }

        // Remove caracteres não numéricos (pontos, traços, espaços)
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se tem exatamente 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Rejeita CPFs conhecidos como inválidos (todos os dígitos iguais)
        // Ex: 000.000.000-00, 111.111.111-11, ..., 999.999.999-99
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            // Calcula o primeiro dígito verificador
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                // Multiplica cada dígito por (10 - posição)
                // Posição 0: multiplica por 10
                // Posição 1: multiplica por 9
                // ... até posição 8: multiplica por 2
                soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int primeiroDigitoVerificador = 11 - (soma % 11);
            // Se o resultado for 10 ou 11, o dígito verificador é 0
            if (primeiroDigitoVerificador >= 10) {
                primeiroDigitoVerificador = 0;
            }

            // Verifica se o primeiro dígito calculado confere com o informado
            if (primeiroDigitoVerificador != Character.getNumericValue(cpf.charAt(9))) {
                return false;
            }

            // Calcula o segundo dígito verificador
            soma = 0;
            for (int i = 0; i < 10; i++) {
                // Agora multiplica por (11 - posição)
                soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            int segundoDigitoVerificador = 11 - (soma % 11);
            if (segundoDigitoVerificador >= 10) {
                segundoDigitoVerificador = 0;
            }

            // Verifica se o segundo dígito calculado confere com o informado
            return segundoDigitoVerificador == Character.getNumericValue(cpf.charAt(10));

        } catch (Exception e) {
            // Se ocorrer qualquer erro durante o processamento, considera inválido
            return false;
        }
    }

    /**
     * Formata um CPF para o padrão visual: XXX.XXX.XXX-XX
     *
     * @param cpf CPF sem formatação (apenas números)
     * @return CPF formatado ou o valor original se inválido
     */
    public static String formatarCPF(String cpf) {
        if (cpf == null) {
            return "";
        }

        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se tem 11 dígitos
        if (cpf.length() != 11) {
            return cpf; // Retorna sem formatação se não tiver 11 dígitos
        }

        // Formata: XXX.XXX.XXX-XX
        return cpf.substring(0, 3) + "." +
               cpf.substring(3, 6) + "." +
               cpf.substring(6, 9) + "-" +
               cpf.substring(9, 11);
    }
}
