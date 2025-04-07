import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * SISTEMA DE GERENCIAMENTO DE EQUIPAMENTOS DE PROTEÇÃO INDIVIDUAL (EPIs)
 *
 * Este sistema permite:
 * - Cadastro e gerenciamento de usuários, EPIs, empréstimos e devoluções
 * - Operações CRUD completas para todas as entidades
 * - Validação robusta de todos os tipos de entrada
 * - Tratamento adequado para entradas vazias ou inválidas
 * - Interface intuitiva com menus hierárquicos
 */
public class SistemaGerenciamentoEPI {

    // Coleções para armazenamento de dados em memória
    private static ArrayList<Usuario> usuarios = new ArrayList<>();
    private static ArrayList<EPI> epis = new ArrayList<>();
    private static ArrayList<Emprestimo> emprestimos = new ArrayList<>();
    private static ArrayList<Devolucao> devolucoes = new ArrayList<>();

    // Scanner global para leitura de entrada do usuário
    private static Scanner scanner = new Scanner(System.in);

    /**
     * MÉTODO PRINCIPAL - Ponto de entrada do sistema
     */
    public static void main(String[] args) {
        exibirMenuPrincipal(); // Inicia o sistema mostrando o menu principal
    }

    // ==================== MÉTODOS AUXILIARES ====================

    /**
     * Lê uma string da entrada do usuário que não pode ser vazia
     * @param mensagem A mensagem a ser exibida para solicitar a entrada
     * @return String não vazia fornecida pelo usuário
     */
    private static String lerStringNaoVazia(String mensagem) {
        String entrada;
        do {
            System.out.print(mensagem);
            entrada = scanner.nextLine().trim();
            if (entrada.isEmpty()) {
                System.out.println("Erro: Este campo é obrigatório. Por favor, digite um valor.");
            }
        } while (entrada.isEmpty());
        return entrada;
    }

