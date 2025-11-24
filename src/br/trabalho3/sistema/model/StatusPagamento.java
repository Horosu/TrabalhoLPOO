package br.trabalho3.sistema.model;

/**
 * Enum que representa os possíveis status de um pagamento.
 *
 * Enumeração (tipo especial de classe)
 *
 */
public enum StatusPagamento {
    /**
     * Pagamento aguardando processamento ou confirmação
     */
    PENDENTE,

    /**
     * Pagamento confirmado e processado com sucesso
     */
    CONFIRMADO,

    /**
     * Pagamento estornado (devolvido ao cliente)
     * Pode ocorrer por erro, cancelamento de matrícula, etc.
     */
    ESTORNADO
}
