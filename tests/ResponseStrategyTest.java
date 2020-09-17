import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResponseStrategyTest {
    ResponseStrategy rs;
    LocalAntenna a;

    @BeforeEach
    void setUp(){
        a = new LocalAntenna(new Coordinates(0,0));
    }
    @Test
    void callPoliceEmergencyResponse(){
        rs = new CallPoliceStrategy();
        a.setSectorTemperature(81);
        rs.respondEmergency(a);

        assertTrue(a.extinguishing);
    }
    @Test
    void canadairEmergencyResponse(){
        rs = new CanadairStrategy();
        a.setSectorTemperature(81);
        rs.respondEmergency(a);

        assertTrue(a.extinguishing);
    }
    @Test
    void evacuateAndSendtruckEmergencyResponse(){
        rs = new EvacuateAndSendTruckStrategy();
        a.setSectorTemperature(81);
        rs.respondEmergency(a);

        assertTrue(a.extinguishing);
    }

}


