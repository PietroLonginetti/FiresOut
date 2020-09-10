import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class FiremanInspector implements Observer{
    private ResponseStrategy responseStrategy;
    public ResponseStrategy getResponseStrategy() {
        return responseStrategy;
    }

    @Override
    public void update(Observable o, Object arg) {
        //O è la FireControlUnit
        //Arg invece è una lista di antenne che si trovano nei settori con incendio
        for (LocalAntenna spot: (ArrayList<LocalAntenna>)arg) {
            try {
                SatellitePhoto situation = ((FireControlUnit)o).takePhotoAt(spot.coordinates);
                System.out.println("Dalla foto scattata dal satellite si vede chiaramente che l'alta temperatura"+
                        " rilevata nel settore " + situation.spot.x + situation.spot.y +
                        " è dovuta a: " + situation.description);
                switch (situation.description) {
                    case CampFire: {
                        responseStrategy = new CallPoliceStrategy();
                        break;
                    }
                    case WoodFire: {
                        responseStrategy = new CanadairStrategy();
                        break;
                    }
                    case HouseFire: {
                        responseStrategy = new EvacuateAndSendTruckStrategy();
                    }
                }
                responseStrategy.respondEmergency(spot);
            } catch (InterruptedException e) {}
        }
    }
}
