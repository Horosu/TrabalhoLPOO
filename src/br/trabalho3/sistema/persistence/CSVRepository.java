package br.trabalho3.sistema.persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstrata base para repositórios que fazem persistência em arquivos CSV.
 * *
 * Esta classe fornece a infraestrutura básica para ler e escrever arquivos CSV,
 * permitindo que cada repositório específico implemente apenas a lógica
 * de conversão entre objetos e strings CSV.
 *
 * @param <T> Tipo da entidade que este repositório gerencia
 */
public abstract class CSVRepository<T> {

    /**
     * Caminho do arquivo CSV que este repositório gerencia.
     * Cada subclasse define seu próprio arquivo.
     */
    protected String caminhoArquivo;

    /**
     * Cabeçalho do arquivo CSV (nomes das colunas).
     * Definido por cada subclasse.
     */
    protected String cabecalho;

    /**
     * Construtor que recebe o caminho do arquivo.
     *
     * @param caminhoArquivo Caminho completo do arquivo CSV
     * @param cabecalho Linha de cabeçalho com nomes das colunas
     */
    public CSVRepository(String caminhoArquivo, String cabecalho) {
        this.caminhoArquivo = caminhoArquivo;
        this.cabecalho = cabecalho;

        // Cria o arquivo se não existir
        criarArquivoSeNaoExistir();
    }

    /**
     * Cria o arquivo CSV se ele não existir, incluindo o cabeçalho.
     */
    private void criarArquivoSeNaoExistir() {
        File arquivo = new File(caminhoArquivo);

        // Cria o diretório se não existir
        File diretorio = arquivo.getParentFile();
        if (diretorio != null && !diretorio.exists()) {
            diretorio.mkdirs();
        }

        // Cria o arquivo com cabeçalho se não existir
        if (!arquivo.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
                writer.write(cabecalho);
                writer.newLine();
            } catch (IOException e) {
                System.err.println("Erro ao criar arquivo: " + caminhoArquivo);
                e.printStackTrace();
            }
        }
    }

    /**
     * Método abstrato que converte uma linha CSV em um objeto.
     *
     * Cada subclasse implementa sua própria lógica de conversão.
     *
     * @param linhaCsv Linha do arquivo CSV
     * @return Objeto do tipo T
     */
    protected abstract T fromCSV(String linhaCsv);

    /**
     * Método abstrato que converte um objeto em uma linha CSV.
     *
     * Cada subclasse implementa sua própria lógica de conversão.
     *
     * @param entidade Objeto a ser convertido
     * @return String no formato CSV
     */
    protected abstract String toCSV(T entidade);

    /**
     * Método abstrato que retorna o identificador único de uma entidade.
     * Usado para operações de busca, atualização e exclusão.
     *
     * @param entidade Entidade
     * @return Identificador único (String)
     */
    protected abstract String getId(T entidade);

    /**
     * Salva todos os dados no arquivo CSV.
     * Sobrescreve o arquivo existente.
     *
     * @param entidades Lista de entidades a serem salvas
     * @return true se salvou com sucesso, false em caso de erro
     */
    public boolean salvarTodos(List<T> entidades) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            // Escreve o cabeçalho
            writer.write(cabecalho);
            writer.newLine();

            // Escreve cada entidade
            for (T entidade : entidades) {
                writer.write(toCSV(entidade));
                writer.newLine();
            }

            return true;

        } catch (IOException e) {
            System.err.println("Erro ao salvar no arquivo: " + caminhoArquivo);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Adiciona uma nova entidade ao final do arquivo.
     *
     * @param entidade Entidade a ser adicionada
     * @return true se adicionou com sucesso, false em caso de erro
     */
    public boolean adicionar(T entidade) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            // true = append mode (adiciona ao final)
            writer.write(toCSV(entidade));
            writer.newLine();
            return true;

        } catch (IOException e) {
            System.err.println("Erro ao adicionar no arquivo: " + caminhoArquivo);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Carrega todos os dados do arquivo CSV.
     *
     * @return Lista com todas as entidades do arquivo
     */
    public List<T> buscarTodos() {
        List<T> entidades = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            // Pula o cabeçalho
            String linha = reader.readLine();

            // Lê cada linha e converte em objeto
            while ((linha = reader.readLine()) != null) {
                linha = linha.trim();

                // Ignora linhas vazias
                if (!linha.isEmpty()) {
                    try {
                        T entidade = fromCSV(linha);
                        entidades.add(entidade);
                    } catch (Exception e) {
                        System.err.println("Erro ao converter linha: " + linha);
                        e.printStackTrace();
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + caminhoArquivo);
            // Retorna lista vazia se o arquivo não existir
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + caminhoArquivo);
            e.printStackTrace();
        }

        return entidades;
    }

    /**
     * Busca uma entidade por ID.
     *
     * @param id Identificador único
     * @return Entidade encontrada ou null se não encontrar
     */
    public T buscarPorId(String id) {
        List<T> todas = buscarTodos();

        for (T entidade : todas) {
            if (getId(entidade).equals(id)) {
                return entidade;
            }
        }

        return null; // Não encontrou
    }

    /**
     * Atualiza uma entidade existente.
     * Busca pelo ID e substitui os dados.
     *
     * @param entidadeAtualizada Entidade com dados atualizados
     * @return true se atualizou, false se não encontrou ou erro
     */
    public boolean atualizar(T entidadeAtualizada) {
        List<T> todas = buscarTodos();
        boolean encontrou = false;

        // Procura e substitui a entidade
        for (int i = 0; i < todas.size(); i++) {
            if (getId(todas.get(i)).equals(getId(entidadeAtualizada))) {
                todas.set(i, entidadeAtualizada);
                encontrou = true;
                break;
            }
        }

        // Se encontrou, salva tudo de novo
        if (encontrou) {
            return salvarTodos(todas);
        }

        return false;
    }

    /**
     * Remove uma entidade do arquivo.
     *
     * @param id Identificador da entidade a ser removida
     * @return true se removeu, false se não encontrou ou erro
     */
    public boolean deletar(String id) {
        List<T> todas = buscarTodos();
        boolean removeu = todas.removeIf(entidade -> getId(entidade).equals(id));

        // Se removeu alguma, salva tudo de novo
        if (removeu) {
            return salvarTodos(todas);
        }

        return false;
    }

    /**
     * Verifica se o arquivo existe.
     *
     * @return true se o arquivo existe, false caso contrário
     */
    public boolean arquivoExiste() {
        return new File(caminhoArquivo).exists();
    }

    /**
     * Conta quantas entidades estão armazenadas.
     *
     * @return Número de entidades no arquivo
     */
    public int contar() {
        return buscarTodos().size();
    }

    /**
     * Limpa todo o conteúdo do arquivo, mantendo apenas o cabeçalho.
     *
     * @return true se limpou com sucesso, false em caso de erro
     */
    public boolean limpar() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            writer.write(cabecalho);
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao limpar arquivo: " + caminhoArquivo);
            e.printStackTrace();
            return false;
        }
    }
}
