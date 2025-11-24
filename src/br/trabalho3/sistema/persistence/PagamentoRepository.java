package br.trabalho3.sistema.persistence;

import br.trabalho3.sistema.model.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Repositório para persistência de pagamentos em arquivo CSV.
 * *
 * Arquivo: dados/pagamentos.csv
 * Formato: id,idMatricula,formaPagamento,valor,dataPagamento,status,detalhesPagamento
 *
 * IMPORTANTE: Este repositório precisa de referência ao MatriculaRepository
 * para reconstruir os objetos compostos.
 *
 */
public class PagamentoRepository extends CSVRepository<Pagamento> {

    /**
     * Nome padrão do arquivo CSV de pagamentos.
     */
    private static final String ARQUIVO_PADRAO = "dados/pagamentos.csv";

    /**
     * Cabeçalho do arquivo CSV.
     */
    private static final String CABECALHO = "id,idMatricula,formaPagamento,valor,dataPagamento,status,detalhesPagamento";

    /**
     * Formato de data usado no CSV.
     */
    private static final SimpleDateFormat FORMATO_DATA = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Repositório de matrículas (necessário para reconstruir pagamentos).
     */
    private MatriculaRepository matriculaRepository;

    /**
     * Construtor que usa o caminho padrão e recebe o repositório necessário.
     *
     * @param matriculaRepository Repositório de matrículas
     */
    public PagamentoRepository(MatriculaRepository matriculaRepository) {
        super(ARQUIVO_PADRAO, CABECALHO);
        this.matriculaRepository = matriculaRepository;
    }

    /**
     * Converte uma linha CSV em um objeto Pagamento.
     *
     * Reconstrói a FormaPagamento correta (PIX, Cartão ou Dinheiro)
     * baseado no campo formaPagamento do CSV.
     *
     * @param linhaCsv Linha no formato: id,idMatricula,formaPagamento,valor,dataPagamento,status,detalhesPagamento
     * @return Objeto Pagamento preenchido
     */
    @Override
    protected Pagamento fromCSV(String linhaCsv) {
        String[] campos = linhaCsv.split(",", 7); // Limita a 7 para não quebrar detalhes que possam ter vírgula

        if (campos.length != 7) {
            throw new IllegalArgumentException("Linha CSV inválida para Pagamento: " + linhaCsv);
        }

        try {
            String id = campos[0].trim();
            String idMatricula = campos[1].trim();
            String tipoFormaPagamento = campos[2].trim();
            double valor = Double.parseDouble(campos[3].trim());
            Date dataPagamento = FORMATO_DATA.parse(campos[4].trim());
            StatusPagamento status = StatusPagamento.valueOf(campos[5].trim());
            String detalhesPagamento = campos[6].trim();

            // Busca a matrícula no repositório
            Matricula matricula = matriculaRepository.buscarPorId(idMatricula);

            if (matricula == null) {
                throw new IllegalArgumentException("Matrícula com ID " + idMatricula + " não encontrada");
            }

            // POLIMORFISMO: Cria a forma de pagamento correta baseado no tipo
            FormaPagamento formaPagamento = criarFormaPagamento(tipoFormaPagamento, detalhesPagamento);

            return new Pagamento(id, matricula, formaPagamento, valor, dataPagamento, status);

        } catch (ParseException e) {
            throw new IllegalArgumentException("Erro ao converter data na linha: " + linhaCsv, e);
        }
    }

    /**
     * Cria a instância correta de FormaPagamento baseado no tipo.
     *
     * Factory method que retorna PagamentoPix, PagamentoCartao ou PagamentoDinheiro.
     *
     * @param tipo Tipo da forma de pagamento (PIX, CARTAO ou DINHEIRO)
     * @param detalhes Detalhes específicos da forma de pagamento
     * @return Instância de FormaPagamento
     */
    private FormaPagamento criarFormaPagamento(String tipo, String detalhes) {
        switch (tipo.toUpperCase()) {
            case "PIX":
                return PagamentoPix.fromDetalhes(detalhes);

            case "CARTAO":
                return PagamentoCartao.fromDetalhes(detalhes);

            case "DINHEIRO":
                return PagamentoDinheiro.fromDetalhes(detalhes);

            default:
                throw new IllegalArgumentException("Tipo de forma de pagamento desconhecido: " + tipo);
        }
    }

    /**
     * Converte um objeto Pagamento em uma linha CSV.
     *
     * O método toCSV() da FormaPagamento é chamado polimorficamente,
     * retornando os detalhes corretos para cada tipo.
     *
     * @param pagamento Pagamento a ser convertido
     * @return String no formato CSV
     */
    @Override
    protected String toCSV(Pagamento pagamento) {
        return String.format("%s,%s,%s,%.2f,%s,%s,%s",
            pagamento.getId(),
            pagamento.getMatricula().getId(),
            pagamento.getFormaPagamento().getTipo(),
            pagamento.getValor(),
            FORMATO_DATA.format(pagamento.getDataPagamento()),
            pagamento.getStatus().name(),
            pagamento.getFormaPagamento().toCSV() // Método polimórfico
        );
    }

    /**
     * Retorna o identificador único do pagamento (ID).
     *
     * @param pagamento Pagamento
     * @return ID do pagamento
     */
    @Override
    protected String getId(Pagamento pagamento) {
        return pagamento.getId();
    }
}
