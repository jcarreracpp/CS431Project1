
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
public class FCFS implements ScheduleAlgorithm{
    private String name = "FCFS-";
    private int switchTime = 3;
    private LoadData ld = LoadData.getLoadDataInstance();
    private ArrayList outputName;
    private ArrayList cpuTime;
    private ArrayList pid;
    private ArrayList startingBurstTime;
    private ArrayList endingBurstTime;
    private ArrayList completionTime;

    public FCFS(){
        outputName = new ArrayList();
        cpuTime = new ArrayList();
        pid = new ArrayList();
        startingBurstTime = new ArrayList();
        endingBurstTime = new ArrayList();
        completionTime = new ArrayList();
    }
    
    @Override
    public void initAlgorithm() {
        emptyData();
        outputName.add(name.concat(ld.getTestFileName()));
        cpuTime.add(0);
        mainLoop();
    }
    
    @Override
    public void mainLoop(){
        int pcount = ld.getPID().size();
        for(int i = 0; i < pcount; i++){
            pid.add(ld.getPID().get(i));
            startingBurstTime.add(ld.getBurst_Time().get(i));
            endingBurstTime.add(0);
            completionTime.add(((int)ld.getBurst_Time().get(i)+(int)cpuTime.get(i)));
            cpuTime.add((int)completionTime.get(i)+switchTime);
            if(pcount - i == 1){
                cpuTime.remove(cpuTime.size()-1);
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
    
    public void emptyData(){
        outputName.clear();
        cpuTime.clear();
        pid.clear();
        startingBurstTime.clear();
        endingBurstTime.clear();
        completionTime.clear();
    }
}
