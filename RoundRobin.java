
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
    private String name;
    private int timeQuantum = 0;
    private int switchTime = 3;
    private int previousPID = -1;
    private LoadData ld = LoadData.getLoadDataInstance();
    private ArrayList outputName;
    private ArrayList cpuTime;
    private ArrayList pid;
    private ArrayList startingBurstTime;
    private ArrayList endingBurstTime;
    private ArrayList completionTime;
    private ArrayList externalPID;
    private ArrayList externalBURST;
    
    public RoundRobin(){
        outputName = new ArrayList();
        cpuTime = new ArrayList();
        pid = new ArrayList();
        startingBurstTime = new ArrayList();
        endingBurstTime = new ArrayList();
        completionTime = new ArrayList();
        externalPID = new ArrayList();
        externalBURST = new ArrayList();
    }
    
    public void roundRobininit(int timeQuantum){
        this.timeQuantum = timeQuantum;
        initAlgorithm();
    }
    
    @Override
    public void initAlgorithm() {
        emptyData();
        name = "RR";
        name = name.concat(String.valueOf(timeQuantum));
        name = name.concat("-");
        outputName.add(name.concat(ld.getTestFileName()));
        cpuTime.add(0);
        mainLoop();
    }

    @Override
    public void mainLoop() {
        boolean looped = false;
        int killcounter = 0;
        externalPID = (ArrayList)ld.getPID().clone();
        externalBURST = (ArrayList)ld.getBurst_Time().clone();
        
        while(!looped){
            killcounter = 0;
            for(int i = 0; i < externalPID.size(); i ++){
                if((int)externalPID.get(i) == -1){
                    killcounter++;
                }else{
                    jobScanner((int)cpuTime.get(cpuTime.size()-1), (int)externalPID.get(i), (int)externalBURST.get(i));
                    modLists(i);
                }
                if(killcounter == externalPID.size()){
                    looped = true;
                }
            }
        }
        cpuTime.remove(cpuTime.size()-1);
    }
    
    private void jobScanner(int cpuTime, int pid, int burst){
        boolean switched = true;
        this.pid.add(pid);
        startingBurstTime.add(burst);

        if((burst - timeQuantum) <= 0){
            endingBurstTime.add(0);
            completionTime.add(cpuTime + burst);
            this.cpuTime.add(cpuTime + burst);
        }else{
            endingBurstTime.add(burst-timeQuantum);
            completionTime.add(-1);
            this.cpuTime.add(cpuTime + timeQuantum);
        }

    }
    private void modLists(int index){
        if(previousPID != (int)externalPID.get(index)){
            cpuTime.set(cpuTime.size()-1, (int)cpuTime.get(cpuTime.size()-1)+switchTime);
        }
        previousPID = (int)externalPID.get(index);
        if((int)externalPID.get(index) != -1){
            if((int)externalBURST.get(index) > timeQuantum){
                externalBURST.set(index, ((int)externalBURST.get(index)-timeQuantum));
            }else{
                externalPID.set(index, -1);
            }
        }
    }

    @Override
    public ArrayList getCalculatedData() {
        int pcount = pid.size();
        System.out.println(outputName +" "+pcount);
        for(int i = 0; i < pcount; i++){
            System.out.print(cpuTime.get(i) + "\t");
            System.out.print(pid.get(i) + "\t");
            System.out.print(startingBurstTime.get(i) + "\t");
            System.out.print(endingBurstTime.get(i) + "\t");
            System.out.println(completionTime.get(i));
        }
        return null;
    }

    @Override
    public void emptyData() {
        outputName.clear();
        cpuTime.clear();
        pid.clear();
        startingBurstTime.clear();
        endingBurstTime.clear();
        completionTime.clear();
    }
    
}