    /**
     * Lê e valida um número inteiro da entrada do usuário
     * Continua solicitando até que um valor inteiro válido seja fornecido
     * @return O número inteiro validado
     */
    private static int lerInteiro() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.print("Erro: Campo obrigatório. Digite um número: ");
                    continue;
                }
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Erro: Por favor, digite um número inteiro válido: ");
            }
        }
    }

    /**
     * Lê e valida uma data no formato String (AAAA-MM-DD)
     * Permite entrada vazia em campos opcionais
     * @return A data no formato String ou string vazia se não informada
     */
    private static String lerData() {
        while (true) {
            try {
                String data = scanner.nextLine().trim();
                if (data.isEmpty()) return ""; // Permite vazio para campos opcionais
                LocalDate.parse(data); // Valida o formato da data
                return data;
            } catch (DateTimeParseException e) {
                System.out.print("Erro: Formato inválido. Use AAAA-MM-DD. Tente novamente: ");
            }
        }
    }

    /**
     * Lê e valida uma data no formato LocalDate (AAAA-MM-DD)
     * Não permite entrada vazia
     * @return O objeto LocalDate correspondente à data informada
     */
    private static LocalDate lerLocalDate() {
        while (true) {
            try {
                String data = lerStringNaoVazia("Data (AAAA-MM-DD): ");
                return LocalDate.parse(data);
            } catch (DateTimeParseException e) {
                System.out.println("Erro: Formato de data inválido. Use AAAA-MM-DD.");
            }
        }
    }

    // ==================== MENU PRINCIPAL ====================

    /**
     * Exibe o menu principal e gerencia a navegação entre as funcionalidades
     * Implementa loop até que o usuário escolha sair (opção 0)
     */
    private static void exibirMenuPrincipal() {
        int opcao;
        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Gerenciar Usuários");
            System.out.println("2. Gerenciar EPIs");
            System.out.println("3. Gerenciar Empréstimos");
            System.out.println("4. Gerenciar Devoluções");
            System.out.println("0. Sair do Sistema");
            System.out.print("Escolha uma opção: ");

            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Erro: Por favor, digite o número da opção desejada.");
                    opcao = -1;
                    continue;
                }
                opcao = Integer.parseInt(input);

                switch (opcao) {
                    case 1 -> gerenciarUsuarios();
                    case 2 -> gerenciarEPIs();
                    case 3 -> gerenciarEmprestimos();
                    case 4 -> gerenciarDevolucoes();
                    case 0 -> System.out.println("\nSaindo do sistema...");
                    default -> System.out.println("Erro: Opção inválida! Digite um número entre 0 e 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite apenas números.");
                opcao = -1;
            }
        } while (opcao != 0);
    }

    // ==================== GERENCIAMENTO DE USUÁRIOS ====================

    /**
     * Menu de gerenciamento de usuários
     * Permite cadastrar, listar, atualizar e remover usuários
     */
    private static void gerenciarUsuarios() {
        int opcao;
        do {
            System.out.println("\n=== GERENCIAMENTO DE USUÁRIOS ===");
            System.out.println("1. Cadastrar Novo Usuário");
            System.out.println("2. Listar Todos os Usuários");
            System.out.println("3. Atualizar Usuário Existente");
            System.out.println("4. Remover Usuário");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());

                switch (opcao) {
                    case 1 -> cadastrarUsuario();
                    case 2 -> listarUsuarios();
                    case 3 -> atualizarUsuario();
                    case 4 -> removerUsuario();
                    case 0 -> System.out.println("Retornando ao menu principal...");
                    default -> System.out.println("Erro: Opção inválida! Digite um número entre 0 e 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite apenas números.");
                opcao = -1;
            }
        } while (opcao != 0);
    }

    /**
     * Cadastra um novo usuário no sistema
     * Solicita e valida todos os campos obrigatórios
     */
    private static void cadastrarUsuario() {
        System.out.println("\n--- CADASTRAR NOVO USUÁRIO ---");

        // Solicita dados obrigatórios com validação
        String nome = lerStringNaoVazia("Nome completo: ");
        String departamento = lerStringNaoVazia("Departamento: ");

        System.out.print("Matrícula (apenas números): ");
        int matricula = lerInteiro();

        // Adiciona o novo usuário à lista
        usuarios.add(new Usuario(nome, departamento, matricula));
        System.out.println("\nSUCESSO: Usuário cadastrado com sucesso!");
    }

    /**
     * Lista todos os usuários cadastrados no sistema
     * Mostra mensagem especial se não houver usuários
     */
    private static void listarUsuarios() {
        System.out.println("\n--- LISTA DE USUÁRIOS CADASTRADOS ---");

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado no sistema.");
            return;
        }

        // Exibe cada usuário com seu índice
        for (int i = 0; i < usuarios.size(); i++) {
            System.out.println("[" + i + "] " + usuarios.get(i));
        }
    }

    /**
     * Atualiza os dados de um usuário existente
     * Permite atualização parcial (campos podem ser mantidos pressionando Enter)
     */
    private static void atualizarUsuario() {
        listarUsuarios();
        if (usuarios.isEmpty()) return;

        System.out.print("\nDigite o índice do usuário a ser atualizado: ");
        int index = lerInteiro();

        // Valida o índice fornecido
        if (index < 0 || index >= usuarios.size()) {
            System.out.println("Erro: Índice inválido!");
            return;
        }

        Usuario usuario = usuarios.get(index);
        System.out.println("\nAtualizando usuário: " + usuario);

        // Atualiza nome (campo opcional - Enter mantém valor atual)
        System.out.print("\nNovo nome [" + usuario.getNome() + "]: ");
        String novoNome = scanner.nextLine().trim();
        if (!novoNome.isEmpty()) usuario.setNome(novoNome);

        // Atualiza departamento (campo opcional)
        System.out.print("Novo departamento [" + usuario.getDepartamento() + "]: ");
        String novoDepto = scanner.nextLine().trim();
        if (!novoDepto.isEmpty()) usuario.setDepartamento(novoDepto);

        // Atualiza matrícula (campo opcional com validação)
        System.out.print("Nova matrícula [" + usuario.getMatricula() + "]: ");
        String novaMatStr = scanner.nextLine().trim();
        if (!novaMatStr.isEmpty()) {
            try {
                usuario.setMatricula(Integer.parseInt(novaMatStr));
            } catch (NumberFormatException e) {
                System.out.println("Aviso: Matrícula não alterada - valor inválido.");
            }
        }

        System.out.println("\nSUCESSO: Usuário atualizado com sucesso!");
    }

    /**
     * Remove um usuário do sistema
     * Solicita confirmação antes da remoção definitiva
     */
    private static void removerUsuario() {
        listarUsuarios();
        if (usuarios.isEmpty()) return;

        System.out.print("\nDigite o índice do usuário a ser removido: ");
        int index = lerInteiro();

        if (index < 0 || index >= usuarios.size()) {
            System.out.println("Erro: Índice inválido!");
            return;
        }

        // Solicita confirmação
        System.out.println("\nUsuário selecionado para remoção:");
        System.out.println(usuarios.get(index));
        System.out.print("\nTem certeza que deseja remover este usuário? (S/N): ");
        String confirmacao = scanner.nextLine().trim().toUpperCase();

        if (confirmacao.equals("S")) {
            usuarios.remove(index);
            System.out.println("\nSUCESSO: Usuário removido com sucesso!");
        } else {
            System.out.println("\nOperação cancelada. O usuário não foi removido.");
        }
    }

    // ==================== GERENCIAMENTO DE EPIs ====================

    /**
     * Menu de gerenciamento de EPIs
     * Permite cadastrar, listar, atualizar e remover EPIs
     */
    private static void gerenciarEPIs() {
        int opcao;
        do {
            System.out.println("\n=== GERENCIAMENTO DE EPIs ===");
            System.out.println("1. Cadastrar Novo EPI");
            System.out.println("2. Listar Todos os EPIs");
            System.out.println("3. Atualizar EPI Existente");
            System.out.println("4. Remover EPI");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());

                switch (opcao) {
                    case 1 -> cadastrarEPI();
                    case 2 -> listarEPIs();
                    case 3 -> atualizarEPI();
                    case 4 -> removerEPI();
                    case 0 -> System.out.println("Retornando ao menu principal...");
                    default -> System.out.println("Erro: Opção inválida! Digite um número entre 0 e 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite apenas números.");
                opcao = -1;
            }
        } while (opcao != 0);
    }

    /**
     * Cadastra um novo EPI no sistema
     * Solicita e valida todos os campos obrigatórios
     */
    private static void cadastrarEPI() {
        System.out.println("\n--- CADASTRAR NOVO EPI ---");

        String nome = lerStringNaoVazia("Nome/descrição do EPI: ");

        System.out.print("Quantidade em estoque: ");
        int quantidade = lerInteiro();

        System.out.print("Data de validade (AAAA-MM-DD): ");
        String dataValidade = lerData();

        // Adiciona o novo EPI à lista
        epis.add(new EPI(nome, quantidade, dataValidade));
        System.out.println("\nSUCESSO: EPI cadastrado com sucesso!");
    }

    /**
     * Lista todos os EPIs cadastrados no sistema
     * Mostra mensagem especial se não houver EPIs
     */
    private static void listarEPIs() {
        System.out.println("\n--- LISTA DE EPIs CADASTRADOS ---");

        if (epis.isEmpty()) {
            System.out.println("Nenhum EPI cadastrado no sistema.");
            return;
        }

        // Exibe cada EPI com seu índice
        for (int i = 0; i < epis.size(); i++) {
            System.out.println("[" + i + "] " + epis.get(i));
        }
    }

    /**
     * Atualiza os dados de um EPI existente
     * Permite atualização parcial (campos podem ser mantidos pressionando Enter)
     */
    private static void atualizarEPI() {
        listarEPIs();
        if (epis.isEmpty()) return;

        System.out.print("\nDigite o índice do EPI a ser atualizado: ");
        int index = lerInteiro();

        if (index < 0 || index >= epis.size()) {
            System.out.println("Erro: Índice inválido!");
            return;
        }

        EPI epi = epis.get(index);
        System.out.println("\nAtualizando EPI: " + epi);

        // Atualiza nome (campo opcional)
        System.out.print("\nNovo nome [" + epi.getNome() + "]: ");
        String novoNome = scanner.nextLine().trim();
        if (!novoNome.isEmpty()) epi.setNome(novoNome);

        // Atualiza quantidade (campo opcional com validação)
        System.out.print("Nova quantidade [" + epi.getQuantidade() + "]: ");
        String novaQtdStr = scanner.nextLine().trim();
        if (!novaQtdStr.isEmpty()) {
            try {
                epi.setQuantidade(Integer.parseInt(novaQtdStr));
            } catch (NumberFormatException e) {
                System.out.println("Aviso: Quantidade não alterada - valor inválido.");
            }
        }

        // Atualiza data de validade (campo opcional com validação)
        System.out.print("Nova data de validade [" + epi.getDataValidade() + "]: ");
        String novaData = lerData();
        if (!novaData.isEmpty()) epi.setDataValidade(novaData);

        System.out.println("\nSUCESSO: EPI atualizado com sucesso!");
    }

    /**
     * Remove um EPI do sistema
     * Solicita confirmação antes da remoção definitiva
     */
    private static void removerEPI() {
        listarEPIs();
        if (epis.isEmpty()) return;

        System.out.print("\nDigite o índice do EPI a ser removido: ");
        int index = lerInteiro();

        if (index < 0 || index >= epis.size()) {
            System.out.println("Erro: Índice inválido!");
            return;
        }

        // Solicita confirmação
        System.out.println("\nEPI selecionado para remoção:");
        System.out.println(epis.get(index));
        System.out.print("\nTem certeza que deseja remover este EPI? (S/N): ");
        String confirmacao = scanner.nextLine().trim().toUpperCase();

        if (confirmacao.equals("S")) {
            epis.remove(index);
            System.out.println("\nSUCESSO: EPI removido com sucesso!");
        } else {
            System.out.println("\nOperação cancelada. O EPI não foi removido.");
        }
    }

    // ==================== GERENCIAMENTO DE EMPRÉSTIMOS ====================

    /**
     * Menu de gerenciamento de empréstimos
     * Permite cadastrar, listar, atualizar e remover empréstimos
     */
    private static void gerenciarEmprestimos() {
        int opcao;
        do {
            System.out.println("\n=== GERENCIAMENTO DE EMPRÉSTIMOS ===");
            System.out.println("1. Cadastrar Novo Empréstimo");
            System.out.println("2. Listar Todos os Empréstimos");
            System.out.println("3. Atualizar Empréstimo Existente");
            System.out.println("4. Remover Empréstimo");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());

                switch (opcao) {
                    case 1 -> cadastrarEmprestimo();
                    case 2 -> listarEmprestimos();
                    case 3 -> atualizarEmprestimo();
                    case 4 -> removerEmprestimo();
                    case 0 -> System.out.println("Retornando ao menu principal...");
                    default -> System.out.println("Erro: Opção inválida! Digite um número entre 0 e 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite apenas números.");
                opcao = -1;
            }
        } while (opcao != 0);
    }

    /**
     * Cadastra um novo empréstimo no sistema
     * Valida todas as entradas e relacionamentos
     */
    private static void cadastrarEmprestimo() {
        System.out.println("\n--- CADASTRAR NOVO EMPRÉSTIMO ---");

        // Verifica se existem usuários e EPIs cadastrados
        if (usuarios.isEmpty()) {
            System.out.println("Erro: Não há usuários cadastrados. Cadastre um usuário primeiro.");
            return;
        }
        if (epis.isEmpty()) {
            System.out.println("Erro: Não há EPIs cadastrados. Cadastre um EPI primeiro.");
            return;
        }

        // Seleção do usuário
        listarUsuarios();
        System.out.print("\nDigite o índice do usuário: ");
        int usuarioIndex = lerInteiro();

        if (usuarioIndex < 0 || usuarioIndex >= usuarios.size()) {
            System.out.println("Erro: Índice de usuário inválido!");
            return;
        }

        // Seleção do EPI
        listarEPIs();
        System.out.print("\nDigite o índice do EPI: ");
        int epiIndex = lerInteiro();

        if (epiIndex < 0 || epiIndex >= epis.size()) {
            System.out.println("Erro: Índice de EPI inválido!");
            return;
        }

        // Datas do empréstimo
        System.out.println("\nData do empréstimo:");
        LocalDate dataEmp = lerLocalDate();

        System.out.println("\nData de devolução prevista:");
        LocalDate dataDev = lerLocalDate();

        // Verifica se a data de devolução é posterior à data de empréstimo
        if (dataDev.isBefore(dataEmp)) {
            System.out.println("Erro: A data de devolução deve ser posterior à data de empréstimo!");
            return;
        }

        // Cria e armazena o novo empréstimo
        emprestimos.add(new Emprestimo(
                usuarios.get(usuarioIndex),
                epis.get(epiIndex),
                dataEmp,
                dataDev
        ));
        System.out.println("\nSUCESSO: Empréstimo cadastrado com sucesso!");
    }

    /**
     * Lista todos os empréstimos cadastrados no sistema
     * Mostra mensagem especial se não houver empréstimos
     */
    private static void listarEmprestimos() {
        System.out.println("\n--- LISTA DE EMPRÉSTIMOS ---");

        if (emprestimos.isEmpty()) {
            System.out.println("Nenhum empréstimo cadastrado no sistema.");
            return;
        }

        // Exibe cada empréstimo com seu índice
        for (int i = 0; i < emprestimos.size(); i++) {
            System.out.println("[" + i + "] " + emprestimos.get(i));
        }
    }

    /**
     * Atualiza os dados de um empréstimo existente
     * Permite atualização parcial (campos podem ser mantidos pressionando Enter)
     */
    private static void atualizarEmprestimo() {
        listarEmprestimos();
        if (emprestimos.isEmpty()) return;

        System.out.print("\nDigite o índice do empréstimo a ser atualizado: ");
        int index = lerInteiro();

        if (index < 0 || index >= emprestimos.size()) {
            System.out.println("Erro: Índice inválido!");
            return;
        }

        Emprestimo emp = emprestimos.get(index);
        System.out.println("\nAtualizando empréstimo: " + emp);

        // Atualização do usuário (campo opcional)
        listarUsuarios();
        System.out.print("\nNovo índice do usuário [" + usuarios.indexOf(emp.getUsuario()) + "]: ");
        String userStr = scanner.nextLine().trim();
        if (!userStr.isEmpty()) {
            try {
                int userIndex = Integer.parseInt(userStr);
                if (userIndex >= 0 && userIndex < usuarios.size()) {
                    emp.setUsuario(usuarios.get(userIndex));
                } else {
                    System.out.println("Aviso: Índice de usuário inválido. Usuário não alterado.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Aviso: Valor inválido. Usuário não alterado.");
            }
        }

        // Atualização do EPI (campo opcional)
        listarEPIs();
        System.out.print("Novo índice do EPI [" + epis.indexOf(emp.getEpi()) + "]: ");
        String epiStr = scanner.nextLine().trim();
        if (!epiStr.isEmpty()) {
            try {
                int epiIndex = Integer.parseInt(epiStr);
                if (epiIndex >= 0 && epiIndex < epis.size()) {
                    emp.setEpi(epis.get(epiIndex));
                } else {
                    System.out.println("Aviso: Índice de EPI inválido. EPI não alterado.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Aviso: Valor inválido. EPI não alterado.");
            }
        }

        // Atualização da data de empréstimo (campo opcional)
        System.out.print("Nova data de empréstimo [" + emp.getDataEmprestimo() + "]: ");
        String dataEmpStr = scanner.nextLine().trim();
        if (!dataEmpStr.isEmpty()) {
            try {
                LocalDate novaDataEmp = LocalDate.parse(dataEmpStr);
                emp.setDataEmprestimo(novaDataEmp);
            } catch (DateTimeParseException e) {
                System.out.println("Aviso: Data inválida. Data de empréstimo não alterada.");
            }
        }

        // Atualização da data de devolução (campo opcional)
        System.out.print("Nova data de devolução [" + emp.getDataDevolucaoPrevista() + "]: ");
        String dataDevStr = scanner.nextLine().trim();
        if (!dataDevStr.isEmpty()) {
            try {
                LocalDate novaDataDev = LocalDate.parse(dataDevStr);

                // Verifica se a nova data de devolução é posterior à data de empréstimo
                if (novaDataDev.isBefore(emp.getDataEmprestimo())) {
                    System.out.println("Erro: A data de devolução deve ser posterior à data de empréstimo!");
                } else {
                    emp.setDataDevolucaoPrevista(novaDataDev);
                }
            } catch (DateTimeParseException e) {
                System.out.println("Aviso: Data inválida. Data de devolução não alterada.");
            }
        }

        System.out.println("\nSUCESSO: Empréstimo atualizado com sucesso!");
    }

    /**
     * Remove um empréstimo do sistema
     * Solicita confirmação antes da remoção definitiva
     */
    private static void removerEmprestimo() {
        listarEmprestimos();
        if (emprestimos.isEmpty()) return;

        System.out.print("\nDigite o índice do empréstimo a ser removido: ");
        int index = lerInteiro();

        if (index < 0 || index >= emprestimos.size()) {
            System.out.println("Erro: Índice inválido!");
            return;
        }

        // Solicita confirmação
        System.out.println("\nEmpréstimo selecionado para remoção:");
        System.out.println(emprestimos.get(index));
        System.out.print("\nTem certeza que deseja remover este empréstimo? (S/N): ");
        String confirmacao = scanner.nextLine().trim().toUpperCase();

        if (confirmacao.equals("S")) {
            emprestimos.remove(index);
            System.out.println("\nSUCESSO: Empréstimo removido com sucesso!");
        } else {
            System.out.println("\nOperação cancelada. O empréstimo não foi removido.");
        }
    }

    // ==================== GERENCIAMENTO DE DEVOLUÇÕES ====================

    /**
     * Menu de gerenciamento de devoluções
     * Permite cadastrar, listar, atualizar e remover devoluções
     */
    private static void gerenciarDevolucoes() {
        int opcao;
        do {
            System.out.println("\n=== GERENCIAMENTO DE DEVOLUÇÕES ===");
            System.out.println("1. Cadastrar Nova Devolução");
            System.out.println("2. Listar Todas as Devoluções");
            System.out.println("3. Atualizar Devolução Existente");
            System.out.println("4. Remover Devolução");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());

                switch (opcao) {
                    case 1 -> cadastrarDevolucao();
                    case 2 -> listarDevolucoes();
                    case 3 -> atualizarDevolucao();
                    case 4 -> removerDevolucao();
                    case 0 -> System.out.println("Retornando ao menu principal...");
                    default -> System.out.println("Erro: Opção inválida! Digite um número entre 0 e 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite apenas números.");
                opcao = -1;
            }
        } while (opcao != 0);
    }

    /**
     * Cadastra uma nova devolução no sistema
     * Valida todas as entradas e relacionamentos
     */
    private static void cadastrarDevolucao() {
        System.out.println("\n--- CADASTRAR NOVA DEVOLUÇÃO ---");

        // Verifica se existem empréstimos cadastrados
        if (emprestimos.isEmpty()) {
            System.out.println("Erro: Não há empréstimos cadastrados. Cadastre um empréstimo primeiro.");
            return;
        }

        // Seleção do empréstimo
        listarEmprestimos();
        System.out.print("\nDigite o índice do empréstimo: ");
        int empIndex = lerInteiro();

        if (empIndex < 0 || empIndex >= emprestimos.size()) {
            System.out.println("Erro: Índice de empréstimo inválido!");
            return;
        }

        // Data da devolução
        System.out.println("\nData da devolução:");
        LocalDate dataDev = lerLocalDate();

        // Verifica se a data de devolução é posterior à data de empréstimo
        Emprestimo emp = emprestimos.get(empIndex);
        if (dataDev.isBefore(emp.getDataEmprestimo())) {
            System.out.println("Erro: A data de devolução deve ser posterior à data de empréstimo!");
            return;
        }

        // Observação (campo opcional)
        System.out.print("Observações (opcional): ");
        String obs = scanner.nextLine().trim();

        // Cria e armazena a nova devolução
        devolucoes.add(new Devolucao(emp, dataDev, obs));
        System.out.println("\nSUCESSO: Devolução cadastrada com sucesso!");
    }

    /**
     * Lista todas as devoluções cadastradas no sistema
     * Mostra mensagem especial se não houver devoluções
     */
    private static void listarDevolucoes() {
        System.out.println("\n--- LISTA DE DEVOLUÇÕES ---");

        if (devolucoes.isEmpty()) {
            System.out.println("Nenhuma devolução cadastrada no sistema.");
            return;
        }

        // Exibe cada devolução com seu índice
        for (int i = 0; i < devolucoes.size(); i++) {
            System.out.println("[" + i + "] " + devolucoes.get(i));
        }
    }

    /**
     * Atualiza os dados de uma devolução existente
     * Permite atualização parcial (campos podem ser mantidos pressionando Enter)
     */
    private static void atualizarDevolucao() {
        listarDevolucoes();
        if (devolucoes.isEmpty()) return;

        System.out.print("\nDigite o índice da devolução a ser atualizada: ");
        int index = lerInteiro();

        if (index < 0 || index >= devolucoes.size()) {
            System.out.println("Erro: Índice inválido!");
            return;
        }

        Devolucao dev = devolucoes.get(index);
        System.out.println("\nAtualizando devolução: " + dev);

        // Atualização do empréstimo relacionado (campo opcional)
        listarEmprestimos();
        System.out.print("\nNovo índice do empréstimo [" + emprestimos.indexOf(dev.getEmprestimo()) + "]: ");
        String empStr = scanner.nextLine().trim();
        if (!empStr.isEmpty()) {
            try {
                int empIndex = Integer.parseInt(empStr);
                if (empIndex >= 0 && empIndex < emprestimos.size()) {
                    dev.setEmprestimo(emprestimos.get(empIndex));
                } else {
                    System.out.println("Aviso: Índice de empréstimo inválido. Empréstimo não alterado.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Aviso: Valor inválido. Empréstimo não alterado.");
            }
        }

        // Atualização da data (campo opcional)
        System.out.print("Nova data de devolução [" + dev.getDataDevolucao() + "]: ");
        String dataDevStr = scanner.nextLine().trim();
        if (!dataDevStr.isEmpty()) {
            try {
                LocalDate novaDataDev = LocalDate.parse(dataDevStr);

                // Verifica se a nova data é posterior à data de empréstimo
                if (novaDataDev.isBefore(dev.getEmprestimo().getDataEmprestimo())) {
                    System.out.println("Erro: A data de devolução deve ser posterior à data de empréstimo!");
                } else {
                    dev.setDataDevolucao(novaDataDev);
                }
            } catch (DateTimeParseException e) {
                System.out.println("Aviso: Data inválida. Data de devolução não alterada.");
            }
        }

        // Atualização da observação (campo opcional)
        System.out.print("Nova observação [" + dev.getObservacao() + "]: ");
        String novaObs = scanner.nextLine().trim();
        if (!novaObs.isEmpty()) {
            dev.setObservacao(novaObs);
        }

        System.out.println("\nSUCESSO: Devolução atualizada com sucesso!");
    }

    /**
     * Remove uma devolução do sistema
     * Solicita confirmação antes da remoção definitiva
     */
    private static void removerDevolucao() {
        listarDevolucoes();
        if (devolucoes.isEmpty()) return;

        System.out.print("\nDigite o índice da devolução a ser removida: ");
        int index = lerInteiro();

        if (index < 0 || index >= devolucoes.size()) {
            System.out.println("Erro: Índice inválido!");
            return;
        }

        // Solicita confirmação
        System.out.println("\nDevolução selecionada para remoção:");
        System.out.println(devolucoes.get(index));
        System.out.print("\nTem certeza que deseja remover esta devolução? (S/N): ");
        String confirmacao = scanner.nextLine().trim().toUpperCase();

        if (confirmacao.equals("S")) {
            devolucoes.remove(index);
            System.out.println("\nSUCESSO: Devolução removida com sucesso!");
        } else {
            System.out.println("\nOperação cancelada. A devolução não foi removida.");
        }
    }
}

// ==================== CLASSES DE MODELO ====================

/**
 * Classe que representa um usuário do sistema
 */
class Usuario {
    private String nome;
    private String departamento;
    private int matricula;

    /**
     * Construtor da classe Usuario
     * @param nome Nome completo do usuário
     * @param departamento Departamento ao qual o usuário pertence
     * @param matricula Número de matrícula do usuário
     */
    public Usuario(String nome, String departamento, int matricula) {
        this.nome = nome;
        this.departamento = departamento;
        this.matricula = matricula;
    }

    // Métodos getters e setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public int getMatricula() { return matricula; }
    public void setMatricula(int matricula) { this.matricula = matricula; }

    /**
     * Retorna uma representação em string do usuário
     * @return String formatada com os dados do usuário
     */
    @Override
    public String toString() {
        return String.format("Usuário: %-20s | Depto: %-15s | Matrícula: %06d",
                nome, departamento, matricula);
    }
}

/**
 * Classe que representa um Equipamento de Proteção Individual (EPI)
 */
class EPI {
    private String nome;
    private int quantidade;
    private String dataValidade;

    /**
     * Construtor da classe EPI
     * @param nome Nome/descrição do EPI
     * @param quantidade Quantidade disponível em estoque
     * @param dataValidade Data de validade no formato AAAA-MM-DD
     */
    public EPI(String nome, int quantidade, String dataValidade) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.dataValidade = dataValidade;
    }

    // Métodos getters e setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public String getDataValidade() { return dataValidade; }
    public void setDataValidade(String dataValidade) { this.dataValidade = dataValidade; }

    /**
     * Retorna uma representação em string do EPI
     * @return String formatada com os dados do EPI
     */
    @Override
    public String toString() {
        return String.format("EPI: %-20s | Qtd: %3d | Validade: %10s",
                nome, quantidade, dataValidade);
    }
}

