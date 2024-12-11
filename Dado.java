import java.util.Random;

public class Dado {
    private int numeroFace;

    public int lancarDado() {
        Random random = new Random();
        numeroFace = random.nextInt(6) + 1; // Gera número entre 1 e 6
        return numeroFace;
    }
}
