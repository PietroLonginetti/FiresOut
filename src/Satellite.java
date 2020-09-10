import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class Satellite extends Thread{
    private static ArrayBlockingQueue<Coordinates> requestSpots;
    private static ArrayBlockingQueue<SatellitePhoto> criticalPhotos;
    private static Satellite instance = null;
    private Satellite() {}

    public static Satellite getInstance(){
        if(instance == null) {
            instance = new Satellite();
            requestSpots = new ArrayBlockingQueue<Coordinates>(1);
            criticalPhotos = new ArrayBlockingQueue<SatellitePhoto>(1);
        }
        return instance;
    }
    public static ArrayBlockingQueue<Coordinates> getRequestSpots() {
        return requestSpots;
    }
    public static ArrayBlockingQueue<SatellitePhoto> getCriticalPhotos() {
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
