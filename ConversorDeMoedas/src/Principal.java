import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Saudação inicial com emoji
        System.out.print("👋 Olá! Qual é o seu nome? ");
        String nome = scanner.nextLine();

        // Validação para evitar que o nome seja apenas números
        while (nome.matches("\\d+")) {
            System.out.print("Por favor, insira um nome válido (não só números): ");
            nome = scanner.nextLine();
        }

        System.out.println("\nBem-vindo(a), " + nome + "! Vamos converter moedas juntos! 💰✨\n");

        Menu menu = new Menu(scanner, nome);
        menu.iniciar();

        scanner.close();
    }
}

