
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Lottery scheduler algorithm set to a time quantum of 50.
 * 
 * @author Jorge
 */
public class Lottery implements ScheduleAlgorithm{
    private String name;
    private int timeQuantum = 50;
    private int switchTime = 3;
    private int previousPID = -1;
    private int priorityRange;
    private LoadData ld = LoadData.getLoadDataInstance();
    private ArrayList outputName;
    private ArrayList cpuTime;
    private ArrayList pid;
    private ArrayList startingBurstTime;
    private ArrayList endingBurstTime;
    private ArrayList completionTime;
    private ArrayList externalPID;
    private ArrayList externalBURST;
    private ArrayList externalPRIORITY;
    
    public Lottery(){
        outputName = new ArrayList();
        cpuTime = new ArrayList();
        pid = new ArrayList();
        startingBurstTime = new ArrayList();
        endingBurstTime = new ArrayList();
        completionTime = new ArrayList();
        externalPID = new ArrayList();
        externalBURST = new ArrayList();
        externalPRIORITY = new ArrayList();
    }
    
    @Override
    public void initAlgorithm() {
        emptyData();
        name = "Lottery";
        name = name.concat(String.valueOf(timeQuantum));
        name = name.concat("-");
        outputName.add(name.concat(ld.getTestFileName()));
        name = name.concat(ld.getTestFileName());
        cpuTime.add(0);
        mainLoop();
    }

    @Override
    public void mainLoop() {
        boolean looped = false;
        boolean tapped;
        int priorityRoll;
        externalPID = (ArrayList)ld.getPID().clone();
        externalBURST = (ArrayList)ld.getBurst_Time().clone();
        externalPRIORITY = (ArrayList)ld.getPriority().clone();
        
        while(!looped){
            tapped = false;
            calcPriRange();
            priorityRoll = (int) (Math.random() * priorityRange);
            for(int i = 0; i < externalPID.size(); i++){
                priorityRoll -= (int)externalPRIORITY.get(i);
                if(priorityRoll <= 0 && !tapped){
                    jobScanner((int)cpuTime.get(cpuTime.size()-1), (int)externalPID.get(i), (int)externalBURST.get(i));
                    modLists(i);
                    tapped = true;
                    i = externalPID.size();
                }
            }
            if(externalPID.isEmpty())
                looped = true;
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
            completionTime.add(0);
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
                externalPID.remove(index);
                externalBURST.remove(index);
                externalPRIORITY.remove(index);
            }
        }
    }
    
    private void calcPriRange(){
        int temp = 0;
        for(int j = 0; j < externalPRIORITY.size(); j++){
            temp += (int)externalPRIORITY.get(j);
        }
        priorityRange = temp;
    }
    
    @Override
    public ArrayList getCalculatedData() {
        System.out.println(name + " Avg Turn-around: \t"
                +(int)completionTime.get(completionTime.size()-1)/ld.getPID().size());
        ArrayList send = new ArrayList();
        send.add(name);
        send.add(cpuTime);
        send.add(pid);
        send.add(startingBurstTime);
        send.add(endingBurstTime);
        send.add(completionTime);
        return send;
    }

    @Override
    public void emptyData() {
        name = null;
        outputName.clear();
        cpuTime.clear();
        pid.clear();
        startingBurstTime.clear();
        endingBurstTime.clear();
        completionTime.clear();
    }
    
}
