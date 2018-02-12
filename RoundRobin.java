
import java.util.ArrayList;

/**
 * Round robin scheduler algorithm.
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
        name = name.concat(ld.getTestFileName());
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
                externalPID.set(index, -1);
            }
        }
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
