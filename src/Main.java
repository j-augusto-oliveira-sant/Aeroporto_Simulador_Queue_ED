import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        String intro_img = """
                Art by Donovan Bake
                __|__
                \\___/
                 | |
                 | |
                _|_|______________
                        /|\\\s
                      */ | \\*
                      / -+- \\
                  ---o--(_)--o---
                    /  0 " 0  \\
                  */     |     \\*
                  /      |      \\
                */       |       \\*""";
        System.out.println(intro_img);
        sleep(2);
        limpaTela();
        // Main Simulator Loop
        Random gerador_random = new Random();
        //
        Relogio relogio = new Relogio();
        Scanner leitor = new Scanner(System.in);
        boolean playing = true;

        // informacoes
        double[] tabela1 = {0f, 0f, 0f};
        double[] tabela2 = {0f, 0f, 0f};

        // pista 1
        Fila fila_aterrisagem1 = new Fila(10);
        fila_aterrisagem1.setTipo("ATE");
        Fila fila_decolagem1 = new Fila(10);
        fila_decolagem1.setTipo("DEC");

        // pista 2
        Fila fila_aterrisagem2 = new Fila(10);
        fila_aterrisagem2.setTipo("ATE");
        Fila fila_decolagem2 = new Fila(10);
        fila_decolagem2.setTipo("DEC");

        while (playing) {
            printarHeader();

            // novos avioes nas filas
            int qtd_novos_avioes_aterrisagem = gerador_random.nextInt(0, 3);
            int qtd_novos_avioes_decolagem = gerador_random.nextInt(0, 3);

            //ver espaço nas filas e determinar para onde vai
            adicionarAvioes(fila_aterrisagem1, fila_aterrisagem2, qtd_novos_avioes_aterrisagem, gerador_random);
            adicionarAvioes(fila_decolagem1, fila_decolagem2, qtd_novos_avioes_decolagem, gerador_random);

            // RELOGIO
            System.out.println(relogio.getTime() + "\n");

            // mostrar pista 1
            printarPista(fila_decolagem1,fila_aterrisagem1,tabela1,1);
            // mostrar pista 2
            printarPista(fila_decolagem2,fila_aterrisagem2,tabela2,2);

            System.out.print("digite qualquer letra para continuar ou (s)air >:");
            String input_lido = leitor.next();

            if (input_lido.equals("s")) {
                playing = false;
            }

            System.out.println("\n");
            relogio.tick();

            //
            limpaTela();
            // remover das filas
            System.out.println("REMOVIDO DA PISTA 1 ||");
            if (fila_aterrisagem1.getTotalAvioes_com_prioridade() >= 1) {
                boolean ate_removeu_com_prioridade1 = fila_aterrisagem1.remover();
                if (ate_removeu_com_prioridade1) {
                    tabela1[2] += 1;
                }
            } else if (fila_aterrisagem1.is_empty()) {
                fila_decolagem1.remover();
            } else if (fila_decolagem1.is_empty()) {
                fila_aterrisagem1.remover();
            } else {
                if (fila_aterrisagem1.getTamanho() < fila_decolagem1.getTamanho()) {
                    fila_decolagem1.remover();
                } else {
                    fila_aterrisagem1.remover();
                }
            }
            //
            sleep(2);
            //
            limpaTela();
            //
            System.out.println("REMOVIDO DA PISTA 2 ||");
            if (fila_aterrisagem2.getTotalAvioes_com_prioridade() >= 1) {
                boolean ate_removeu_com_prioridade2 = fila_aterrisagem2.remover();
                if (ate_removeu_com_prioridade2) {
                    tabela2[2] += 1;
                }
            } else if (fila_aterrisagem2.is_empty()) {
                fila_decolagem2.remover();
            } else if (fila_decolagem2.is_empty()) {
                fila_aterrisagem2.remover();
            } else {
                if (fila_aterrisagem2.getTamanho() < fila_decolagem2.getTamanho()) {
                    fila_decolagem2.remover();
                } else {
                    fila_aterrisagem2.remover();
                }
            }
            //
            sleep(2);
            //

            // passar tempo
            fila_aterrisagem1.passar_tempo();
            fila_aterrisagem2.passar_tempo();
            fila_decolagem1.passar_tempo();
            fila_decolagem2.passar_tempo();
            //
            System.out.println("\n");
            limpaTela();
        }
    }

    public static void printarPista(Fila fila_decolagem, Fila fila_aterrisagem, double[] tabela, int pista){
        // avioes na fila de aterrisagem
        System.out.println("║ FILA DE ATERRISAGEM PISTA "+pista+" ║\n");
        System.out.println(fila_aterrisagem.show_fila());

        // avioes na fila de decolagem
        System.out.println("║ FILA DE DECOLAGEM PISTA "+pista+" ║\n");
        System.out.println(fila_decolagem.show_fila());

        tabela[0] = fila_decolagem.tempo_medio_espera();
        tabela[1] = fila_aterrisagem.tempo_medio_espera();

        System.out.format("%-42s%-45s%-45s\n", "Tempo médio de espera para decolagem",
                "O tempo médio de espera para aterrissagem", "O número de aviões que aterrissam em reserva de combustível");
        System.out.format("%-42.3f%-45.3f%-45.1f\n", tabela[0], tabela[1], tabela[2]);
        System.out.println("#######################################");
        System.out.println("\n");
    }

    public static void adicionarAvioes(Fila fila1, Fila fila2, int qtd, Random gerador_random) {
        for (int i = 0; i <= qtd; i++) {
            if (fila1.getTamanho() == fila2.getTamanho()) {
                int randFila = gerador_random.nextInt(0,2);
                if (randFila == 0) {
                    fila1.inserir();
                } else if (randFila == 1) {
                    fila2.inserir();
                }
            } else if (fila1.getTamanho() < fila2.getTamanho()) {
                fila1.inserir();
            } else {
                fila2.inserir();
            }
        }
    }

    public static void printarHeader() {
        System.out.println("#######################################");
        System.out.println("# Bem vindo ao Simulador de Aeroporto #");
        System.out.println("#######################################");
    }

    public static void sleep(int num) {
        try {
            num *= 1000;
            Thread.sleep(num); // 5000 milliseconds = 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void limpaTela() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {
            // handle exception here
        }
    }
}