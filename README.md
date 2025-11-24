# Sistema de Academia

Trabalho da disciplina de Linguagem de Programação Orientada a Objetos (LPOO).

## Sobre

Este é um sistema de gerenciamento para academias desenvolvido em Java com interface gráfica usando Swing. O projeto foi feito para aplicar os conceitos de POO aprendidos na disciplina.

## Funcionalidades

O sistema permite:
- Cadastrar alunos, instrutores e planos
- Realizar matrículas vinculando alunos aos planos
- Registrar pagamentos (aceita PIX, cartão de crédito/débito e dinheiro)
- Listar e visualizar todos os cadastros
- Gerar relatórios básicos

Os dados são salvos em arquivos CSV na pasta `dados/`.

## Estrutura do Projeto

```
SistemaAcademia/
├── src/
│   └── br/trabalho3/sistema/
│       ├── main/           - Classe principal
│       ├── model/          - Classes de domínio
│       ├── persistence/    - Repositórios para salvar/carregar dados
│       ├── exceptions/     - Exceções customizadas
│       ├── utils/          - Validadores (CPF, email)
│       └── ui/             - Telas do sistema
└── dados/                  - Arquivos CSV com os dados
```

## Como Executar

### Opção 1: Usando o script (mais fácil)

```bash
cd SistemaAcademia
./run.sh
```

### Opção 2: Manualmente

```bash
# Compilar
mkdir -p bin
find src -name "*.java" | xargs javac -d bin -sourcepath src

# Executar
java -cp bin br.trabalho3.sistema.main.Main
```

## Conceitos POO Utilizados

### Herança
Três hierarquias de classes foram implementadas:
- Pessoa → Aluno, Instrutor
- Plano → PlanoComum, PlanoPremium, PlanoEstudante
- FormaPagamento → PagamentoPix, PagamentoCartao, PagamentoDinheiro

### Polimorfismo
O método `calcularPrecoFinal()` na classe Plano é implementado de forma diferente em cada subclasse:
- PlanoComum: retorna o preço base
- PlanoPremium: retorna o preço base com acréscimo de 50%
- PlanoEstudante: retorna o preço base com desconto de 30%

### Encapsulamento
Todos os atributos das classes são privados e acessados através de getters e setters.

### Abstração
Quatro classes abstratas foram criadas: Pessoa, Plano, FormaPagamento e CSVRepository.

### Instância Única da Academia
A classe Academia garante que só existe uma academia no sistema inteiro. Isso é feito através de um construtor privado e um método getInstance() que sempre retorna a mesma instância. Assim, todas as partes do sistema trabalham com a mesma academia, evitando problemas de dados duplicados ou inconsistentes.

### Exceções Customizadas
Foram criadas 4 exceções:
- UsuarioNaoEncontradoException
- MatriculaInvalidaException
- PagamentoNaoEncontradoException
- DadosInvalidosException

### Coleções
O sistema usa ArrayList para gerenciar listas de alunos, instrutores, planos, matrículas e pagamentos.

### Persistência
Os dados são salvos em arquivos CSV sem usar bibliotecas externas, apenas BufferedReader e BufferedWriter.

## Detalhes de Implementação

### Sistema de Pagamentos
O sistema permite registrar pagamentos de três formas:
- **PIX**: Basta selecionar a opção
- **Cartão**: É necessário escolher entre crédito ou débito
- **Dinheiro**: Basta selecionar a opção

Quando um pagamento é registrado, o sistema mostra todos os pagamentos anteriores daquela matrícula em uma tabela, além de exibir o total já pago e o valor que ainda falta.

É possível fazer pagamentos parciais. Por exemplo, se uma mensalidade custa R$ 100,00, o aluno pode pagar R$ 50,00 hoje e R$ 50,00 depois.

### Validações
- CPF: validado usando o algoritmo oficial
- Email: validado usando expressão regular
- Campos obrigatórios: o sistema não permite salvar cadastros incompletos

### Arquivos CSV
Os dados ficam salvos em:
- `dados/alunos.csv`
- `dados/instrutores.csv`
- `dados/planos.csv`
- `dados/matriculas.csv`
- `dados/pagamentos.csv`

O sistema carrega esses arquivos ao iniciar e salva automaticamente quando você clica em "Sair".

## Telas do Sistema

1. **Tela Principal**: Menu com todas as opções
2. **Cadastro de Aluno**: Formulário para cadastrar alunos
3. **Cadastro de Instrutor**: Formulário para cadastrar instrutores
4. **Cadastro de Plano**: Formulário para cadastrar planos (mostra o preço final calculado)
5. **Matrícula**: Vincula um aluno a um plano
6. **Pagamento**: Registra pagamentos e mostra histórico
7. **Relatórios**: Exibe informações sobre o sistema
8. **Listar Alunos**: Mostra todos os alunos em uma tabela

## Problemas Conhecidos

Se o sistema não abrir a janela corretamente quando executado dentro do VSCode, execute-o em um terminal normal do Ubuntu. O VSCode às vezes tem problemas para renderizar aplicações Swing.

## Dados de Teste

O sistema já vem com alguns dados de exemplo nos arquivos CSV para facilitar os testes. Você pode apagá-los se quiser começar do zero.

---

**Observação**: Este é um projeto acadêmico desenvolvido para a disciplina LPOO.