/**
 * Classe que representa um empréstimo de EPI para um usuário
 */
class Emprestimo {
    private Usuario usuario;
    private EPI epi;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;

    /**
     * Construtor da classe Emprestimo
     * @param usuario Usuário que realizou o empréstimo
     * @param epi EPI que foi emprestado
     * @param dataEmprestimo Data em que o empréstimo foi realizado
     * @param dataDevolucaoPrevista Data prevista para devolução do EPI
     */
    public Emprestimo(Usuario usuario, EPI epi, LocalDate dataEmprestimo, LocalDate dataDevolucaoPrevista) {
        this.usuario = usuario;
        this.epi = epi;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    // Métodos getters e setters
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public EPI getEpi() { return epi; }
    public void setEpi(EPI epi) { this.epi = epi; }

    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(LocalDate dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }

    public LocalDate getDataDevolucaoPrevista() { return dataDevolucaoPrevista; }
    public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) { this.dataDevolucaoPrevista = dataDevolucaoPrevista; }

    /**
     * Retorna uma representação em string do empréstimo
     * @return String formatada com os dados do empréstimo
     */
    @Override
    public String toString() {
        return String.format("Emp: %-15s | EPI: %-15s | Data Emp: %s | Data Dev: %s",
                usuario.getNome(), epi.getNome(),
                dataEmprestimo, dataDevolucaoPrevista);
    }
}

