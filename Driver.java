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
    public static void main(String args[]){
        LoadData ld = LoadData.getLoadDataInstance();
        FCFS fcfs = new FCFS();
        ld.loadFile("testdata1.txt");
        fcfs.initAlgorithm();
        fcfs.getCalculatedData();
    }
}
