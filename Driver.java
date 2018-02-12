
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This class will call main work function that uses a data class. Data class
 * can take input from files and output to files. Interface for the scheduler
 * classes.
 * @author Jorge
 */
public class Driver {
        static LoadData ld = LoadData.getLoadDataInstance();
        static FCFS fcfs = new FCFS();
        static SJF sjf = new SJF();
        static RoundRobin rr = new RoundRobin();
        static Lottery lotto = new Lottery();
        static RenderData rd = new RenderData();
        
    public static void main(String args[]) throws IOException{
        algosuite("testdata1.txt");
        algosuite("testdata2.txt");
        algosuite("testdata3.txt");
        algosuite("testdata4.txt");
    }
    
    public static void algosuite(String string) throws IOException{
        ld.loadFile(string);
        fcfs.initAlgorithm();
        rd.render(fcfs.getCalculatedData());
        sjf.initAlgorithm();
        rd.render(sjf.getCalculatedData());
        rr.roundRobininit(25);
        rd.render(rr.getCalculatedData());
        rr.roundRobininit(50);
        rd.render(rr.getCalculatedData()); 
        lotto.initAlgorithm();
        rd.render(lotto.getCalculatedData());
    }
}