/**
 * Classe que representa a devolução de um EPI emprestado
 */
class Devolucao {
    private Emprestimo emprestimo;
    private LocalDate dataDevolucao;
    private String observacao;

    /**
     * Construtor da classe Devolucao
     * @param emprestimo Empréstimo relacionado à devolução
     * @param dataDevolucao Data em que o EPI foi devolvido
     * @param observacao Observações sobre a devolução (opcional)
     */
    public Devolucao(Emprestimo emprestimo, LocalDate dataDevolucao, String observacao) {
        this.emprestimo = emprestimo;
        this.dataDevolucao = dataDevolucao;
        this.observacao = observacao;
    }

    // Métodos getters e setters
    public Emprestimo getEmprestimo() { return emprestimo; }
    public void setEmprestimo(Emprestimo emprestimo) { this.emprestimo = emprestimo; }

    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public void setDataDevolucao(LocalDate dataDevolucao) { this.dataDevolucao = dataDevolucao; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    /**
     * Retorna uma representação em string da devolução
     * @return String formatada com os dados da devolução
     */
    @Override
    public String toString() {
        return String.format("Dev: %-15s | EPI: %-15s | Data Dev: %s | Obs: %s",
                emprestimo.getUsuario().getNome(), emprestimo.getEpi().getNome(),
                dataDevolucao, observacao.isEmpty() ? "Nenhuma" : observacao);
    }
}