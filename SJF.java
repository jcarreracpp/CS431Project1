
import java.util.ArrayList;

/**
 * Shortest job first scheduler algorithm.
 * 
 * @author Jorge
 */
public class SJF implements ScheduleAlgorithm{
    private String name = "SJF-";
    private int switchTime = 3;
    private LoadData ld = LoadData.getLoadDataInstance();
    private ArrayList outputName;
    private ArrayList cpuTime;
    private ArrayList pid;
    private ArrayList startingBurstTime;
    private ArrayList endingBurstTime;
    private ArrayList completionTime;
    private ArrayList externalPID;
    private ArrayList externalBURST;
    
    public SJF(){
        externalPID = new ArrayList();
        externalBURST = new ArrayList();
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
        name = name.concat(ld.getTestFileName());
        cpuTime.add(0);
        sjfOrganize();
        mainLoop();
    }

    @Override
    public void mainLoop() {
        int pcount = externalPID.size();
        for(int i = 0; i < pcount; i++){
            pid.add(externalPID.get(i));
            startingBurstTime.add(externalBURST.get(i));
            endingBurstTime.add(0);
            completionTime.add(((int)externalBURST.get(i)+(int)cpuTime.get(i)));
            cpuTime.add((int)completionTime.get(i)+switchTime);
            if(pcount - i == 1){
                cpuTime.remove(cpuTime.size()-1);
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
    public void emptyData(){
        name = "SJF-";
        outputName.clear();
        cpuTime.clear();
        pid.clear();
        startingBurstTime.clear();
        endingBurstTime.clear();
        completionTime.clear();
        externalBURST.clear();
        externalPID.clear();
    }
    
    private void sjfOrganize(){
        int minpid = 0;
        int minburst = 0;
        externalPID = (ArrayList)ld.getPID().clone();
        externalBURST = (ArrayList)ld.getBurst_Time().clone();
        
        for(int i = 0 ; i < externalPID.size()-1; i++){
            int listid = i;
            for(int j = i+1; j < externalPID.size(); j++){
                if((int)externalBURST.get(j) < (int)externalBURST.get(listid)){
                    listid = j;
                }
            }
                minpid = (int) externalPID.get(listid);
                minburst = (int) externalBURST.get(listid);
                externalBURST.set(listid, (int)externalBURST.get(i));
                externalPID.set(listid, (int)externalPID.get(i));
                externalBURST.set(i, minburst);
                externalPID.set(i, minpid);            
        }
        
    }
}
