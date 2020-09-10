import java.util.Observable;
import java.util.Random;

public class LocalAntenna extends Observable {
    final Coordinates coordinates;
    final static int criticalThreshold = 80;  //degrees
    private int sectorTemperature;  //degrees
    private int increment;      //degrees

    public LocalAntenna(Coordinates coordinates) {
        this.coordinates = coordinates;

        /* Il costruttore genera un valore casuale di temperatura rilevata già al
        momento della creazione */
        Random r = new Random();
        sectorTemperature = 25 + r.nextInt(10) - r.nextInt(10);
        setChanged();
        notifyObservers();
    }
    public int getSectorTemperature() {
        return sectorTemperature;
    }
    public int getIncrement() {
        return increment;
    }
    public void resetIncrement(){increment = 0;}

    /* Funzione che non dovrebbe poter essere accessibile da classi esterne ma che deve
    necessariamente esserlo per poter simulare una reale variazione della temperatura.*/
    public void setSectorTemperature(int sectorTemperature) {
        this.increment = sectorTemperature - this.sectorTemperature;
        this.sectorTemperature = sectorTemperature;
        setChanged();
        notifyObservers();
    }
}

