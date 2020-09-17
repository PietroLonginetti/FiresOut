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
        assertNotNull(satellite.getRequestSpots());
        assertNotNull(satellite.getCriticalPhotos());
    }
    @Test
    void emptyQueuesOnCreation(){
        assertTrue(satellite.getRequestSpots().isEmpty());
        assertTrue(satellite.getCriticalPhotos().isEmpty());
    }
    @Test
    void photoShootingMechanism(){
        Coordinates c = new Coordinates(0,0);
        satellite.getRequestSpots().offer(c);

        assertEquals(1, satellite.getRequestSpots().size());
        assertEquals(0, satellite.getCriticalPhotos().size());

        satellite.start();
        try {
            sleep(1000);

            assertEquals(0, satellite.getRequestSpots().size());
            assertEquals(1, satellite.getCriticalPhotos().size());

            satellite.getCriticalPhotos().take();

            assertEquals(0, satellite.getRequestSpots().size());
            assertEquals(0, satellite.getCriticalPhotos().size());

        }catch (InterruptedException e){}
    }

}
