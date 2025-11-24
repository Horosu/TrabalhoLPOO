package br.trabalho3.sistema.model;

/**
 * Enum que representa os possíveis status de uma matrícula na academia.
 *
 * Enumeração (tipo especial de classe)
 *
 */
public enum StatusMatricula {
    /**
     * Matrícula ativa - aluno pode frequentar a academia
     */
    ATIVA,

    /**
     * Matrícula suspensa - aluno temporariamente impedido de frequentar
     * (pode ocorrer por inadimplência ou solicitação do aluno)
     */
    SUSPENSA,

    /**
     * Matrícula cancelada - aluno desistiu ou foi desligado
     */
    CANCELADA,

    /**
     * Matrícula vencida - período de vigência expirou
     */
    VENCIDA
}
