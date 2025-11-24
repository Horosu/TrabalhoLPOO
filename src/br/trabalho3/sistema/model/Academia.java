package br.trabalho3.sistema.model;

import br.trabalho3.sistema.exceptions.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe central do sistema que gerencia todas as operações da academia.
 * *
 * Esta classe é o ponto central de acesso a todas as funcionalidades do sistema.
 * A interface gráfica (UI) se comunica apenas com esta classe, que por sua vez
 * coordena as demais classes do modelo.
 *
 */
public class Academia {

    /**
     * Instância única da classe Academia (padrão Singleton).
     * SINGLETON PATTERN
     */
    private static Academia instancia;

    /**
     * Lista de todos os alunos cadastrados.
     * COLEÇÕES (ArrayList)
     */
    private List<Aluno> alunos;

    /**
     * Lista de todos os instrutores cadastrados.
     */
    private List<Instrutor> instrutores;

    /**
     * Lista de todos os planos disponíveis.
     */
    private List<Plano> planos;

    /**
     * Lista de todas as matrículas realizadas.
     */
    private List<Matricula> matriculas;

    /**
     * Lista de todos os pagamentos registrados.
     */
    private List<Pagamento> pagamentos;

    /**
     * Contador para gerar IDs únicos de matrículas.
     */
    private int contadorMatriculas;

    /**
     * Contador para gerar IDs únicos de pagamentos.
     */
    private int contadorPagamentos;

    /**
     * Construtor privado para implementar o padrão Singleton.
     * SINGLETON PATTERN
     *
     * O construtor é privado para que nenhuma outra classe possa
     * instanciar Academia diretamente. A única forma de obter uma
     * instância é através do método getInstance().
     */
    private Academia() {
        // Inicializa todas as coleções
        this.alunos = new ArrayList<>();
        this.instrutores = new ArrayList<>();
        this.planos = new ArrayList<>();
        this.matriculas = new ArrayList<>();
        this.pagamentos = new ArrayList<>();
        this.contadorMatriculas = 1;
        this.contadorPagamentos = 1;
    }

    /**
     * Obtém a instância única da Academia.
     *
     * SINGLETON PATTERN
     * Este método garante que apenas uma instância da classe Academia
     * existe em toda a aplicação. Se ainda não existe, cria uma nova.
     * Se já existe, retorna a existente.
     *
     * @return Instância única de Academia
     */
    public static Academia getInstance() {
        if (instancia == null) {
            instancia = new Academia();
        }
        return instancia;
    }

    // ========== MÉTODOS DE ALUNOS ==========

    /**
     * Adiciona um novo aluno ao sistema.
     *
     * @param aluno Aluno a ser adicionado
     * @throws DadosInvalidosException Se os dados do aluno forem inválidos
     */
    public void adicionarAluno(Aluno aluno) throws DadosInvalidosException {
        // Valida se o aluno não é nulo
        if (aluno == null) {
            throw new DadosInvalidosException("Aluno não pode ser nulo");
        }

        // Verifica se já existe um aluno com este CPF
        for (Aluno a : alunos) {
            if (a.getCpf().equals(aluno.getCpf())) {
                throw new DadosInvalidosException("Já existe um aluno com o CPF: " + aluno.getCpf());
            }
        }

        alunos.add(aluno);
    }

    /**
     * Busca um aluno pelo CPF.
     *
     * @param cpf CPF do aluno
     * @return Aluno encontrado
     * @throws UsuarioNaoEncontradoException Se o aluno não for encontrado
     */
    public Aluno buscarAlunoPorCpf(String cpf) throws UsuarioNaoEncontradoException {
        for (Aluno aluno : alunos) {
            if (aluno.getCpf().equals(cpf)) {
                return aluno;
            }
        }
        throw new UsuarioNaoEncontradoException("Aluno com CPF " + cpf + " não encontrado");
    }

