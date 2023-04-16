import java.util.Random;

public class Aviao {
    private Aviao next;
    private int id;
    // combustivel 1=1 20=20
    private int combustivel;
    private String nome;
    private int tempo_esperando = 0;

    public Aviao(int id, int combustivel, Aviao next) {
        Random gerador_random = new Random();
        String[] nomes_possiveis = {"LATAM", "GOL", "AZUL", "PASSAREDO", "TAM", "EMIRATES", "AIR FRANCE", "QATAR", "LUFTHANSA"};
        this.nome = nomes_possiveis[gerador_random.nextInt(0, 9)];
        this.next = next;
        this.id = id;
        this.combustivel = combustivel;
    }

    public Aviao getNext() {
        return next;
    }

    public int tempo_restante() {
        return (combustivel);
    }

    public void setNext(Aviao next) {
        this.next = next;
    }

    public int getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(int combustivel) {
        this.combustivel = combustivel;
    }

    public int getTempo_esperando() {
        return tempo_esperando;
    }

    public void setTempo_esperando(int tempo_esperando) {
        this.tempo_esperando = tempo_esperando;
    }

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String COLOR = "";
        String RESET = "";
        if (this.getCombustivel() <= 1) {
            COLOR = "\u001B[31m";
            RESET = "\u001B[0m";
        }
        String idStr = "ID: " + this.getid();
        String nomeStr = this.nome;
        String combustivelStr = "Combustivel: " + this.getCombustivel();
        String tempoStr = "Tempo com Combustivel: " + this.tempo_restante();

        int idWidth = Math.max(idStr.length(), 5);
        int nomeWidth = Math.max(nomeStr.length(), 20);
        int combustivelWidth = Math.max(combustivelStr.length(), 12);
        int tempoWidth = Math.max(tempoStr.length(), 25);

        idStr = String.format("%-" + idWidth + "s", idStr);
        nomeStr = String.format("%-" + nomeWidth + "s", nomeStr);
        combustivelStr = String.format("%-" + combustivelWidth + "s", combustivelStr);
        tempoStr = String.format("%-" + tempoWidth + "s", tempoStr);

        return COLOR + nomeStr + " | " + idStr + " | " + combustivelStr + " | " + tempoStr + RESET;
    }
}
