package br.trabalho3.sistema.utils;

/**
 * Classe utilitária para validação de CPF (Cadastro de Pessoa Física).
 *
 * Classe utilitária com métodos estáticos
 * Não precisa ser instanciada, todos os métodos são estáticos.
 *
 * A validação do CPF verifica apenas se possui 11 dígitos numéricos.
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
     * Valida um CPF verificando apenas se possui 11 dígitos.
     *
     * @param cpf CPF a ser validado (pode conter pontos e traços ou apenas números)
     * @return true se o CPF tem 11 dígitos, false caso contrário
     */
    public static boolean validarCPF(String cpf) {
        // Verifica se o CPF é nulo ou vazio
        if (cpf == null || cpf.trim().isEmpty()) {
            return false;
        }

        // Remove caracteres não numéricos (pontos, traços, espaços)
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se tem exatamente 11 dígitos
        return cpf.length() == 11;
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