    /**
     * Remove um aluno do sistema.
     *
     * @param cpf CPF do aluno a ser removido
     * @return true se removeu, false se não encontrou
     */
    public boolean removerAluno(String cpf) {
        return alunos.removeIf(aluno -> aluno.getCpf().equals(cpf));
    }

    /**
     * Atualiza os dados de um aluno existente.
     *
     * @param alunoAtualizado Aluno com dados atualizados
     * @throws UsuarioNaoEncontradoException Se o aluno não for encontrado
     */
    public void atualizarAluno(Aluno alunoAtualizado) throws UsuarioNaoEncontradoException {
        for (int i = 0; i < alunos.size(); i++) {
            if (alunos.get(i).getCpf().equals(alunoAtualizado.getCpf())) {
                alunos.set(i, alunoAtualizado);
                return;
            }
        }
        throw new UsuarioNaoEncontradoException("Aluno com CPF " + alunoAtualizado.getCpf() + " não encontrado");
    }

    /**
     * Retorna todos os alunos cadastrados.
     *
     * @return Lista de alunos
     */
    public List<Aluno> listarAlunos() {
        return new ArrayList<>(alunos); // Retorna uma cópia para segurança
    }

    // ========== MÉTODOS DE INSTRUTORES ==========

    /**
     * Adiciona um novo instrutor ao sistema.
     *
     * @param instrutor Instrutor a ser adicionado
     * @throws DadosInvalidosException Se os dados forem inválidos
     */
    public void adicionarInstrutor(Instrutor instrutor) throws DadosInvalidosException {
        if (instrutor == null) {
            throw new DadosInvalidosException("Instrutor não pode ser nulo");
        }

        // Verifica se já existe um instrutor com este CPF
        for (Instrutor i : instrutores) {
            if (i.getCpf().equals(instrutor.getCpf())) {
                throw new DadosInvalidosException("Já existe um instrutor com o CPF: " + instrutor.getCpf());
            }
        }

        instrutores.add(instrutor);
    }

    /**
     * Busca um instrutor pelo CPF.
     *
     * @param cpf CPF do instrutor
     * @return Instrutor encontrado
     * @throws UsuarioNaoEncontradoException Se não encontrar
     */
    public Instrutor buscarInstrutorPorCpf(String cpf) throws UsuarioNaoEncontradoException {
        for (Instrutor instrutor : instrutores) {
            if (instrutor.getCpf().equals(cpf)) {
                return instrutor;
            }
        }
        throw new UsuarioNaoEncontradoException("Instrutor com CPF " + cpf + " não encontrado");
    }

    /**
     * Remove um instrutor do sistema.
     *
     * @param cpf CPF do instrutor
     * @return true se removeu, false se não encontrou
     */
    public boolean removerInstrutor(String cpf) {
        return instrutores.removeIf(instrutor -> instrutor.getCpf().equals(cpf));
    }

    /**
     * Atualiza os dados de um instrutor existente.
     *
     * @param instrutorAtualizado Instrutor com dados atualizados
     * @throws UsuarioNaoEncontradoException Se não encontrar
     */
    public void atualizarInstrutor(Instrutor instrutorAtualizado) throws UsuarioNaoEncontradoException {
        for (int i = 0; i < instrutores.size(); i++) {
            if (instrutores.get(i).getCpf().equals(instrutorAtualizado.getCpf())) {
                instrutores.set(i, instrutorAtualizado);
                return;
            }
        }
        throw new UsuarioNaoEncontradoException("Instrutor com CPF " + instrutorAtualizado.getCpf() + " não encontrado");
    }

    /**
     * Retorna todos os instrutores cadastrados.
     *
     * @return Lista de instrutores
     */
    public List<Instrutor> listarInstrutores() {
        return new ArrayList<>(instrutores);
    }

    // ========== MÉTODOS DE PLANOS ==========

