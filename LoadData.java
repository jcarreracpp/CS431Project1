
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jorge
 */
public class LoadData {
    private String testfileName;
    private ArrayList pid;
    private ArrayList burst_time;
    private ArrayList priority;
    
    private static LoadData instance = null;
    
    private LoadData(){
        pid = new ArrayList();
        burst_time = new ArrayList();
        priority = new ArrayList();
    }
    
    //Singleton class to ensure all the data loading is funneled by the same
    //instance of LoadData.
    public static LoadData getLoadDataInstance(){
        if(instance == null){
            instance = new LoadData();
        }
        return instance;
    }
    
    //Accesses and loads in data from the specified file if it exists, else
    //returns an exception.
    public void loadFile(String name){
        try{
            Scanner scanner = new Scanner(new File(name));
            testfileName = name;
            while(scanner.hasNextInt()){
                pid.add(scanner.nextInt());
                burst_time.add(scanner.nextInt());
                priority.add(scanner.nextInt());
            }
        }catch(Exception ohno){
            System.out.println("ERROR: Input file does not exist or is impro"
                    + "perly named!");
        }
    }
    
    public ArrayList getData(){
        ArrayList temp = new ArrayList();
        temp.add(testfileName);
        temp.add(pid);
        temp.add(burst_time);
        temp.add(priority);
        return temp;
    }
    
    //Empties the arraylists.
    public void emptyData(){
        pid.clear();
        burst_time.clear();
        priority.clear();
    }
    
}
