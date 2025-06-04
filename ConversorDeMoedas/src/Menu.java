import java.util.Scanner;

public class Menu {
    private Scanner scanner;
    private String nome;
    private MoedaService moedaService;
    private Historico historico;

    // Construtor recebendo Scanner e nome do usuário
    public Menu(Scanner scanner, String nome) {
        this.scanner = scanner;
        this.nome = nome;
        this.moedaService = new MoedaService();
        this.historico = new Historico();
    }

    // Método para iniciar o menu
    public void iniciar() {
        while (true) {

            System.out.println("🪙 Escolha a moeda de origem:");
            System.out.println("1️⃣ - 💵 USD (Dólar Americano)");
            System.out.println("2️⃣ - 💶 EUR (Euro)");
            System.out.println("3️⃣ - 💷 GBP (Libra Esterlina)");
            System.out.println("4️⃣ - 💰 ARS (Peso Argentino)");
            System.out.println("5️⃣ - 🪙 BRL (Real Brasileiro)");
            System.out.println("6️⃣ - 💴 JPY (Iene Japonês)");
            System.out.println("0️⃣ - ❌ Sair");
            System.out.print("👉 Digite a opção: ");


            String opcaoOrigem = scanner.nextLine();

            if (!validarOpcao(opcaoOrigem, 0, 6)) {
                System.out.println("❌ Opção inválida! Por favor, escolha um número válido.");
                continue;
            }

            if (opcaoOrigem.equals("0")) {
                System.out.println("👋 Até mais, " + nome + "! Obrigado por usar o Conversor de Moedas.");
                break;
            }

            String moedaOrigem = obterCodigoMoeda(opcaoOrigem);

            System.out.println("🪙 Escolha a moeda de destino :");
            System.out.println("1️⃣ - 💵 USD (Dólar Americano)");
            System.out.println("2️⃣ - 💶 EUR (Euro)");
            System.out.println("3️⃣ - 💷 GBP (Libra Esterlina)");
            System.out.println("4️⃣ - 💰 ARS (Peso Argentino)");
            System.out.println("5️⃣ - 🪙 BRL (Real Brasileiro)");
            System.out.println("6️⃣ - 💴 JPY (Iene Japonês)");
            System.out.println("0️⃣ - ❌ Sair");
            System.out.print("👉 Digite a opção: ");


            String opcaoDestino = scanner.nextLine();

            if (!validarOpcao(opcaoDestino, 1, 6)) {
                System.out.println("❌ Opção inválida! Por favor, escolha um número válido.");
                continue;
            }

            String moedaDestino = obterCodigoMoeda(opcaoDestino);

            double valor = solicitarValor();

            // Faz a conversão chamando o serviço
            Conversao resultado = moedaService.converter(moedaOrigem, moedaDestino, valor);

            if (resultado == null) {
                System.out.println("❌ Não foi possível realizar a conversão. Tente novamente.");
                continue;
            }

            System.out.printf("💸 %.2f %s = %.2f %s%n", valor, moedaOrigem, resultado.valorConvertido(), moedaDestino);

            System.out.print("Deseja salvar essa conversão no histórico? (sim/não): ");
            String salvar = scanner.nextLine().trim().toLowerCase();

            if (salvar.equals("sim")) {
                historico.adicionarRegistro(moedaOrigem, moedaDestino, valor, resultado.valorConvertido());
                System.out.println("✅ Conversão salva no histórico!\n");
            } else {
                System.out.println("👍 Conversão não salva.\n");
            }

            System.out.println("-----------------------------------------\n");
        }

        // Quando sair, pergunta se quer gerar arquivo HTML
        if (historico.temRegistros()) {
            System.out.print("Deseja gerar o arquivo HTML com o histórico de conversões? (sim/não): ");
            String gerar = scanner.nextLine().trim().toLowerCase();

            if (gerar.equals("sim")) {
                boolean sucesso = historico.gerarArquivoHtml();
                if (sucesso) {
                    System.out.println("📁 Arquivo HTML criado com sucesso: historico-conversoes.html");
                } else {
                    System.out.println("❌ Falha ao criar arquivo HTML.");
                }
            }
        }
    }

    // Valida se a opção é um número dentro do intervalo permitido
    private boolean validarOpcao(String entrada, int min, int max) {
        try {
            int opc = Integer.parseInt(entrada);
            return opc >= min && opc <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Retorna o código da moeda pelo número da opção
    private String obterCodigoMoeda(String opcao) {
        return switch (opcao) {
            case "1" -> "USD";
            case "2" -> "EUR";
            case "3" -> "GBP";
            case "4" -> "ARS";
            case "5" -> "BRL";
            case "6" -> "JPY";
            default -> "";
        };
    }

    // Solicita o valor a ser convertido, com validação
    private double solicitarValor() {
        while (true) {
            System.out.print("Digite o valor a ser convertido: ");
            String entrada = scanner.nextLine().replace(",", ".");

            try {
                double valor = Double.parseDouble(entrada);
                if (valor <= 0) {
                    System.out.println("❌ Por favor, digite um valor positivo.");
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("❌ Valor inválido! Digite um número válido (ex: 123.45).");
            }
        }
    }
}
