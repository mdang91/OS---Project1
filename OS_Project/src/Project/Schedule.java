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
	public Schedule copySchedule() {
		Schedule copy = new Schedule(this.getName() + "copy");
		copy.setSpeed(this.getSpeed());
		
		
		for(int i = 0; i < num; i++) {
			copy.addProcess(this.getProcess(i));
		}
		
		return copy;
		
	}
	
	public ArrayList<Process> getSched(){
		return this.sched;
	}
	

	public void addProcess(Process p) {			//adds a process to array list and increment num
		sched.add(p);
		num++;
	}
	
	public int getNum() {						//return num (number or process in a list)
		return num;
	}
	public String getName() {
		return this.name;
	}
	
	public Process getProcess(int i) {			//return the process at index i
		return sched.get(i);
	}
	public long getSpeed() {					//return speed of CPU
		return this.speed;
	}
	

	
	public void setSpeed(long sp) {				//used to set the speed of the CPU used
		this.speed = sp;
	}
	
	public void removeProcess(int i) {
		this.sched.remove(i);
		this.num--;
	}
	
	public int waitTimeAvg() {			//returns average of wait times in this schedule
		
		//input variables
		int num = this.getNum();
		long speed = this.getSpeed();
		Process proc = new Process();
		long cycles;
		
		//calculated variables		
		int wait = 0;					//wait stores wait time of each 
		int waitSum = 0;				//waitSum sums the wait times of each
		int timeElapsed = 0;
		long runTime = 0;

		
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
		
		
		//input variable
		Process proc = new Process();
		int num = this.getNum();	
		long speed = this.getSpeed();
		long cycles;
		
		//calculated variables
		long runTime = 0;
		int timeElapsed = 0;
		int turnAroundSum = 0;
		int turnAroundAvg;
		
		


		
		
		for(int i = 0; i < num; i++) {
			
			proc = this.getProcess(i);
			cycles = proc.getCycles();
			
			//runTime is cycles of current process divided by speed of CPU used
			runTime = cycles / speed;
			
			//time elapsed at finish of runtime is turnaround time (for FIFO anf SJF)
			timeElapsed += runTime;
			
			turnAroundSum += timeElapsed;
			
		}
		
		turnAroundAvg = turnAroundSum / num;
		
		
		
		return turnAroundAvg;
	}
	
 	public void printSchedule() {
		Process proc = new Process();
		for(int i = 0; i < num; i++) {
			proc = this.getProcess(i);
			System.out.println("PID: " + proc.getPID() + "   Cyc: " + proc.getCycles() + "  Mem: " + proc.getMem());
		}
		
	}
	
	
	}
