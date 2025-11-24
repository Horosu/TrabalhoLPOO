package br.trabalho3.sistema.model;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Classe que representa um pagamento realizado por um aluno.
 * *
 * Um pagamento está vinculado a uma matrícula e possui uma forma
 * de pagamento específica (PIX, cartão ou dinheiro).
 *
 */
public class Pagamento {

    /**
     * Identificador único do pagamento.
     * Formato sugerido: PAG001, PAG002, etc.
     */
    private String id;

    /**
     * Matrícula à qual este pagamento está vinculado.
     * COMPOSIÇÃO (Pagamento tem uma Matrícula)
     */
    private Matricula matricula;

    /**
     * Forma de pagamento utilizada.
     * POLIMORFISMO
     * Pode ser PagamentoPix, PagamentoCartao ou PagamentoDinheiro
     */
    private FormaPagamento formaPagamento;

    /**
     * Valor pago.
     */
    private double valor;

    /**
     * Data em que o pagamento foi realizado.
     */
    private Date dataPagamento;

    /**
     * Status do pagamento.
     * USO DE ENUM
     * Valores possíveis: PENDENTE, CONFIRMADO, ESTORNADO
     */
    private StatusPagamento status;

    /**
     * Formato de data usado para conversão string/Date.
     */
    private static final SimpleDateFormat FORMATO_DATA = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Construtor padrão (vazio).
     */
    public Pagamento() {
    }

    /**
     * Construtor completo.
     *
     * @param id Identificador único
     * @param matricula Matrícula associada
     * @param formaPagamento Forma de pagamento (PIX, Cartão ou Dinheiro)
     * @param valor Valor pago
     * @param dataPagamento Data do pagamento
     * @param status Status do pagamento
     */
    public Pagamento(String id, Matricula matricula, FormaPagamento formaPagamento,
                     double valor, Date dataPagamento, StatusPagamento status) {
        this.id = id;
        this.matricula = matricula;
        this.formaPagamento = formaPagamento;
        this.valor = valor;
        this.dataPagamento = dataPagamento;
        this.status = status;
    }

    /**
     * Confirma o pagamento alterando seu status para CONFIRMADO.
     * Apenas pagamentos pendentes podem ser confirmados.
     *
     * @return true se foi confirmado, false se não estava pendente
     */
    public boolean confirmar() {
        if (this.status == StatusPagamento.PENDENTE) {
            this.status = StatusPagamento.CONFIRMADO;
            return true;
        }
        return false;
    }

    /**
     * Estorna o pagamento alterando seu status para ESTORNADO.
     * Apenas pagamentos confirmados podem ser estornados.
     *
     * @return true se foi estornado, false se não estava confirmado
     */
    public boolean estornar() {
        if (this.status == StatusPagamento.CONFIRMADO) {
            this.status = StatusPagamento.ESTORNADO;
            return true;
        }
        return false;
    }

    /**
     * Verifica se o pagamento está confirmado.
     *
     * @return true se o status é CONFIRMADO, false caso contrário
     */
    public boolean estaConfirmado() {
        return status == StatusPagamento.CONFIRMADO;
    }

    /**
     * Verifica se o pagamento está pendente.
     *
     * @return true se o status é PENDENTE, false caso contrário
     */
    public boolean estaPendente() {
        return status == StatusPagamento.PENDENTE;
    }

    /**
     * Valida os dados do pagamento usando o método polimórfico
     * da forma de pagamento.
     *
     * POLIMORFISMO
     * O método validarPagamento() é implementado de forma diferente
     * por cada tipo de forma de pagamento.
     *
     * @return true se o pagamento é válido, false caso contrário
     */
    public boolean validar() {
        // Valida valor
        if (valor <= 0) {
            return false;
        }

        // Valida forma de pagamento (polimorfismo em ação!)
        if (formaPagamento == null || !formaPagamento.validarPagamento()) {
            return false;
        }

        // Valida matrícula
        if (matricula == null) {
            return false;
        }

        return true;
    }

    // ========== GETTERS E SETTERS ==========

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }

    /**
     * Converte para formato CSV.
     *
     * @return String no formato: id,idMatricula,formaPagamento,valor,dataPagamento,status,detalhes
     */
    public String toCSV() {
        return String.format("%s,%s,%s,%.2f,%s,%s,%s",
            id,
            matricula.getId(),
            formaPagamento.getTipo(),
            valor,
            FORMATO_DATA.format(dataPagamento),
            status.name(),
            formaPagamento.toCSV() // Detalhes da forma de pagamento
        );
    }

    @Override
    public String toString() {
        return String.format("Pagamento[id=%s, matricula=%s, formaPagamento=%s, valor=R$ %.2f, data=%s, status=%s]",
            id,
            matricula.getId(),
            formaPagamento.getTipo(),
            valor,
            FORMATO_DATA.format(dataPagamento),
            status);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Pagamento)) {
            return false;
        }
        Pagamento outro = (Pagamento) obj;
        return this.id != null && this.id.equals(outro.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
