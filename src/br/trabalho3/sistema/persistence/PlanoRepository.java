package br.trabalho3.sistema.persistence;

import br.trabalho3.sistema.model.*;

/**
 * Repositório para persistência de planos em arquivo CSV.
 * *
 * Arquivo: dados/planos.csv
 * Formato: id,nome,precoBase,duracao,tipo
 *
 */
public class PlanoRepository extends CSVRepository<Plano> {

    /**
     * Nome padrão do arquivo CSV de planos.
     */
    private static final String ARQUIVO_PADRAO = "dados/planos.csv";

    /**
     * Cabeçalho do arquivo CSV.
     */
    private static final String CABECALHO = "id,nome,precoBase,duracao,tipo";

    /**
     * Construtor padrão que usa o caminho padrão do arquivo.
     */
    public PlanoRepository() {
        super(ARQUIVO_PADRAO, CABECALHO);
    }

    /**
     * Construtor que permite especificar o caminho do arquivo.
     *
     * @param caminhoArquivo Caminho customizado do arquivo
     */
    public PlanoRepository(String caminhoArquivo) {
        super(caminhoArquivo, CABECALHO);
    }

    /**
     * Converte uma linha CSV em um objeto Plano.
     *
     * Cria instâncias de PlanoComum, PlanoPremium ou PlanoEstudante
     * baseado no campo "tipo" do CSV.
     *
     * @param linhaCsv Linha no formato: id,nome,precoBase,duracao,tipo
     * @return Objeto Plano (que pode ser Comum, Premium ou Estudante)
     */
    @Override
    protected Plano fromCSV(String linhaCsv) {
        String[] campos = linhaCsv.split(",");

        if (campos.length != 5) {
            throw new IllegalArgumentException("Linha CSV inválida para Plano: " + linhaCsv);
        }

        String id = campos[0].trim();
        String nome = campos[1].trim();
        double precoBase = Double.parseDouble(campos[2].trim());
        int duracao = Integer.parseInt(campos[3].trim());
        String tipo = campos[4].trim();

        // POLIMORFISMO: Cria a instância correta baseado no tipo
        switch (tipo.toUpperCase()) {
            case "COMUM":
                return new PlanoComum(id, nome, precoBase, duracao);

            case "PREMIUM":
                return new PlanoPremium(id, nome, precoBase, duracao);

            case "ESTUDANTE":
                return new PlanoEstudante(id, nome, precoBase, duracao);

            default:
                throw new IllegalArgumentException("Tipo de plano desconhecido: " + tipo);
        }
    }

    /**
     * Converte um objeto Plano em uma linha CSV.
     *
     * O método getTipo() retorna o tipo correto (COMUM, PREMIUM ou ESTUDANTE)
     * dependendo da instância concreta do plano.
     *
     * @param plano Plano a ser convertido
     * @return String no formato CSV
     */
    @Override
    protected String toCSV(Plano plano) {
        return String.format("%s,%s,%.2f,%d,%s",
            plano.getId(),
            plano.getNome(),
            plano.getPrecoBase(),
            plano.getDuracao(),
            plano.getTipo()); // Método polimórfico: retorna COMUM, PREMIUM ou ESTUDANTE
    }

    /**
     * Retorna o identificador único do plano (ID).
     *
     * @param plano Plano
     * @return ID do plano
     */
    @Override
    protected String getId(Plano plano) {
        return plano.getId();
    }
}
