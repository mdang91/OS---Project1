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

	
	public void printSchedule() {
		Process proc = new Process();
		for(int i = 0; i < num; i++) {
			proc = this.getProcess(i);
			System.out.println("PID: " + proc.getPID() + "   Cyc: " + proc.getCycles() + "  Mem: " + proc.getMem());
		}
		
	}
	
	
	}
