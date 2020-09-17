import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class FireControlUnitTest {
    FireControlUnit fcu;

    @BeforeEach
    void setUp(){
        Random r = new Random();
        int numAntennas = r.nextInt(100);
        int dimension = (int)Math.sqrt(numAntennas);
        LocalAntenna[][] antennasGrid = new LocalAntenna[dimension][dimension];
        for (int i = 0; i < antennasGrid.length; i++) {
            for (int j = 0; j < antennasGrid.length; j++) {
                antennasGrid[i][j] = new LocalAntenna(new Coordinates(i,j));
            }
        }
        fcu = new FireControlUnit(antennasGrid);
        for (int i = 0; i < antennasGrid.length; i++) {
            for (int j = 0; j < antennasGrid.length; j++) {
                antennasGrid[i][j].addObserver(fcu);
            }
        }
    }
    @Test
    void correctlyInitialized(){
        assertNotNull(fcu.getSectors());
        assertNotNull(fcu.getCriticalSectors());
        for (int i = 0; i < fcu.getSectors().length; i++) {
            for (int j = 0; j < fcu.getSectors().length; j++) {
                assertTrue(fcu.getSectors()[i][j].getSectorTemperature()>0);
            }
        }
        assertTrue(fcu.getCriticalSectors().isEmpty());
    }
    @Test
    void hasSpottedFire(){
        for (int i = 0; i < fcu.getSectors().length; i++) {
            for (int j = 0; j < fcu.getSectors().length; j++) {
                if(i == 0 && j == 0)
                    //La prima antenna simula un incendio
                    fcu.getSectors()[i][j].setSectorTemperature(81);
                else fcu.getSectors()[i][j].setSectorTemperature(fcu.getSectors()[i][j].getSectorTemperature());
            }
        }
        assertEquals(1, fcu.getCriticalSectors().size());
    }
    @Test
    void hasSpottedMultipleFires(){
        for (int i = 0; i < fcu.getSectors().length; i++) {
            for (int j = 0; j < fcu.getSectors().length; j++) {
                fcu.getSectors()[i][j].setSectorTemperature(81);
            }
        }
        assertEquals(Math.pow(fcu.getSectors().length, 2), fcu.getCriticalSectors().size());
    }
    @Test
    void hasTakenPhotoAtLegalPosition() throws InterruptedException{
        Random r = new Random();
        Coordinates c = new Coordinates(r.nextInt(fcu.getSectors().length),r.nextInt(fcu.getSectors().length));
        fcu.getSectors()[c.x][c.y].setSectorTemperature(51);
        SatellitePhoto sp = fcu.takePhotoAt(c);
        assertNotNull(sp);
        assertNotNull(sp.description);
        assertNotNull(sp.spot);
    }
    @Test
    void hasFailedTakingPhotoAtIllegalPosition() throws InterruptedException{
        SatellitePhoto sp = fcu.takePhotoAt(new Coordinates(-1,0));
        assertNull(sp);
        sp = fcu.takePhotoAt(new Coordinates(0,-1));
        assertNull(sp);
        sp = fcu.takePhotoAt(new Coordinates(fcu.getSectors().length +1,0));
        assertNull(sp);
        sp = fcu.takePhotoAt(new Coordinates(0,fcu.getSectors().length +1));
        assertNull(sp);
    }
}

