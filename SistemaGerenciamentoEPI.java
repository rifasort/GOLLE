import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * CLASSE PRINCIPAL DO SISTEMA DE GERENCIAMENTO DE EPIs
 *
 * Funcionalidades principais:
 * - Cadastro de usuários, EPIs, empréstimos e devoluções
 * - Operações CRUD (Create, Read, Update, Delete) para todas as entidades
 * - Validação de entrada de dados
 * - Interface de console organizada em menus hierárquicos
 */
public class SistemaGerenciamentoEPI {

    // Coleções para armazenamento em memória
    private static ArrayList<Usuario> usuarios = new ArrayList<>();
    private static ArrayList<EPI> epis = new ArrayList<>();
    private static ArrayList<Emprestimo> emprestimos = new ArrayList<>();
    private static ArrayList<Devolucao> devolucoes = new ArrayList<>();

    // Scanner para leitura de entrada do usuário
    private static Scanner scanner = new Scanner(System.in);

    /**
     * MÉTODO PRINCIPAL - Ponto de entrada do programa
     * @param args Argumentos de linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        // Exibe o menu principal ao iniciar o sistema
        exibirMenuPrincipal();
    }

    // ==================== MENU PRINCIPAL ====================

    /**
     * Exibe o menu principal e gerencia a navegação entre as funcionalidades
     */
    private static void exibirMenuPrincipal() {
        int opcao;
        do {
            // Cabeçalho do menu
            System.out.println("\n=== SISTEMA DE GERENCIAMENTO DE EPIs ===");
            System.out.println("1. Gerenciar Usuários");
            System.out.println("2. Gerenciar EPIs");
            System.out.println("3. Gerenciar Empréstimos");
            System.out.println("4. Gerenciar Devoluções");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            // Lê e valida a opção do usuário
            opcao = lerInteiro();
            scanner.nextLine(); // Limpa o buffer do teclado

            // Navega para a funcionalidade selecionada
            switch (opcao) {
                case 1 -> gerenciarUsuarios();
                case 2 -> gerenciarEPIs();
                case 3 -> gerenciarEmprestimos();
                case 4 -> gerenciarDevolucoes();
                case 0 -> System.out.println("Saindo do sistema...");
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 0); // Repete até o usuário escolher sair
    }

    // ==================== GERENCIAMENTO DE USUÁRIOS ====================

    /**
     * Exibe o menu de gerenciamento de usuários
     */
    private static void gerenciarUsuarios() {
        int opcao;
        do {
            System.out.println("\n=== GERENCIAMENTO DE USUÁRIOS ===");
            System.out.println("1. Cadastrar Usuário");
            System.out.println("2. Listar Usuários");
            System.out.println("3. Atualizar Usuário");
            System.out.println("4. Remover Usuário");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiro();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarUsuario();
                case 2 -> listarUsuarios();
                case 3 -> atualizarUsuario();
                case 4 -> removerUsuario();
                case 0 -> System.out.println("Retornando ao menu principal...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    /**
     * Cadastra um novo usuário no sistema
     */
    private static void cadastrarUsuario() {
        System.out.println("\n--- CADASTRAR USUÁRIO ---");

        // Solicita e valida os dados do usuário
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Departamento: ");
        String departamento = scanner.nextLine();

        System.out.print("Matrícula: ");
        int matricula = lerInteiro();
        scanner.nextLine();

        // Adiciona o novo usuário à lista
        usuarios.add(new Usuario(nome, departamento, matricula));
        System.out.println("Usuário cadastrado com sucesso!");
    }

    /**
     * Lista todos os usuários cadastrados
     */
    private static void listarUsuarios() {
        System.out.println("\n--- LISTA DE USUÁRIOS ---");
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }
        // Exibe cada usuário com seu índice
        for (int i = 0; i < usuarios.size(); i++) {
            System.out.println(i + " - " + usuarios.get(i));
        }
    }

    /**
     * Atualiza os dados de um usuário existente
     */
    private static void atualizarUsuario() {
        listarUsuarios();
        if (usuarios.isEmpty()) return;

        System.out.print("\nÍndice do usuário a atualizar: ");
        int index = lerInteiro();
        scanner.nextLine();

        // Valida o índice informado
        if (index < 0 || index >= usuarios.size()) {
            System.out.println("Índice inválido!");
            return;
        }

        Usuario usuario = usuarios.get(index);

        // Atualiza apenas os campos que receberem nova entrada
        System.out.print("Novo nome [" + usuario.getNome() + "]: ");
        String nome = scanner.nextLine();
        if (!nome.isEmpty()) usuario.setNome(nome);

        System.out.print("Novo departamento [" + usuario.getDepartamento() + "]: ");
        String depto = scanner.nextLine();
        if (!depto.isEmpty()) usuario.setDepartamento(depto);

        System.out.print("Nova matrícula [" + usuario.getMatricula() + "]: ");
        String matStr = scanner.nextLine();
        if (!matStr.isEmpty()) usuario.setMatricula(Integer.parseInt(matStr));

        System.out.println("Usuário atualizado com sucesso!");
    }

    /**
     * Remove um usuário do sistema
     */
    private static void removerUsuario() {
        listarUsuarios();
        if (usuarios.isEmpty()) return;

        System.out.print("\nÍndice do usuário a remover: ");
        int index = lerInteiro();
        scanner.nextLine();

        if (index < 0 || index >= usuarios.size()) {
            System.out.println("Índice inválido!");
            return;
        }

        // Remove o usuário da lista
        usuarios.remove(index);
        System.out.println("Usuário removido com sucesso!");
    }

    // ==================== GERENCIAMENTO DE EPIs ====================

    /**
     * Exibe o menu de gerenciamento de EPIs
     */
    private static void gerenciarEPIs() {
        int opcao;
        do {
            System.out.println("\n=== GERENCIAMENTO DE EPIs ===");
            System.out.println("1. Cadastrar EPI");
            System.out.println("2. Listar EPIs");
            System.out.println("3. Atualizar EPI");
            System.out.println("4. Remover EPI");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiro();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarEPI();
                case 2 -> listarEPIs();
                case 3 -> atualizarEPI();
                case 4 -> removerEPI();
                case 0 -> System.out.println("Retornando ao menu principal...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    /**
     * Cadastra um novo EPI no sistema
     */
    private static void cadastrarEPI() {
        System.out.println("\n--- CADASTRAR EPI ---");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Quantidade: ");
        int quantidade = lerInteiro();
        scanner.nextLine();

        System.out.print("Data de validade (AAAA-MM-DD): ");
        String dataValidade = lerData();

        epis.add(new EPI(nome, quantidade, dataValidade));
        System.out.println("EPI cadastrado com sucesso!");
    }

    /**
     * Lista todos os EPIs cadastrados
     */
    private static void listarEPIs() {
        System.out.println("\n--- LISTA DE EPIs ---");
        if (epis.isEmpty()) {
            System.out.println("Nenhum EPI cadastrado.");
            return;
        }
        for (int i = 0; i < epis.size(); i++) {
            System.out.println(i + " - " + epis.get(i));
        }
    }

    /**
     * Atualiza os dados de um EPI existente
     */
    private static void atualizarEPI() {
        listarEPIs();
        if (epis.isEmpty()) return;

        System.out.print("\nÍndice do EPI a atualizar: ");
        int index = lerInteiro();
        scanner.nextLine();

        if (index < 0 || index >= epis.size()) {
            System.out.println("Índice inválido!");
            return;
        }

        EPI epi = epis.get(index);

        System.out.print("Novo nome [" + epi.getNome() + "]: ");
        String nome = scanner.nextLine();
        if (!nome.isEmpty()) epi.setNome(nome);

        System.out.print("Nova quantidade [" + epi.getQuantidade() + "]: ");
        String qtdStr = scanner.nextLine();
        if (!qtdStr.isEmpty()) epi.setQuantidade(Integer.parseInt(qtdStr));

        System.out.print("Nova data de validade [" + epi.getDataValidade() + "]: ");
        String data = lerData();
        if (!data.isEmpty()) epi.setDataValidade(data);

        System.out.println("EPI atualizado com sucesso!");
    }

    /**
     * Remove um EPI do sistema
     */
    private static void removerEPI() {
        listarEPIs();
        if (epis.isEmpty()) return;

        System.out.print("\nÍndice do EPI a remover: ");
        int index = lerInteiro();
        scanner.nextLine();

        if (index < 0 || index >= epis.size()) {
            System.out.println("Índice inválido!");
            return;
        }

        epis.remove(index);
        System.out.println("EPI removido com sucesso!");
    }

    // ==================== GERENCIAMENTO DE EMPRÉSTIMOS ====================

    /**
     * Exibe o menu de gerenciamento de empréstimos
     */
    private static void gerenciarEmprestimos() {
        int opcao;
        do {
            System.out.println("\n=== GERENCIAMENTO DE EMPRÉSTIMOS ===");
            System.out.println("1. Cadastrar Empréstimo");
            System.out.println("2. Listar Empréstimos");
            System.out.println("3. Atualizar Empréstimo");
            System.out.println("4. Remover Empréstimo");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiro();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarEmprestimo();
                case 2 -> listarEmprestimos();
                case 3 -> atualizarEmprestimo();
                case 4 -> removerEmprestimo();
                case 0 -> System.out.println("Retornando ao menu principal...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    /**
     * Cadastra um novo empréstimo no sistema
     */
    private static void cadastrarEmprestimo() {
        System.out.println("\n--- CADASTRAR EMPRÉSTIMO ---");

        // Seleção do usuário
        listarUsuarios();
        if (usuarios.isEmpty()) return;

        System.out.print("Índice do usuário: ");
        int usuarioIndex = lerInteiro();
        scanner.nextLine();

        if (usuarioIndex < 0 || usuarioIndex >= usuarios.size()) {
            System.out.println("Índice inválido!");
            return;
        }

        // Seleção do EPI
        listarEPIs();
        if (epis.isEmpty()) return;

        System.out.print("Índice do EPI: ");
        int epiIndex = lerInteiro();
        scanner.nextLine();

        if (epiIndex < 0 || epiIndex >= epis.size()) {
            System.out.println("Índice inválido!");
            return;
        }

        // Datas do empréstimo
        System.out.print("Data do empréstimo (AAAA-MM-DD): ");
        LocalDate dataEmp = lerLocalDate();

        System.out.print("Data de devolução prevista (AAAA-MM-DD): ");
        LocalDate dataDev = lerLocalDate();

        // Cria e armazena o novo empréstimo
        emprestimos.add(new Emprestimo(
                usuarios.get(usuarioIndex),
                epis.get(epiIndex),
                dataEmp,
                dataDev
        ));
        System.out.println("Empréstimo cadastrado com sucesso!");
    }

    /**
     * Lista todos os empréstimos cadastrados
     */
    private static void listarEmprestimos() {
        System.out.println("\n--- LISTA DE EMPRÉSTIMOS ---");
        if (emprestimos.isEmpty()) {
            System.out.println("Nenhum empréstimo cadastrado.");
            return;
        }
        for (int i = 0; i < emprestimos.size(); i++) {
            System.out.println(i + " - " + emprestimos.get(i));
        }
    }

    /**
     * Atualiza os dados de um empréstimo existente
     */
    private static void atualizarEmprestimo() {
        listarEmprestimos();
        if (emprestimos.isEmpty()) return;

        System.out.print("\nÍndice do empréstimo a atualizar: ");
        int index = lerInteiro();
        scanner.nextLine();

        if (index < 0 || index >= emprestimos.size()) {
            System.out.println("Índice inválido!");
            return;
        }

        Emprestimo emp = emprestimos.get(index);

        // Atualização do usuário
        listarUsuarios();
        System.out.print("Novo índice do usuário [" + usuarios.indexOf(emp.getUsuario()) + "]: ");
        String userStr = scanner.nextLine();
        if (!userStr.isEmpty()) {
            int userIndex = Integer.parseInt(userStr);
            if (userIndex >= 0 && userIndex < usuarios.size()) {
                emp.setUsuario(usuarios.get(userIndex));
            }
        }

        // Atualização do EPI
        listarEPIs();
        System.out.print("Novo índice do EPI [" + epis.indexOf(emp.getEpi()) + "]: ");
        String epiStr = scanner.nextLine();
        if (!epiStr.isEmpty()) {
            int epiIndex = Integer.parseInt(epiStr);
            if (epiIndex >= 0 && epiIndex < epis.size()) {
                emp.setEpi(epis.get(epiIndex));
            }
        }

        // Atualização das datas
        System.out.print("Nova data de empréstimo [" + emp.getDataEmprestimo() + "]: ");
        String dataEmpStr = scanner.nextLine();
        if (!dataEmpStr.isEmpty()) {
            emp.setDataEmprestimo(LocalDate.parse(dataEmpStr));
        }

        System.out.print("Nova data de devolução [" + emp.getDataDevolucaoPrevista() + "]: ");
        String dataDevStr = scanner.nextLine();
        if (!dataDevStr.isEmpty()) {
            emp.setDataDevolucaoPrevista(LocalDate.parse(dataDevStr));
        }

        System.out.println("Empréstimo atualizado com sucesso!");
    }

    /**
     * Remove um empréstimo do sistema
     */
    private static void removerEmprestimo() {
        listarEmprestimos();
        if (emprestimos.isEmpty()) return;

        System.out.print("\nÍndice do empréstimo a remover: ");
        int index = lerInteiro();
        scanner.nextLine();

        if (index < 0 || index >= emprestimos.size()) {
            System.out.println("Índice inválido!");
            return;
        }

        emprestimos.remove(index);
        System.out.println("Empréstimo removido com sucesso!");
    }

    // ==================== GERENCIAMENTO DE DEVOLUÇÕES ====================

    /**
     * Exibe o menu de gerenciamento de devoluções
     */
    private static void gerenciarDevolucoes() {
        int opcao;
        do {
            System.out.println("\n=== GERENCIAMENTO DE DEVOLUÇÕES ===");
            System.out.println("1. Cadastrar Devolução");
            System.out.println("2. Listar Devoluções");
            System.out.println("3. Atualizar Devolução");
            System.out.println("4. Remover Devolução");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiro();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarDevolucao();
                case 2 -> listarDevolucoes();
                case 3 -> atualizarDevolucao();
                case 4 -> removerDevolucao();
                case 0 -> System.out.println("Retornando ao menu principal...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    /**
     * Cadastra uma nova devolução no sistema
     */
    private static void cadastrarDevolucao() {
        System.out.println("\n--- CADASTRAR DEVOLUÇÃO ---");

        // Seleção do empréstimo
        listarEmprestimos();
        if (emprestimos.isEmpty()) return;

        System.out.print("Índice do empréstimo: ");
        int empIndex = lerInteiro();
        scanner.nextLine();

        if (empIndex < 0 || empIndex >= emprestimos.size()) {
            System.out.println("Índice inválido!");
            return;
        }

        // Data e observação da devolução
        System.out.print("Data da devolução (AAAA-MM-DD): ");
        LocalDate dataDev = lerLocalDate();

        System.out.print("Observação: ");
        String obs = scanner.nextLine();

        // Cria e armazena a nova devolução
        devolucoes.add(new Devolucao(
                emprestimos.get(empIndex),
                dataDev,
                obs
        ));
        System.out.println("Devolução cadastrada com sucesso!");
    }

    /**
     * Lista todas as devoluções cadastradas
     */
    private static void listarDevolucoes() {
        System.out.println("\n--- LISTA DE DEVOLUÇÕES ---");
        if (devolucoes.isEmpty()) {
            System.out.println("Nenhuma devolução cadastrada.");
            return;
        }
        for (int i = 0; i < devolucoes.size(); i++) {
            System.out.println(i + " - " + devolucoes.get(i));
        }
    }

    /**
     * Atualiza os dados de uma devolução existente
     */
    private static void atualizarDevolucao() {
        listarDevolucoes();
        if (devolucoes.isEmpty()) return;

        System.out.print("\nÍndice da devolução a atualizar: ");
        int index = lerInteiro();
        scanner.nextLine();

        if (index < 0 || index >= devolucoes.size()) {
            System.out.println("Índice inválido!");
            return;
        }

        Devolucao dev = devolucoes.get(index);

        // Atualização do empréstimo relacionado
        listarEmprestimos();
        System.out.print("Novo índice do empréstimo [" + emprestimos.indexOf(dev.getEmprestimo()) + "]: ");
        String empStr = scanner.nextLine();
        if (!empStr.isEmpty()) {
            int empIndex = Integer.parseInt(empStr);
            if (empIndex >= 0 && empIndex < emprestimos.size()) {
                dev.setEmprestimo(emprestimos.get(empIndex));
            }
        }

        // Atualização da data
        System.out.print("Nova data de devolução [" + dev.getDataDevolucao() + "]: ");
        String dataDevStr = scanner.nextLine();
        if (!dataDevStr.isEmpty()) {
            dev.setDataDevolucao(LocalDate.parse(dataDevStr));
        }

        // Atualização da observação
        System.out.print("Nova observação [" + dev.getObservacao() + "]: ");
        String obs = scanner.nextLine();
        if (!obs.isEmpty()) {
            dev.setObservacao(obs);
        }

        System.out.println("Devolução atualizada com sucesso!");
    }

    /**
     * Remove uma devolução do sistema
     */
    private static void removerDevolucao() {
        listarDevolucoes();
        if (devolucoes.isEmpty()) return;

        System.out.print("\nÍndice da devolução a remover: ");
        int index = lerInteiro();
        scanner.nextLine();

        if (index < 0 || index >= devolucoes.size()) {
            System.out.println("Índice inválido!");
            return;
        }

        devolucoes.remove(index);
        System.out.println("Devolução removida com sucesso!");
    }

    // ==================== MÉTODOS AUXILIARES ====================

    /**
     * Lê e valida um número inteiro da entrada do usuário
     * @return O número inteiro validado
     */
    private static int lerInteiro() {
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, digite um número válido!");
            scanner.next(); // Descarta a entrada inválida
        }
        return scanner.nextInt();
    }

    /**
     * Lê e valida uma data no formato String (AAAA-MM-DD)
     * @return A data no formato String ou string vazia se não informada
     */
    private static String lerData() {
        while (true) {
            try {
                String data = scanner.nextLine();
                if (data.isEmpty()) return "";
                LocalDate.parse(data); // Valida o formato
                return data;
            } catch (DateTimeParseException e) {
                System.out.print("Formato inválido. Use AAAA-MM-DD. Tente novamente: ");
            }
        }
    }

    /**
     * Lê e valida uma data no formato LocalDate (AAAA-MM-DD)
     * @return O objeto LocalDate correspondente à data informada
     */
    private static LocalDate lerLocalDate() {
        while (true) {
            try {
                String data = scanner.nextLine();
                return LocalDate.parse(data);
            } catch (DateTimeParseException e) {
                System.out.print("Formato inválido. Use AAAA-MM-DD. Tente novamente: ");
            }
        }
    }
}

// ==================== CLASSES DE DOMÍNIO ====================

/**
 * Classe que representa um usuário do sistema
 * Atributos:
 * - nome: Nome completo do usuário
 * - departamento: Departamento ao qual o usuário pertence
 * - matricula: Número de matrícula do usuário
 */
class Usuario {
    private String nome;
    private String departamento;
    private int matricula;

    /**
     * Construtor da classe Usuario
     * @param nome Nome do usuário
     * @param departamento Departamento do usuário
     * @param matricula Número de matrícula
     */
    public Usuario(String nome, String departamento, int matricula) {
        this.nome = nome;
        this.departamento = departamento;
        this.matricula = matricula;
    }

    // Métodos getters e setters
    public String getNome() { return nome; }
    public String getDepartamento() { return departamento; }
    public int getMatricula() { return matricula; }

    public void setNome(String nome) { this.nome = nome; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    public void setMatricula(int matricula) { this.matricula = matricula; }

    /**
     * Retorna uma representação em string do usuário
     * @return String formatada com os dados do usuário
     */
    @Override
    public String toString() {
        return String.format("Nome: %-15s | Depto: %-10s | Matrícula: %06d",
                nome, departamento, matricula);
    }
}

/**
 * Classe que representa um Equipamento de Proteção Individual (EPI)
 * Atributos:
 * - nome: Nome/descrição do EPI
 * - quantidade: Quantidade disponível em estoque
 * - dataValidade: Data de validade do EPI (formato String)
 */
class EPI {
    private String nome;
    private int quantidade;
    private String dataValidade;

    /**
     * Construtor da classe EPI
     * @param nome Nome do EPI
     * @param quantidade Quantidade disponível
     * @param dataValidade Data de validade no formato AAAA-MM-DD
     */
    public EPI(String nome, int quantidade, String dataValidade) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.dataValidade = dataValidade;
    }

    // Métodos getters e setters
    public String getNome() { return nome; }
    public int getQuantidade() { return quantidade; }
    public String getDataValidade() { return dataValidade; }

    public void setNome(String nome) { this.nome = nome; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public void setDataValidade(String dataValidade) { this.dataValidade = dataValidade; }

    /**
     * Retorna uma representação em string do EPI
     * @return String formatada com os dados do EPI
     */
    @Override
    public String toString() {
        return String.format("Nome: %-15s | Quantidade: %3d | Validade: %10s",
                nome, quantidade, dataValidade);
    }
}

/**
 * Classe que representa um empréstimo de EPI para um usuário
 * Atributos:
 * - usuario: Usuário que realizou o empréstimo
 * - epi: EPI emprestado
 * - dataEmprestimo: Data em que o empréstimo foi realizado
 * - dataDevolucaoPrevista: Data prevista para devolução
 */
class Emprestimo {
    private Usuario usuario;
    private EPI epi;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;

    /**
     * Construtor da classe Emprestimo
     * @param usuario Usuário que realizou o empréstimo
     * @param epi EPI emprestado
     * @param dataEmprestimo Data do empréstimo
     * @param dataDevolucaoPrevista Data prevista para devolução
     */
    public Emprestimo(Usuario usuario, EPI epi, LocalDate dataEmprestimo, LocalDate dataDevolucaoPrevista) {
        this.usuario = usuario;
        this.epi = epi;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    // Métodos getters e setters
    public Usuario getUsuario() { return usuario; }
    public EPI getEpi() { return epi; }
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public LocalDate getDataDevolucaoPrevista() { return dataDevolucaoPrevista; }

    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public void setEpi(EPI epi) { this.epi = epi; }
    public void setDataEmprestimo(LocalDate dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }
    public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) { this.dataDevolucaoPrevista = dataDevolucaoPrevista; }

    /**
     * Retorna uma representação em string do empréstimo
     * @return String formatada com os dados do empréstimo
     */
    @Override
    public String toString() {
        return String.format("Usuário: %-15s | EPI: %-15s | Empréstimo: %s | Devolução Prevista: %s",
                usuario.getNome(), epi.getNome(), dataEmprestimo, dataDevolucaoPrevista);
    }
}

/**
 * Classe que representa a devolução de um EPI emprestado
 * Atributos:
 * - emprestimo: Empréstimo relacionado à devolução
 * - dataDevolucao: Data em que o EPI foi devolvido
 * - observacao: Observações sobre a devolução
 */
class Devolucao {
    private Emprestimo emprestimo;
    private LocalDate dataDevolucao;
    private String observacao;

    /**
     * Construtor da classe Devolucao
     * @param emprestimo Empréstimo relacionado
     * @param dataDevolucao Data da devolução
     * @param observacao Observações adicionais
     */
    public Devolucao(Emprestimo emprestimo, LocalDate dataDevolucao, String observacao) {
        this.emprestimo = emprestimo;
        this.dataDevolucao = dataDevolucao;
        this.observacao = observacao;
    }

    // Métodos getters e setters
    public Emprestimo getEmprestimo() { return emprestimo; }
    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public String getObservacao() { return observacao; }

    public void setEmprestimo(Emprestimo emprestimo) { this.emprestimo = emprestimo; }
    public void setDataDevolucao(LocalDate dataDevolucao) { this.dataDevolucao = dataDevolucao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    /**
     * Retorna uma representação em string da devolução
     * @return String formatada com os dados da devolução
     */
    @Override
    public String toString() {
        return String.format("Usuário: %-15s | EPI: %-15s | Devolução: %s | Observação: %s",
                emprestimo.getUsuario().getNome(), emprestimo.getEpi().getNome(),
                dataDevolucao, observacao);
    }
}