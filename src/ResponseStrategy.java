import java.util.Random;

import static java.lang.Thread.sleep;

public interface ResponseStrategy {
    void respondEmergency(LocalAntenna antenna);
    default void extinguishFire(LocalAntenna a){
//        Random r = new Random();
//        a.setSectorTemperature(30 + r.nextInt(5) - r.nextInt(5));
        a.extinguishing = true;
    }
}

class CallPoliceStrategy implements ResponseStrategy{
    @Override
    public void respondEmergency(LocalAntenna antenna) {
        System.out.println("La polizia viene contattata, una pattuglia sopraggiunge nel settore "
                + antenna.coordinates.x + antenna.coordinates.y +
                " e intima alle persone di spegnere il fuoco e di tornarsene a casa.");
        extinguishFire(antenna);
    }
}

class CanadairStrategy implements ResponseStrategy{
    @Override
    public void respondEmergency(LocalAntenna antenna) {
        System.out.println("Un aereo-cisterna decolla e rilascia a pioggia sul settore " + antenna.coordinates.x +
                antenna.coordinates.y + " una quantità d'acqua sufficiente a spegnere un piccolo incendio");
        extinguishFire(antenna);
    }
}

class EvacuateAndSendTruckStrategy implements ResponseStrategy{
    @Override
    public void respondEmergency(LocalAntenna antenna) {
        System.out.println("I residenti nel settore " + antenna.coordinates.x + antenna.coordinates.y +
                " vengono contattati e fatti evacuare, nel frattempo i camion-cisterna dei vigili del " +
                "fuoco arrivano sul luogo e domano l'incendio");
        extinguishFire(antenna);
    }
}