    /**
     * Adiciona um novo plano ao sistema.
     *
     * @param plano Plano a ser adicionado
     * @throws DadosInvalidosException Se os dados forem inválidos
     */
    public void adicionarPlano(Plano plano) throws DadosInvalidosException {
        if (plano == null) {
            throw new DadosInvalidosException("Plano não pode ser nulo");
        }

        // Verifica se já existe um plano com este ID
        for (Plano p : planos) {
            if (p.getId().equals(plano.getId())) {
                throw new DadosInvalidosException("Já existe um plano com o ID: " + plano.getId());
            }
        }

        planos.add(plano);
    }

    /**
     * Busca um plano pelo ID.
     *
     * @param id ID do plano
     * @return Plano encontrado
     * @throws DadosInvalidosException Se não encontrar
     */
    public Plano buscarPlanoPorId(String id) throws DadosInvalidosException {
        for (Plano plano : planos) {
            if (plano.getId().equals(id)) {
                return plano;
            }
        }
        throw new DadosInvalidosException("Plano com ID " + id + " não encontrado");
    }

    /**
     * Remove um plano do sistema.
     *
     * @param id ID do plano
     * @return true se removeu, false se não encontrou
     */
    public boolean removerPlano(String id) {
        return planos.removeIf(plano -> plano.getId().equals(id));
    }

    /**
     * Retorna todos os planos cadastrados.
     *
     * @return Lista de planos
     */
    public List<Plano> listarPlanos() {
        return new ArrayList<>(planos);
    }

    // ========== MÉTODOS DE MATRÍCULAS ==========

    /**
     * Realiza uma nova matrícula de aluno em um plano.
     *
     * POLIMORFISMO
     * O preço mensal é calculado usando o método polimórfico calcularPrecoFinal()
     * que tem implementações diferentes em cada tipo de plano.
     *
     * @param aluno Aluno a ser matriculado
     * @param plano Plano escolhido
     * @param dataInicio Data de início
     * @param dataFim Data de término
     * @return Matrícula criada
     * @throws MatriculaInvalidaException Se a matrícula for inválida
     */
    public Matricula realizarMatricula(Aluno aluno, Plano plano, Date dataInicio, Date dataFim)
            throws MatriculaInvalidaException {

        // Valida se o aluno já possui matrícula ativa
        for (Matricula m : matriculas) {
            if (m.getAluno().getCpf().equals(aluno.getCpf()) && m.getStatus() == StatusMatricula.ATIVA) {
                throw new MatriculaInvalidaException(
                    "Aluno " + aluno.getNome() + " já possui uma matrícula ativa");
            }
        }

        // Valida datas
        if (dataInicio.after(dataFim)) {
            throw new MatriculaInvalidaException("Data de início não pode ser posterior à data de fim");
        }

        // Cria a matrícula
        String id = String.format("MAT%03d", contadorMatriculas++);

        // POLIMORFISMO EM AÇÃO: calcularPrecoFinal() retorna valores diferentes
        // dependendo do tipo de plano (Comum, Premium ou Estudante)
        double valorMensal = plano.calcularPrecoFinal();

        Matricula matricula = new Matricula(
            id, aluno, plano, dataInicio, dataFim,
            StatusMatricula.ATIVA, valorMensal
        );

        matriculas.add(matricula);
        return matricula;
    }

    /**
     * Busca uma matrícula pelo ID.
     *
     * @param id ID da matrícula
     * @return Matrícula encontrada
     * @throws MatriculaInvalidaException Se não encontrar
     */
    public Matricula buscarMatriculaPorId(String id) throws MatriculaInvalidaException {
        for (Matricula matricula : matriculas) {
            if (matricula.getId().equals(id)) {
                return matricula;
            }
        }
        throw new MatriculaInvalidaException("Matrícula com ID " + id + " não encontrada");
    }

    /**
     * Retorna todas as matrículas cadastradas.
     *
     * @return Lista de matrículas
     */
    public List<Matricula> listarMatriculas() {
        return new ArrayList<>(matriculas);
    }

