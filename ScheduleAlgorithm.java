
import java.util.ArrayList;

/**
 * Interface file from which all the algorithms implement.
 * @author Jorge
 */
public interface ScheduleAlgorithm {
    
    public void initAlgorithm();
    
    public void mainLoop();
    
    public ArrayList getCalculatedData();
    
    public void emptyData();
}
