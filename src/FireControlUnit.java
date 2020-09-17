import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class FireControlUnit extends Observable implements Observer {
    private Satellite satellite;
    private int antennasUpdated;
    private final LocalAntenna[][] sectors;
    private final ArrayList<LocalAntenna> criticalSectors;

    public FireControlUnit(LocalAntenna[][] antennasGrid){
        criticalSectors = new ArrayList<>();
        sectors = antennasGrid;
    }
    public void printGrid() {
        //Funzione che mostra su schermo tutti i valori di temperatura nei vari settori e i loro relativi incrementi
        for (int i = 0; i < sectors.length; i++) {
            for (int j = 0; j < sectors.length; j++) {
                if(sectors[i][j].getIncrement() < 0) {
                    System.out.print("A" + i + j + ":" + sectors[i][j].getSectorTemperature() + "°" +
                            "(" + sectors[i][j].getIncrement() + ")    ");
                } else {
                    System.out.print("A" + i + j + ":" + sectors[i][j].getSectorTemperature() + "°" +
                            "(+" + sectors[i][j].getIncrement() + ")    ");
                }
                sectors[i][j].resetIncrement();
            }
            System.out.println();
        }
    }
    public LocalAntenna[][] getSectors() {
        return sectors;
    }
    public ArrayList<LocalAntenna> getCriticalSectors() {
        return criticalSectors;
    }

    private void checkFires(){
        for (LocalAntenna a:criticalSectors) {
            System.out.println("Incendio rilevato nel settore " + a.coordinates.x + a.coordinates.y);
        }
        if(!criticalSectors.isEmpty()) {
            setChanged();
            notifyObservers(new ArrayList<>(criticalSectors));  //Copia difensiva
        }
    }
    public SatellitePhoto takePhotoAt(Coordinates c) throws InterruptedException {
        //Proxy method
        try {
            if (c.x > sectors.length || c.x < 0
                    || c.y > sectors.length || c.y < 0)
                throw new InvalidParameterException("Non è possibile scattare una foto in un settore non monitorato.");
        } catch (InvalidParameterException e){
            System.err.println(e);
            return null;
        }
        if(satellite == null){
            satellite = Satellite.getInstance();
            satellite.start();
        }
        satellite.getRequestSpots().offer(c);
        //il metodo take() aspetta che ci sia almeno un elemento nella coda di foto scattate dal satellite
        return satellite.getCriticalPhotos().take();
    }

    @Override
    public void update(Observable o, Object arg) {
        //O corrisponde alla antenna che ha segnalato una variazione di temperatura
        //Arg è nullo -> Pull Observer
        LocalAntenna s = (LocalAntenna) o;
        if (s.getSectorTemperature() > LocalAntenna.criticalThreshold) {
            criticalSectors.add(s);
        }

        //Gestione dello spegnimento di un incendio
        if (s.getSectorTemperature() - s.getIncrement() > LocalAntenna.criticalThreshold) {
            System.out.println("Incendio domato nel settore " + s.coordinates.x + s.coordinates.y + "\n");
            criticalSectors.remove(s);
            if (criticalSectors.isEmpty()) {
                System.out.println("Tutti gli incendi domati. Attuale situazione: ");
                printGrid();
            }
        }

        //Quando tutti i valori della griglia sono stati aggiornati viene mostrata
        //la griglia di valori aggiornata e il programma controlla se ci sono incendi
        antennasUpdated++;
        if (antennasUpdated == Math.pow(sectors.length, 2)) {
            System.out.println();
            printGrid();
            checkFires();
            antennasUpdated = 0;
        }
    }
}

