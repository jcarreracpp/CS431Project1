
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * This class takes all the generated information and makes new .csv files
 * from them.
 * 
 * @author Jorge
 */
public class RenderData {
    private String fileName;
    private ArrayList cpuTime;
    private ArrayList pID;
    private ArrayList startBurst;
    private ArrayList endBurst;
    private ArrayList compTime;
    
    public RenderData(){
        cpuTime = new ArrayList();
        pID = new ArrayList();
        startBurst = new ArrayList();
        endBurst = new ArrayList();
        compTime = new ArrayList();
    }
    
    public void render(ArrayList bundle) throws IOException{
        assignment(bundle);
        reExtendFileName();
        makePrint();
    }
    
    private void assignment(ArrayList bundle){
        fileName = (String)bundle.get(0);
        cpuTime = (ArrayList)((ArrayList)bundle.get(1)).clone();
        pID = (ArrayList)((ArrayList)bundle.get(2)).clone();
        startBurst = (ArrayList)((ArrayList)bundle.get(3)).clone();
        endBurst = (ArrayList)((ArrayList)bundle.get(4)).clone();
        compTime = (ArrayList)((ArrayList)bundle.get(5)).clone();
    }
    
    private void reExtendFileName(){
        fileName = fileName.substring(0, fileName.length()-4);
        fileName = fileName.concat(".csv");
    }
    
    private void makePrint() throws IOException{
        FileWriter file = new FileWriter(fileName);
        PrintWriter pw = new PrintWriter(file);
        
        pw.print("CpuTime,PID,StartingBurstTime,EndingBurstTime,CompletionTime\r\n");
        for(int i = 0; i < cpuTime.size(); i++){
            pw.print(String.valueOf(cpuTime.get(i))+","+String.valueOf(pID.get(i))+","
                    +String.valueOf(startBurst.get(i))+","+String.valueOf(endBurst.get(i))+","+String.valueOf(compTime.get(i))+"\r\n");
        }
        pw.close();
        file.close();
    }
}
