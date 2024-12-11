import java.util.*;
import java.io.*;

public class Game {
    private List<Player> listaPlayers;
    private Dado dado1;
    private Dado dado2;

    public Game() {
        listaPlayers = new ArrayList<>();
        dado1 = new Dado();
        dado2 = new Dado();
    }

    public void adicionarPlayer(String nome, int aposta) throws Exception {
        // Verifica duplicidade de apostas
        for (Player player : listaPlayers) {
            if (player.getAposta() == aposta) {
                throw new Exception("Já existe um jogador com essa aposta!");
            }
        }
        listaPlayers.add(new Player(nome, aposta));
    }

    public void iniciarJogo() {
        int resultado = dado1.lancarDado() + dado2.lancarDado();
        System.out.println("Número sorteado: " + resultado);

        // Verifica vencedores
        Player vencedor = null;
        for (Player player : listaPlayers) {
            if (player.getAposta() == resultado) {
                vencedor = player;
                break;
            }
        }

        if (vencedor != null) {
            System.out.println("Vencedor: " + vencedor.getNome());
            vencedor.registrarVitoria();
            atualizarRanking(vencedor);
        } else {
            System.out.println("A máquina venceu!");
        }
    }

    private void atualizarRanking(Player vencedor) {
        try (BufferedReader reader = new BufferedReader(new FileReader("ranking.txt"));
             PrintWriter writer = new PrintWriter(new FileWriter("ranking_temp.txt"))) {

            Map<String, Integer> ranking = new HashMap<>();
            String linha;

            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                ranking.put(partes[0], Integer.parseInt(partes[1]));
            }

            ranking.put(vencedor.getNome(), vencedor.getVitorias());

            for (Map.Entry<String, Integer> entry : ranking.entrySet()) {
                writer.println(entry.getKey() + "," + entry.getValue());
            }

            File rankingFile = new File("ranking.txt");
            rankingFile.delete();
            new File("ranking_temp.txt").renameTo(rankingFile);

        } catch (IOException e) {
            System.out.println("Erro ao atualizar ranking: " + e.getMessage());
        }
    }

    public void exibirRanking() {
        try (BufferedReader reader = new BufferedReader(new FileReader("ranking_temp.txt"))) {
            List<String> topFive = new ArrayList<>();
            String linha;

            while ((linha = reader.readLine()) != null) {
                topFive.add(linha);
            }

            topFive.sort((a, b) -> Integer.parseInt(b.split(",")[1]) - Integer.parseInt(a.split(",")[1]));
            System.out.println("TOP FIVE:");
            for (int i = 0; i < Math.min(5, topFive.size()); i++) {
                System.out.println((i + 1) + ". " + topFive.get(i));
            }

        } catch (IOException e) {
            System.out.println("Erro ao exibir ranking: " + e.getMessage());
        }
    }
}
