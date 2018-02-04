
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jorge
 */
public interface ScheduleAlgorithm {
    public String schedulerName = null;
    
    public void runAlgorithm(ArrayList input);
    
    public ArrayList getCalculatedData();
}
