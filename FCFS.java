
import java.util.ArrayList;

/**
 * First come first serve scheduler algorithm.
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
        name = name.concat(ld.getTestFileName());
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
        name = "FCFS-";
        outputName.clear();
        cpuTime.clear();
        pid.clear();
        startingBurstTime.clear();
        endingBurstTime.clear();
        completionTime.clear();
    }
}
