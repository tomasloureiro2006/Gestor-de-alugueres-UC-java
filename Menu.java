import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Interface de consola e menu principal da aplicação.
 */
public class Menu {
    private List<Utilizador> utilizadores;
    private List<Veiculo> veiculos;
    private List<Aluguer> alugueres;
    private Scanner scanner;
    private static final String FICHEIRO_UTILIZADORES = "utilizadores.txt";
    private static final String FICHEIRO_VEICULOS = "veiculos.txt";
    private static final String FICHEIRO_ALUGUERES = "alugueres.obj";

    /**
     * Constrói o menu e prepara as listas e o scanner.
     */
    public Menu() {
        this.utilizadores = new ArrayList<>();
        this.veiculos = new ArrayList<>();
        this.alugueres = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Arranca a aplicação: carrega dados e mostra o menu.
     */
    public void iniciar() {
        apresentarCabecalho();
        carregarDados();
        mostrarMenuPrincipal();
    }

    /**
     * Mostra o cabeçalho na consola.
     */
    private void apresentarCabecalho() {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║           MOBILIDADE UC            ║");
        System.out.println("║  Sistema de Gestão de Alugueres   ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.println();
    }

    /**
     * Carrega utilizadores, veículos e alugueres dos ficheiros.
     */
    private void carregarDados() {
        System.out.println("A carregar dados...\n");
        utilizadores = GestorDados.carregarUtilizadores(FICHEIRO_UTILIZADORES);
        veiculos = GestorDados.carregarVeiculos(FICHEIRO_VEICULOS);
        alugueres = GestorDados.carregarAlugueres(FICHEIRO_ALUGUERES);
        
        System.out.println("Dados carregados com sucesso:");
        System.out.println("  - " + utilizadores.size() + " utilizadores");
        System.out.println("  - " + veiculos.size() + " veículos");
        System.out.println("  - " + alugueres.size() + " alugueres");
        System.out.println();
    }

    /**
     * Loop principal do menu.
     */
    private void mostrarMenuPrincipal() {
        while (true) {
            System.out.println("\n\n╔════════════════════════════════════╗");
            System.out.println("║         MENU PRINCIPAL             ║");
            System.out.println("╚════════════════════════════════════╝");
            System.out.println("1. Criar novo aluguer");
            System.out.println("2. Listar todos os alugueres");
            System.out.println("3. Listar utilizadores");
            System.out.println("4. Listar veículos");
            System.out.println("0. Terminar");
            System.out.println("────────────────────────────────────");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        criarAluguer();
                        break;
                    case 2:
                        listarAlugueres();
                        break;
                    case 3:
                        listarUtilizadores();
                        break;
                    case 4:
                        listarVeiculos();
                        break;
                    case 0:
                        terminar();
                        return;
                    default:
                        System.out.println("\nOpção inválida! Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nEntrada inválida! Por favor, insira um número.");
                scanner.nextLine();
            }
        }
    }

    /**
     * Pede dados e cria um novo aluguer.
     */
    private void criarAluguer() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║      CRIAR NOVO ALUGUER            ║");
        System.out.println("╚════════════════════════════════════╝");

        System.out.print("\nNúmero mecanográfico do utilizador: ");
        String numeroMec = scanner.nextLine().trim();
        
        Utilizador utilizador = procurarUtilizador(numeroMec);
        if (utilizador == null) {
            System.out.println("Utilizador não encontrado!");
            return;
        }
        System.out.println("Utilizador: " + utilizador);

        System.out.print("\nID do veículo (4 dígitos): ");
        int idVeiculo;
        try {
            idVeiculo = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("ID inválido!");
            scanner.nextLine();
            return;
        }

        Veiculo veiculo = procurarVeiculo(idVeiculo);
        if (veiculo == null) {
            System.out.println("Veículo não encontrado!");
            return;
        }
        System.out.println("Veículo: " + veiculo.getId() + " (" + veiculo.getTipoVeiculo() + ")");

        LocalDateTime dataInicio = pedirDataHora("\nData e hora de INÍCIO");
        if (dataInicio == null) {
            System.out.println("Data/hora inválida!");
            return;
        }

        LocalDateTime dataFim = pedirDataHora("\nData e hora de FIM");
        if (dataFim == null) {
            System.out.println("Data/hora inválida!");
            return;
        }

        if (dataFim.isBefore(dataInicio)) {
            System.out.println("A data de fim não pode ser anterior à data de início!");
            return;
        }

        Aluguer aluguer = new Aluguer(utilizador, veiculo, dataInicio, dataFim);

        adicionarServicos(aluguer);

        alugueres.add(aluguer);
        mostrarResumoAluguer(aluguer);
    }

