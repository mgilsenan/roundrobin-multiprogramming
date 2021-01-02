public class Process implements Runnable {
	
	long timerMs; 
	int processNumber; 
	long endTimeMs; 
	long arrivalTimeMs;
	
	long executionTimeMs;  
	long remainingTimeMs; 
	long waitingTimeMs;
	long quantumMs; 
	String processState = null;
    private long arrivalTime;
    private long executionTime;
	
	Process(int processNumber, long arrivalTime, long executionTime) {
        this.processNumber = processNumber; 
        this.arrivalTime = arrivalTime;
        this.executionTime = executionTime;
		this.arrivalTimeMs = arrivalTime*1000;
		this.executionTimeMs = executionTime*1000; 
		remainingTimeMs = executionTimeMs;
		quantumMs = 1000;
	}
	
	String getProcessInfo() {
			String info = " Process " + processNumber + ", " + processState; 
			return info;
	}
	
	void printWaitingTime() { 
		waitingTimeMs = endTimeMs- (executionTimeMs + arrivalTimeMs);
		System.out.println("Process " + processNumber + ": " + waitingTimeMs);
	}

	void setProcessState(String processState) { 
		this.processState = processState;
	}
	
	void setRemainingTime() {
		if (remainingTimeMs > quantumMs)
		remainingTimeMs -= quantumMs;
		else 
			remainingTimeMs = 0;
	}
	
	void setEndTime(long endTimeDec) { 
		this.endTimeMs = endTimeDec;
	}
	
	int getProcessNumberMs() {
		return processNumber;
	}
	
	long getQuantumMs() {
		return quantumMs;
	}
	long getRemainingTimeMs() {
		return remainingTimeMs; 
	}
	long getWaitingTimeDecMs() {
		return waitingTimeMs;
	}
	
	long getArrivalTimeMs() {
		return arrivalTimeMs;
	}
	
    public void printProcessInfo() {
        System.out.println("Process " + processNumber + ", " + processState);
    }

	public void run () {

	}

	public long getArrivalTime() {
		return arrivalTime;
	}
}

