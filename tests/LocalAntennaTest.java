import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LocalAntennaTest {
    LocalAntenna a;

    @BeforeEach
    void setUp(){
        Coordinates c = new Coordinates(0,0);
        a = new LocalAntenna(c);
    }
    @Test
    void getCoordinates(){
        assertEquals(0, a.coordinates.x);
        assertEquals(0, a.coordinates.y);
    }
    @Test
    void getCriticalThreshold(){
        assertEquals(50, LocalAntenna.criticalThreshold);
    }
    @Test
    void hasChangedValuesOnCreation(){
        assertTrue(a.getSectorTemperature() > 0);
        assertEquals(0, a.getIncrement());
    }
    @Test
    void hasChangedTemperatureValue(){
        int oldTemperature = a.getSectorTemperature();
        a.setSectorTemperature(a.getSectorTemperature() + 5);
        assertEquals(oldTemperature + 5, a.getSectorTemperature());
    }
    @Test
    void hasChangedIncrementValue(){
        int oldTemperature = a.getSectorTemperature();
        a.setSectorTemperature(0);
        assertEquals(-oldTemperature, a.getIncrement());
    }
}
