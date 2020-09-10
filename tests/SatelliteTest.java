import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

class SatelliteTest{
    Satellite satellite;

    @BeforeEach
    void setUp(){
        satellite = Satellite.getInstance();
    }
    @Test
    void initialized(){
        assertNotNull(satellite);
        assertNotNull(Satellite.getRequestSpots());
        assertNotNull(Satellite.getCriticalPhotos());
    }
    @Test
    void emptyQueuesOnCreation(){
        assertTrue(Satellite.getRequestSpots().isEmpty());
        assertTrue(Satellite.getCriticalPhotos().isEmpty());
    }
    @Test
    void photoShootingMechanism(){
        Coordinates c = new Coordinates(0,0);
        Satellite.getRequestSpots().offer(c);

        assertEquals(1, Satellite.getRequestSpots().size());
        assertEquals(0, Satellite.getCriticalPhotos().size());

        satellite.start();
        try {
            sleep(1000);

            assertEquals(0, Satellite.getRequestSpots().size());
            assertEquals(1, Satellite.getCriticalPhotos().size());

            Satellite.getCriticalPhotos().take();

            assertEquals(0, Satellite.getRequestSpots().size());
            assertEquals(0, Satellite.getCriticalPhotos().size());

        }catch (InterruptedException e){}
    }

}
