import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class Satellite extends Thread{
    private ArrayBlockingQueue<Coordinates> requestSpots;
    private ArrayBlockingQueue<SatellitePhoto> criticalPhotos;
    private static Satellite instance = null;
    private Satellite() {
        requestSpots = new ArrayBlockingQueue<Coordinates>(1);
        criticalPhotos = new ArrayBlockingQueue<SatellitePhoto>(1);
    }

    public static synchronized Satellite getInstance(){
        if(instance == null) {
            instance = new Satellite();
        }
        return instance;
    }
    public ArrayBlockingQueue<Coordinates> getRequestSpots() {
        return requestSpots;
    }
    public ArrayBlockingQueue<SatellitePhoto> getCriticalPhotos() {
        return criticalPhotos;
    }

    private void takePhotoAt(Coordinates c){
        Random r = new Random();
        int randomPhotoIndex = r.nextInt(SatellitePhotoDescription.values().length);
        criticalPhotos.offer(new SatellitePhoto(SatellitePhotoDescription.values()[randomPhotoIndex], c));
        System.out.println("Il satellite ha scattato una foto nel settore "+ c.x + c.y);
    }

    @Override
    public void run() {
        while(true) {
            try{
                //Quando la coda è vuota il satellite resta in attesa di una "richiesta".
                //La richiesta è rappresentata dagli elementi (Coordinate) presenti nella coda requestSpots.
                takePhotoAt(requestSpots.take());
            } catch (InterruptedException e){}
        }
    }
}
