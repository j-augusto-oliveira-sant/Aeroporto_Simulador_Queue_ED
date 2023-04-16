import java.util.Random;

public class Fila {
    private Aviao head = null;
    private final int limite;
    private int tamanho = 0;
    private String tipo = "";
    private int ultimo_id = 0;
    private int tempo_total_esperando = 0;
    private int totalAvioes = 0;
    private int totalAvioes_com_prioridade = 0;

    public Fila(int limite) {
        this.limite = limite;
    }

    public void inserir() {
        Random gerador_random = new Random();
        if (has_space()) {
            totalAvioes += 1;
            ultimo_id += 1;
            int novo_id = ultimo_id;
            Aviao new_node = new Aviao(novo_id, gerador_random.nextInt(1, 21), null);
            if (this.tipo.equals("DEC")) {
                new_node.setCombustivel(gerador_random.nextInt(15, 21));
            } else {
                if (new_node.getCombustivel() <= 2) {
                    if (totalAvioes_com_prioridade >= 2) {
                        new_node.setCombustivel(gerador_random.nextInt(19, 21));
                    } else {
                        if (new_node.getCombustivel() <= 1) {
                            totalAvioes_com_prioridade++;
                        }
                    }
                }
            }
            if (this.head == null) {
                this.head = new_node;
            } else {
                Aviao current = this.head;
                while (current.getNext() != null) {
                    current = current.getNext();
                }
                current.setNext(new_node);
            }
            this.tamanho++;
        }
    }

    public Aviao getHead() {
        return head;
    }

    public boolean checarPrioridade() {
        // checar um aviao que necessite de prioridade e remove-o
        Aviao current = this.head;
        if (current.getCombustivel() <= 1) {
            tempo_total_esperando += head.getTempo_esperando();
            head = head.getNext();
            this.tamanho--;
            this.totalAvioes_com_prioridade--;
            return true;
        }
        while (current != null) {
            if (current.getNext() != null) {
                if (current.getNext().getCombustivel() <= 1) {
                    tempo_total_esperando += current.getNext().getTempo_esperando();
                    current.setNext(current.getNext().getNext());
                    this.tamanho--;
                    this.totalAvioes_com_prioridade--;
                    return true;
                }
            }
            current = current.getNext();
        }
        return false;
    }

    public boolean remover() {
        // remove primeiro da fila se não tiver ninguem prioritario
        // retorna true or false para calculo total prioridade
        boolean cheque_prioritario;
        if (tipo.equals("ATE")) {
            cheque_prioritario = checarPrioridade();
        } else {
            cheque_prioritario = false;
        }
        if (!cheque_prioritario) {
            tempo_total_esperando += head.getTempo_esperando();
            head = head.getNext();
            this.tamanho--;
            return false;
        }
        return true;
    }

    public boolean has_space() {
        return limite > tamanho;
    }

    public String show_fila() {
        Aviao current = this.head;
        String texto = "";
        while (current != null) {
            texto += current.toString() + "\n";
            current = current.getNext();
        }
        return texto;
    }

    public void passar_tempo() {
        // passar o tempo para todos os aviões consumindo combustivel
        Aviao current = this.head;
        while (current != null) {
            current.setCombustivel(current.getCombustivel() - 1);
            // checar prioridade
            if (current.getCombustivel() <= 2){
                this.totalAvioes_com_prioridade++;
            }
            current.setTempo_esperando(current.getTempo_esperando() + 1);
            current = current.getNext();
        }
    }

    public int tempo_medio_espera() {
        // tempo que demora para um aviao com um combustivel
        return tempo_total_esperando / totalAvioes;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getTamanho() {
        return tamanho;
    }
}
