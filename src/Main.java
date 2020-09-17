import java.util.Random;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserire il numero di antenne che si hanno a disposizione per il monitoraggio della zona: ");
        int numAntennas = scanner.nextInt();
        int gridDimension = (int)Math.sqrt(numAntennas);

        //Creazione della FireControlUnit
        LocalAntenna[][] antennasGrid = new LocalAntenna[gridDimension][gridDimension];
        FireControlUnit fcu = new FireControlUnit(antennasGrid);
        //Creazione delle Antenne
        for (int i = 0; i < antennasGrid.length; i++) {
            for (int j = 0; j < antennasGrid.length; j++) {
                antennasGrid[i][j] = new LocalAntenna(new Coordinates(i,j));
                antennasGrid[i][j].addObserver(fcu);
            }
        }
        //Creazione del FireInspector
        FiremanInspector fi = new FiremanInspector();
        fcu.addObserver(fi);


        System.out.println("Configurazione iniziale:");
        fcu.printGrid();
        System.out.println("\nAvvio del programma di monitoraggio...");
        sleep(2000);

        //Simulazione  delle variazioni di temperatura
        while (true) {
            for (int i = 0; i < antennasGrid.length; i++) {
                Random r = new Random();
                for (int j = 0; j < antennasGrid.length; j++) {
                    LocalAntenna current = antennasGrid[i][j];
                    if(!current.extinguishing) {
                        int dice = r.nextInt(50);
                        if (dice != 0) {
                            //Simulazione  di piccole e normali variazioni di temperatura
                            current.setSectorTemperature(current.getSectorTemperature() + (r.nextInt() % 3));
                        } else /*dice == 0 */ {
                            //Simulazione  di un aumento di temperatura corrispondente ad un incendio
                            current.setSectorTemperature(current.getSectorTemperature() + r.nextInt(100));
                        }
                    } else {
                        current.setSectorTemperature(current.getSectorTemperature() - r.nextInt(20));
                    }
                }
            }
            sleep(2000);
        }
    }
}


