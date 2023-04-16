import java.util.Random;

public class Relogio {
    private int hora;
    private int minuto;

    public Relogio() {
        Random rand = new Random();
        this.hora = 0;
        this.minuto = 0;
    }

    public void tick() {
        this.minuto += 15;
        if (this.minuto >= 60) {
            this.hora += 1;
            this.minuto = this.minuto % 60;
        }
        if (this.hora >= 24) {
            this.hora = this.hora % 24;
        }
    }

    public String getTime() {
        String horaStr = String.format("%02d", this.hora);
        String minutoStr = String.format("%02d", this.minuto);
        return "Horario: " + horaStr + ":" + minutoStr;
    }
}
