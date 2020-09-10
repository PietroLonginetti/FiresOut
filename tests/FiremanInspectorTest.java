import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class FiremanInspectorTest {
    FireControlUnit fcu;
    FiremanInspector fi;

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
        fi = new FiremanInspector();
        fcu.addObserver(fi);
    }

    @Test
    void noStrategyOnCreation(){
        assertNull(fi.getResponseStrategy());
    }

    @Test
    void elaboratesStrategyWhenFiresNotified(){
        Random r = new Random();
        Coordinates c = new Coordinates(r.nextInt(fcu.getSectors().length), r.nextInt(fcu.getSectors().length));
        for (int i = 0; i < fcu.getSectors().length; i++) {
            for (int j = 0; j < fcu.getSectors().length; j++) {
                if(i == c.x && j == c.y)
                    //La antenna alle coordinate c simula un incendio
                    fcu.getSectors()[i][j].setSectorTemperature(81);
                else fcu.getSectors()[i][j].setSectorTemperature(fcu.getSectors()[i][j].getSectorTemperature());
            }
        }
        assertNotNull(fi.getResponseStrategy());
    }

}
