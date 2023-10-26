import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

class ProcesadorArchivo extends Thread {
    private String url;
    private Map<String, Integer> resultados;

    public ProcesadorArchivo(String url, Map<String, Integer> results) {
        this.url = url;
        this.resultados = resultados;
    }

    public void run() {
        long inicio = System.currentTimeMillis(); 
        try {
            URL urlArchivo = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(urlArchivo.openStream()));
            int lineas = 0;
            while (in.readLine() != null) {
                lineas++;
            }
            resultados.put(url, lineas);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long fin = System.currentTimeMillis(); 
        System.out.println("El archivo: " + url + " Contiene " + resultados.get(url) + " lineas");
        System.out.println("Tiempo de ejecución del hilo: " + (fin - inicio) + " milisegundos");
    }
}

public class AnalizadorArchivo {

    public static void main(String[] args) {

        String[] archivos = { "addresses.csv", "airtravel.csv", "biostats.csv", "cities.csv", "crash_catalonia.csv", "deniro.csv", "example.csv", "ford_escort.csv", "faithful.csv",
                              "freshman_kgs.csv", "freshman_lbs.csv", "grades.csv", "homes.csv", "hooke.csv", "hurricanes.csv", "hw_200.csv", "hw_25000.csv", "lead_shot.csv", "letter_frequency.csv",
                              "mlb_players.csv", "mlb_teams_2012.csv", "news_decline.csv", "nile.csv", "oscar_age_female.csv", "oscar_age_male.csv", "snakes_count_10.csv", "snakes_count_100.csv",
                             "snakes_count_1000.csv", "snakes_count_10000.csv", "tally_cab.csv", "taxables.csv", "trees.csv", "zillow.csv" };

        Map<String, Integer> resultados = new HashMap<>();

        for (String archivo : archivos) {
            String url = "https://people.sc.fsu.edu/~jburkardt/data/csv/" + archivo;
            ProcesadorArchivo processor = new ProcesadorArchivo(url, resultados);
            processor.start();
            try {
                processor.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