    /**
     * Retorna matrículas ativas de um aluno.
     *
     * @param cpfAluno CPF do aluno
     * @return Lista de matrículas ativas
     */
    public List<Matricula> listarMatriculasAtivasPorAluno(String cpfAluno) {
        return matriculas.stream()
            .filter(m -> m.getAluno().getCpf().equals(cpfAluno) && m.estaAtiva())
            .collect(Collectors.toList());
    }

    // ========== MÉTODOS DE PAGAMENTOS ==========

    /**
     * Registra um novo pagamento.
     *
     * POLIMORFISMO
     * A validação do pagamento usa o método polimórfico validarPagamento()
     * que tem implementações diferentes para cada forma de pagamento.
     *
     * @param matricula Matrícula associada
     * @param formaPagamento Forma de pagamento (PIX, Cartão ou Dinheiro)
     * @param valor Valor pago
     * @param dataPagamento Data do pagamento
     * @return Pagamento criado
     * @throws DadosInvalidosException Se os dados forem inválidos
     */
    public Pagamento registrarPagamento(Matricula matricula, FormaPagamento formaPagamento,
                                       double valor, Date dataPagamento)
            throws DadosInvalidosException {

        // POLIMORFISMO EM AÇÃO: validarPagamento() tem lógica diferente
        // para PIX, Cartão e Dinheiro
        if (!formaPagamento.validarPagamento()) {
            throw new DadosInvalidosException("Dados da forma de pagamento inválidos");
        }

        if (valor <= 0) {
            throw new DadosInvalidosException("Valor deve ser maior que zero");
        }

        // Cria o pagamento
        String id = String.format("PAG%03d", contadorPagamentos++);

        Pagamento pagamento = new Pagamento(
            id, matricula, formaPagamento, valor,
            dataPagamento, StatusPagamento.CONFIRMADO
        );

        pagamentos.add(pagamento);
        return pagamento;
    }

    /**
     * Busca um pagamento pelo ID.
     *
     * @param id ID do pagamento
     * @return Pagamento encontrado
     * @throws PagamentoNaoEncontradoException Se não encontrar
     */
    public Pagamento buscarPagamentoPorId(String id) throws PagamentoNaoEncontradoException {
        for (Pagamento pagamento : pagamentos) {
            if (pagamento.getId().equals(id)) {
                return pagamento;
            }
        }
        throw new PagamentoNaoEncontradoException("Pagamento com ID " + id + " não encontrado");
    }

    /**
     * Retorna todos os pagamentos registrados.
     *
     * @return Lista de pagamentos
     */
    public List<Pagamento> listarPagamentos() {
        return new ArrayList<>(pagamentos);
    }

    /**
     * Retorna pagamentos de uma matrícula específica.
     *
     * @param idMatricula ID da matrícula
     * @return Lista de pagamentos
     */
    public List<Pagamento> listarPagamentosPorMatricula(String idMatricula) {
        return pagamentos.stream()
            .filter(p -> p.getMatricula().getId().equals(idMatricula))
            .collect(Collectors.toList());
    }

    // ========== MÉTODOS AUXILIARES ==========

    /**
     * Limpa todos os dados do sistema.
     * Útil para testes e reset.
     */
    public void limparTodosDados() {
        alunos.clear();
        instrutores.clear();
        planos.clear();
        matriculas.clear();
        pagamentos.clear();
        contadorMatriculas = 1;
        contadorPagamentos = 1;
    }

    /**
     * Define os contadores de IDs.
     * Usado ao carregar dados do arquivo.
     *
     * @param ultimaMatricula Último ID de matrícula usado
     * @param ultimoPagamento Último ID de pagamento usado
     */
    public void setContadores(int ultimaMatricula, int ultimoPagamento) {
        this.contadorMatriculas = ultimaMatricula + 1;
        this.contadorPagamentos = ultimoPagamento + 1;
    }
}
