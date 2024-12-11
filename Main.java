import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bem-vindo ao Jogo de Dados!");
        game.exibirRanking();

        System.out.println("Digite o número de jogadores (até 11): ");
        int numJogadores = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        for (int i = 0; i < numJogadores; i++) {
            System.out.println("Jogador " + (i + 1) + ":");
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("Aposta (entre 1 e 12): ");
            int aposta = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            try {
                game.adicionarPlayer(nome, aposta);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                i--; // Tenta novamente
            }
        }

        game.iniciarJogo();
        scanner.close();
    }
}
