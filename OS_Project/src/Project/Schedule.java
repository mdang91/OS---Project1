package Project;

import java.util.ArrayList;

public class Schedule {
	
	private String name;
	private ArrayList<Process> sched = new ArrayList<Process>();
	private int num;
	private long speed;
	
	//private int maxMem;   //for future parts
	
	public Schedule(String n) {
		this.name = n;
		num = 0;
	}
	
	public void addProcess(Process p) {			//adds a process to array list and increment num
		sched.add(p);
		num++;
	}
	
	public int getNum() {						//return num (number or process in a list)
		return num;
	}
	
	public Process getProcess(int i) {			//return the process at index i
		return sched.get(i);
	}
	public long getSpeed() {					//return speed of CPU
		return this.speed;
	}
	
	public Schedule clone() {
		return this;
	}
	
	public void setSpeed(long sp) {				//used to set the speed of the CPU used
		this.speed = sp;
	}

	public int waitTimeAvg() {					//returns average of wait times in this schedule
		int num = this.getNum();
		
		int wait = 0;					//wait stores wait time of each 
		int waitSum = 0;				//waitSum sums the wait times of each
		int timeElapsed = 0;
		long runTime = 0;
		long speed = this.getSpeed();
		
		Process proc = new Process();
		long cycles;
		
		for(int i = 0; i < num; i++) {
			//get current process
			proc = this.getProcess(i);
			cycles = proc.getCycles();
			
			//wait time is equal to the time elapsed before running current process
			wait = timeElapsed;
			//wait sum is sum of wait time for all processes
			waitSum += wait;
			//runTime is cycles of current process divided by speed of CPU used
			runTime =  (cycles / speed);
			//total time elapsed at end of current process			
			timeElapsed += runTime;
		}
		
		
		return waitSum / num;
	}
	
	public int turnAroundTimeAvg () {			//returns average of turn around time for thi schedule
		
		int num = this.getNum();
		long runTime = 0;
		int timeElapsed = 0;
		
		
		long speed = this.getSpeed();
		Process proc = new Process();
		long cycles;
		
		for(int i = 0; i < num; i++) {
			
			proc = this.getProcess(i);
			cycles = proc.getCycles();
			
			//runTime is cycles of current process divided by speed of CPU used
			runTime = cycles / speed;
			
			//time elapsed at finish of runtime is turnaround time (for FIFO anf SJF)
			timeElapsed += runTime;
			
		}
		
		
		
		
		return timeElapsed;
	}
	
 	public void printSchedule() {
		Process proc = new Process();
		for(int i = 0; i < num; i++) {
			proc = this.getProcess(i);
			System.out.println("PID: " + proc.getPID() + "   Cyc: " + proc.getCycles() + "  Mem: " + proc.getMem());
		}
		
	}
	
	
	}
