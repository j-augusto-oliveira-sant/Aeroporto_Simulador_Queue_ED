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
        double[] tabela = {0f, 0f, 0f};
        // filas
        Fila fila_aterrisagem = new Fila(10);
        fila_aterrisagem.setTipo("ATE");
        Fila fila_decolagem = new Fila(10);
        fila_decolagem.setTipo("DEC");

        while (playing) {
            printarHeader();
            // novos avioes nas filas
            int qtd_novos_avioes_aterrisagem = gerador_random.nextInt(0, 2);
            int qtd_novos_avioes_decolagem = gerador_random.nextInt(0, 2);
            adicionarAvioes(fila_aterrisagem, qtd_novos_avioes_aterrisagem);
            adicionarAvioes(fila_decolagem, qtd_novos_avioes_decolagem);
            // avioes saindo das filas
            System.out.println(relogio.getTime()+"\n");
            // avioes na fila de aterrisagem
            System.out.println("║ FILA DE ATERRISAGEM ║\n");
            System.out.println(fila_aterrisagem.show_fila());
            // avioes na fila de decolagem
            System.out.println("║ FILA DE DECOLAGEM ║\n");
            System.out.println(fila_decolagem.show_fila());
            tabela[0] = fila_decolagem.tempo_medio_espera();
            tabela[1] = fila_aterrisagem.tempo_medio_espera();
            System.out.format("%-42s%-45s%-45s\n", "Tempo médio de espera para decolagem",
                    "O tempo médio de espera para aterrissagem", "O número de aviões que aterrissam em reserva de combustível");
            System.out.format("%-42.3f%-45.3f%-45.1f\n", tabela[0], tabela[1], tabela[2]);
            System.out.println("\n");
            System.out.print("digite qualquer letra para continuar ou (s)air >:");
            String input_lido = leitor.next();
            if (input_lido.equals("s")){
                playing = false;
            }
            System.out.println("\n");
            relogio.tick();
            // remover das filas
            boolean ate_removeu_com_prioridade = fila_aterrisagem.remover();
            boolean dec_removeu_com_prioridade = fila_decolagem.remover();
            if (ate_removeu_com_prioridade){
                tabela[2] += 1;
            }
            // passar tempo apenas nos que estão no ar, aterrisagem
            fila_aterrisagem.passar_tempo();
            //
            limpaTela();
        }
    }

    public static void adicionarAvioes(Fila fila, int qtd) {
        for (int i = 0; i <= qtd; i++) {
            fila.inserir();
        }
    }

    public static void printarHeader(){
        System.out.println("#######################################");
        System.out.println("# Bem vindo ao Simulador de Aeroporto #");
        System.out.println("#######################################");
    }
    public static void sleep(int num){
        try {
            num *= 1000;
            Thread.sleep(num); // 5000 milliseconds = 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void limpaTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}