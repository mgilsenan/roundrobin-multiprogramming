import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class Scheduler implements Runnable {
	
	long timeCounter = 0; 
	ArrayList <Process> sortingQueue = new ArrayList<Process>();
	Queue <Process> readyQueue = new LinkedList <Process>();  
	Queue <Process> waitQueue = new LinkedList <Process>(); 
	
Scheduler (Queue<Process> waitQueue) {
	this.waitQueue = waitQueue; 
}

public void run() {
	
    while (!waitQueue.isEmpty() || !readyQueue.isEmpty() || !sortingQueue.isEmpty()){		
        
        checkReadyQueue();		
            
        checkWaitQueueEmpty();
            
        checkWaitQueueNotEmpty();
    }
 
	}

private void checkWaitQueueNotEmpty() {
    while (!waitQueue.isEmpty()) {	

            Process q = waitQueue.element();
            
            if (timeCounter >= q.arrivalTimeMs) {
            readyQueue.add(q);
            waitQueue.remove(q);
            break;
            }
            else {
                timeCounter++;
                break;	
            }	
    }
}

private void checkWaitQueueEmpty() {
    while (!sortingQueue.isEmpty() && waitQueue.isEmpty()) {
      
    Collections.sort(sortingQueue, new Comparator<Process>(){

        public int  compare(Process p1, Process p2) {
            
            return (int)(p1.getRemainingTimeMs() - p2.getRemainingTimeMs());
        }
        });
    
    for (Process j: sortingQueue) {
        waitQueue.add(j);
    }
    sortingQueue.clear(); 
    }
}

private void checkReadyQueue() {
    while (!readyQueue.isEmpty()) {
        
            Process process = readyQueue.element();
            Thread threadProcess = new Thread(process);	

            
        if (process.remainingTimeMs > process.quantumMs) {

            if (process.remainingTimeMs == process.executionTimeMs) {
            
            threadProcess.start();
            process.setRemainingTime();
            process.setProcessState("Started");
            System.out.print("Time "+ timeCounter +", ");
            process.printProcessInfo();
            
            process.setProcessState("Resumed");
            System.out.print("Time "+ timeCounter +", ");
            process.printProcessInfo();
            }
        
            else {
            threadProcess.start();
            process.setRemainingTime();
            process.setProcessState("Resumed");
            System.out.print("Time "+ timeCounter +", ");
            process.printProcessInfo();
            }
            
            sortingQueue.add(process);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            timeCounter += process.getQuantumMs(); 
            
            process.setProcessState("Paused"); 
            readyQueue.remove();
            System.out.print("Time "+ timeCounter +", ");
            process.printProcessInfo();
            
        }
    
        else {
            threadProcess.start(); 
            
            try {
                threadProcess.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            

            timeCounter += process.remainingTimeMs;
            process.setRemainingTime();
            process.setProcessState("Finished");
            readyQueue.remove();
            System.out.print("Time "+ timeCounter +", ");
            process.setEndTime(timeCounter);
            process.printProcessInfo();
        }
    }
}
}