
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
public class RoundRobin implements ScheduleAlgorithm{
    private String name = "RoundRobbin";
    private int timeQuantum = 0;
    private int switchTime = 3;
    private LoadData ld = LoadData.getLoadDataInstance();
    private ArrayList outputName;
    private ArrayList cpuTime;
    private ArrayList pid;
    private ArrayList startingBurstTime;
    private ArrayList endingBurstTime;
    private ArrayList completionTime;
    
    public RoundRobin(){
        outputName = new ArrayList();
        cpuTime = new ArrayList();
        pid = new ArrayList();
        startingBurstTime = new ArrayList();
        endingBurstTime = new ArrayList();
        completionTime = new ArrayList();
    }
    
    public void roundRobininit(int timeQuantum){
        this.timeQuantum = timeQuantum;
        initAlgorithm();
    }
    
    @Override
    public void initAlgorithm() {
        emptyData();
        name = name.concat(String.valueOf(timeQuantum));
        outputName.add(name.concat(ld.getTestFileName()));
        cpuTime.add(0);
        mainLoop();
    }

    @Override
    public void mainLoop() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList getCalculatedData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void emptyData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
