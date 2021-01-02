import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class RoundRobin {
	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		
	File file = new File ("input.txt");
	
	Scanner scanner2 = new Scanner(file); 
	
	Queue <Process> waitQueue = new LinkedList <Process>();  
	ArrayList<Process> arrayList = new ArrayList<Process>();
	
	int processNumber = 1; 
	long arrivalTime; 
	long executionTime;
	
	while (scanner2.hasNext()) {
		
		arrivalTime = scanner2.nextInt(); 
		executionTime = scanner2.nextInt();
		Process p = new Process(processNumber, arrivalTime, executionTime);
		arrayList.add(p);
		processNumber++;
		
		}
	
	sortQueue(arrayList);
	
	for(Process i: arrayList) {
		waitQueue.add(i);
    }
    
	scanner2.close();
	
	Scheduler s = new Scheduler(waitQueue);
	Thread scheduler = new Thread (s);
	scheduler.start(); 
	scheduler.join();
	
	System.out.println("----------------------------------");
	System.out.println("Waiting Times: ");
	
	for(Process i: arrayList) {
		i.printWaitingTime();
		}
     }

    private static void sortQueue(ArrayList<Process> arrayList) {
        Collections.sort(arrayList, new Comparator<Process>(){

        	public int  compare(Process p1, Process p2) {
        		
        		return (int)(p1.getArrivalTime() - p2.getArrivalTime());
        	}
        	});
    }
}