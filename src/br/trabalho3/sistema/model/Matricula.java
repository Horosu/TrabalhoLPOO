package br.trabalho3.sistema.model;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Classe que representa uma matrícula de aluno em um plano da academia.
 * *
 * Uma matrícula vincula um aluno a um plano específico, com período
 * de vigência definido e valor mensal calculado.
 *
 */
public class Matricula {

    /**
     * Identificador único da matrícula.
     * Formato sugerido: MAT001, MAT002, etc.
     */
    private String id;

    /**
     * Aluno associado à matrícula.
     */
    private Aluno aluno;

    /**
     * Plano contratado na matrícula.
     */
    private Plano plano;

    /**
     * Data de início da vigência da matrícula.
     */
    private Date dataInicio;

    /**
     * Data de término da vigência da matrícula.
     */
    private Date dataFim;

    /**
     * Status atual da matrícula.
     * Valores possíveis: ATIVA, SUSPENSA, CANCELADA, VENCIDA
     */
    private StatusMatricula status;

    /**
     * Valor mensal da matrícula.
     * Calculado automaticamente com base no tipo de plano (polimorfismo).
     */
    private double valorMensal;

    /**
     * Formato de data usado para conversão string/Date.
     */
    private static final SimpleDateFormat FORMATO_DATA = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Construtor padrão (vazio).
     */
    public Matricula() {
    }

    /**
     * Construtor completo.
     *
     * @param id Identificador único
     * @param aluno Aluno matriculado
     * @param plano Plano contratado
     * @param dataInicio Data de início
     * @param dataFim Data de término
     * @param status Status da matrícula
     * @param valorMensal Valor mensal
     */
    public Matricula(String id, Aluno aluno, Plano plano, Date dataInicio,
                     Date dataFim, StatusMatricula status, double valorMensal) {
        this.id = id;
        this.aluno = aluno;
        this.plano = plano;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = status;
        this.valorMensal = valorMensal;
    }

    /**
     * Verifica se a matrícula está vencida comparando a data de fim
     * com a data atual.
     *
     * @return true se a matrícula está vencida, false caso contrário
     */
    public boolean estaVencida() {
        if (dataFim == null) {
            return false;
        }
        return dataFim.before(new Date());
    }

    /**
     * Verifica se a matrícula está ativa.
     *
     * @return true se o status é ATIVA, false caso contrário
     */
    public boolean estaAtiva() {
        return status == StatusMatricula.ATIVA;
    }

    /**
     * Suspende a matrícula alterando seu status.
     */
    public void suspender() {
        this.status = StatusMatricula.SUSPENSA;
    }

    /**
     * Cancela a matrícula alterando seu status.
     */
    public void cancelar() {
        this.status = StatusMatricula.CANCELADA;
    }

    /**
     * Reativa uma matrícula suspensa.
     */
    public void reativar() {
        if (this.status == StatusMatricula.SUSPENSA) {
            this.status = StatusMatricula.ATIVA;
        }
    }

    // ========== GETTERS E SETTERS ==========

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public StatusMatricula getStatus() {
        return status;
    }

    public void setStatus(StatusMatricula status) {
        this.status = status;
    }

    public double getValorMensal() {
        return valorMensal;
    }

    public void setValorMensal(double valorMensal) {
        this.valorMensal = valorMensal;
    }

    /**
     * Converte para formato CSV.
     *
     * @return String no formato: id,cpfAluno,idPlano,dataInicio,dataFim,status,valorMensal
     */
    public String toCSV() {
        return String.format("%s,%s,%s,%s,%s,%s,%.2f",
            id,
            aluno.getCpf(),
            plano.getId(),
            FORMATO_DATA.format(dataInicio),
            FORMATO_DATA.format(dataFim),
            status.name(),
            valorMensal);
    }

    @Override
    public String toString() {
        return String.format("Matricula[id=%s, aluno=%s, plano=%s, dataInicio=%s, dataFim=%s, status=%s, valorMensal=R$ %.2f]",
            id,
            aluno.getNome(),
            plano.getNome(),
            FORMATO_DATA.format(dataInicio),
            FORMATO_DATA.format(dataFim),
            status,
            valorMensal);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Matricula)) {
            return false;
        }
        Matricula outra = (Matricula) obj;
        return this.id != null && this.id.equals(outra.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