    /**
     * Procura utilizador pelo número mecanográfico.
     * @param numeroMecanografico número mecanográfico
     * @return utilizador ou null
     */
    private Utilizador procurarUtilizador(String numeroMecanografico) {
        for (Utilizador u : utilizadores) {
            if (u.getNumeroMecanografico().equals(numeroMecanografico)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Procura veículo pelo ID.
     * @param id identificador
     * @return veículo ou null
     */
    private Veiculo procurarVeiculo(int id) {
        for (Veiculo v : veiculos) {
            if (v.getId() == id) {
                return v;
            }
        }
        return null;
    }

    /**
     * Pede data e hora em dd/MM/yyyy HH:mm.
     * @param mensagem texto de prompt
     * @return data/hora ou null
     */
    private LocalDateTime pedirDataHora(String mensagem) {
        System.out.println(mensagem);
        System.out.print("Formato (dd/MM/yyyy HH:mm): ");
        String input = scanner.nextLine().trim();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        try {
            return LocalDateTime.parse(input, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Pede e adiciona serviços adicionais.
     * @param aluguer aluguer em edição
     */
    private void adicionarServicos(Aluguer aluguer) {
        System.out.println("\nDeseja adicionar serviços adicionais?");
        System.out.println("1. Capacete (5 euros/dia)");
        System.out.println("2. Luz (2,5 euros/dia)");
        System.out.println("0. Não adicionar");
        
        while (true) {
            System.out.print("\nEscolha (0 para terminar): ");
            try {
                int opcao = scanner.nextInt();
                scanner.nextLine();

                if (opcao == 0) {
                    break;
                } else if (opcao == 1) {
                    aluguer.adicionarServico(new ServicoAdicional(ServicoAdicional.CAPACETE));
                    System.out.println("Capacete adicionado");
                } else if (opcao == 2) {
                    aluguer.adicionarServico(new ServicoAdicional(ServicoAdicional.LUZ));
                    System.out.println("Luz adicionada");
                } else {
                    System.out.println("Opção inválida!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida!");
                scanner.nextLine();
            }
        }
    }

    /**
     * Mostra o resumo de um aluguer.
     * @param aluguer aluguer a apresentar
     */
    private void mostrarResumoAluguer(Aluguer aluguer) {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║      RESUMO DO ALUGUER             ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.println("Utilizador: " + aluguer.getUtilizador().getNumeroMecanografico());
        System.out.println("Veículo: " + aluguer.getVeiculo().getId()+"(" + aluguer.getVeiculo().getTipoVeiculo() + ")");
        System.out.println("Duração: " + String.format("%.2f", aluguer.calcularDuracaoHoras()) + " horas");
        
        if (!aluguer.getServicosAdicionais().isEmpty()) {
            System.out.println("Serviços adicionais:");
            for (ServicoAdicional servico : aluguer.getServicosAdicionais()) {
                System.out.println("  - " + servico.getTipo());
            }
        }
        
        System.out.println("\nCUSTO TOTAL: " + String.format("%.2f", aluguer.calcularCustoTotal()) + " euros");
        System.out.println("Aluguer criado com sucesso!");
    }

    /**
     * Lista alugueres e total acumulado.
     */
    private void listarAlugueres() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║      LISTA DE ALUGUERES            ║");
        System.out.println("╚════════════════════════════════════╝");

        if (alugueres.isEmpty()) {
            System.out.println("\nNão existem alugueres registados.");
            return;
        }

        double valorTotal = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (int i = 0; i < alugueres.size(); i++) {
            Aluguer a = alugueres.get(i);
            System.out.println("\n─── Aluguer #" + (i + 1) + " ───");
            System.out.println("Utilizador: " + a.getUtilizador().getNumeroMecanografico() + 
                             " (" + a.getUtilizador().getTipo() + ")");
            System.out.println("Veículo: " + a.getVeiculo());
            System.out.println("Início: " + a.getDataHoraInicio().format(formatter));
            System.out.println("Fim: " + a.getDataHoraFim().format(formatter));
            System.out.println("Duração: " + String.format("%.2f", a.calcularDuracaoHoras()) + " horas");
            
            if (!a.getServicosAdicionais().isEmpty()) {
                System.out.print("Serviços: ");
                for (ServicoAdicional s : a.getServicosAdicionais()) {
                    System.out.print(s.getTipo() + " ");
                }
                System.out.println();
            }
            
            double custo = a.calcularCustoTotal();
            System.out.println("Custo: " + String.format("%.2f", custo) + " euros");
            valorTotal += custo;
        }

        System.out.println("\n════════════════════════════════════");
        System.out.println("VALOR TOTAL: " + String.format("%.2f", valorTotal) + " euros");
    }

    /**
     * Lista utilizadores.
     */
    private void listarUtilizadores() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║      LISTA DE UTILIZADORES         ║");
        System.out.println("╚════════════════════════════════════╝");

        if (utilizadores.isEmpty()) {
            System.out.println("\nNão existem utilizadores registados.");
            return;
        }

        for (int i = 0; i < utilizadores.size(); i++) {
            System.out.println((i + 1) + ". " + utilizadores.get(i));
        }
    }

    /**
     * Lista veículos.
     */
    private void listarVeiculos() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║      LISTA DE VEÍCULOS             ║");
        System.out.println("╚════════════════════════════════════╝");

        if (veiculos.isEmpty()) {
            System.out.println("\nNão existem veículos registados.");
            return;
        }

        for (int i = 0; i < veiculos.size(); i++) {
            System.out.println((i + 1) + ". " + veiculos.get(i));
        }
    }

    /**
     * Guarda alugueres e fecha a aplicação.
     */
    private void terminar() {
        System.out.println("\nA guardar alugueres...");
        GestorDados.guardarAlugueres(FICHEIRO_ALUGUERES, alugueres);
        System.out.println("Alugueres guardados com sucesso!");
        
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║  Obrigado por usar Mobilidade UC!  ║");
        System.out.println("╚════════════════════════════════════╝");
        
        scanner.close();
    }
}
